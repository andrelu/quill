package io.getquill.sources

import io.getquill.SourceSpec
import io.getquill.sources.mirror.mirrorSource
import io.getquill.sources.mirror.mirrorSource._

class ExtractSchmeaAndInsertActionSpec extends SourceSpec(mirrorSource) {

  "Extract should work" in {
    val q = quote {
      qr1.schema(_.entity("test").columns(_.i -> "'i", _.o -> "'i").generated(_.i)).insert
    }
    val (entity, insert) = ExtractEntityAndInsertAction(q.ast)
    entity.isDefined mustBe true
    insert.isDefined mustBe true
    entity.get.generated mustBe Some("i")
  }
}
