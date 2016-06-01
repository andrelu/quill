package io.getquill.ast

import io.getquill.SourceSpec
import io.getquill.sources.mirror.mirrorSource
import io.getquill.sources.mirror.mirrorSource._

class AstSpec extends SourceSpec(mirrorSource) {

  "overrides toString to ease debugging" in {
    val q =
      quote {
        query[TestEntity].filter(t => t.s == "test")
      }
    q.ast.toString mustEqual """query[TestEntity].filter(t => t.s == "test")"""
  }
}
