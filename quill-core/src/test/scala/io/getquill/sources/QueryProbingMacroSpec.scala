package io.getquill.sources

import io.getquill._
import io.getquill.sources.mirror.mirrorSource._
import io.getquill.sources.mirror.mirrorSource
import io.getquill.sources.mirror._

class QueryProbingMacroSpec extends SourceSpec(mirrorSource) {

  "fails if the source can't be resolved at compile time" in {
    object s extends MirrorSourceTemplate with QueryProbing
    "buggySource.run(qr1)" mustNot compile
  }

  "doesn't warn if query probing is disabled and the source can't be resolved at compile time" in {
    object s extends MirrorSourceTemplate
    s.run(qr1.delete)
    ()
  }

  "fails compilation if the query probing fails" - {
    case class Fail()
    "object source" in {
      "mirrorSource.run(query[Fail].delete)" mustNot compile
    }
    "class source" in {
      def test(s: MirrorSourceTemplateWithQueryProbing) =
        "mirrorSource.run(query[Fail].delete)" mustNot compile
    }
  }
}
