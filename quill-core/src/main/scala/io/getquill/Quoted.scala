package io.getquill

import io.getquill.ast.Ast

trait Quoted[+T] {
  def ast: Ast
}
