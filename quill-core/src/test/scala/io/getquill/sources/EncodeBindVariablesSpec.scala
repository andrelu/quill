package io.getquill.sources

import io.getquill.SourceSpec
import io.getquill.sources.mirror.mirrorSource._
import io.getquill.sources.mirror.mirrorSource
import io.getquill.sources.mirror.Row

class EncodeBindVariablesSpec extends SourceSpec(mirrorSource) {

  "encodes bind variables" - {
    "one" in {
      val q = quote {
        (i: Int) => qr1.filter(t => t.i == i)
      }
      mirrorSource.run(q)(1).binds mustEqual Row(1)
    }
    "two" in {
      val q = quote {
        (i: Int, j: Long, o: Option[Int]) => qr1.filter(t => t.i == i && t.i > j && t.o == o)
      }
      mirrorSource.run(q)(1, 2, None).binds mustEqual Row(1, 2L, None)
    }
  }

  "fails if there isn't an encoder for the binded value" in {
    val q = quote {
      (i: Thread) => qr1.filter(_.i == i)
    }
    "mirrorSource.run(q)(new Thread)" mustNot compile
  }

  "uses a custom implicit encoder" in {
    implicit val doubleEncoder = new mirrorSource.Encoder[Double] {
      override def apply(index: Int, value: Double, row: Row) =
        row.add(value)
    }
    val q = quote {
      (d: Double) => qr1.filter(_.i == d)
    }
    mirrorSource.run(q)(1D).binds mustEqual Row(1D)
  }

  "encodes bind variables for wrapped types" - {

    "encodes `WrappedValue` extended value class" in {
      case class Entity(x: WrappedEncodable)
      val q = quote {
        (x: WrappedEncodable) => query[Entity].filter(_.x == x)
      }
      val r = mirrorSource.run(q)(WrappedEncodable(1))
      r.ast.toString mustEqual "query[Entity].filter(x3 => x3.x == p1).map(x3 => x3.x)"
      r.binds mustEqual Row(1)
    }

    "encodes constructable `WrappedType` extended class" in {
      case class Wrapped(value: Int) extends WrappedType {
        override type Type = Int
      }
      case class Entity(x: Wrapped)

      val q = quote {
        (x: Wrapped) => query[Entity].filter(_.x == x)
      }
      val r = mirrorSource.run(q)(Wrapped(1))
      r.ast.toString mustEqual "query[Entity].filter(x4 => x4.x == p1).map(x4 => x4.x)"
      r.binds mustEqual Row(1)
    }

    "fails for unwrapped class" in {
      case class WrappedNotEncodable(value: Int)
      case class Entity(x: WrappedNotEncodable)
      val q = quote {
        (x: WrappedNotEncodable) => query[Entity].filter(_.x == x)
      }
      "mirrorSource.run(q)(WrappedNotEncodable(1))" mustNot compile
    }
  }
}
