package io.getquill

import scala.language.implicitConversions
import language.experimental.macros
import io.getquill.quotation.NonQuotedException
import io.getquill.sources._
import scala.reflect.ClassTag

trait Ops {

  def query[T](implicit ct: ClassTag[T]): EntityQuery[T] = NonQuotedException()

  def lift[T](v: T): T = NonQuotedException()

  def quote[T](body: Quoted[T]): Quoted[T] = macro Macro.doubleQuote[T]
  def quote[T1, R](func: T1 => Quoted[R]): Quoted[T1 => R] = macro Macro.quotedFunctionBody
  def quote[T1, T2, R](func: (T1, T2) => Quoted[R]): Quoted[(T1, T2) => R] = macro Macro.quotedFunctionBody
  def quote[T1, T2, T3, R](func: (T1, T2, T3) => Quoted[R]): Quoted[(T1, T2, T3) => R] = macro Macro.quotedFunctionBody
  def quote[T1, T2, T3, T4, R](func: (T1, T2, T3, T4) => Quoted[R]): Quoted[(T1, T2, T3, T4) => R] = macro Macro.quotedFunctionBody
  def quote[T1, T2, T3, T4, T5, R](func: (T1, T2, T3, T4, T5) => Quoted[R]): Quoted[(T1, T2, T3, T4, T5) => R] = macro Macro.quotedFunctionBody
  def quote[T1, T2, T3, T4, T5, T6, R](func: (T1, T2, T3, T4, T5, T6) => Quoted[R]): Quoted[(T1, T2, T3, T4, T5, T6) => R] = macro Macro.quotedFunctionBody
  def quote[T1, T2, T3, T4, T5, T6, T7, R](func: (T1, T2, T3, T4, T5, T6, T7) => Quoted[R]): Quoted[(T1, T2, T3, T4, T5, T6, T7) => R] = macro Macro.quotedFunctionBody
  def quote[T1, T2, T3, T4, T5, T6, T7, T8, R](func: (T1, T2, T3, T4, T5, T6, T7, T8) => Quoted[R]): Quoted[(T1, T2, T3, T4, T5, T6, T7, T8) => R] = macro Macro.quotedFunctionBody
  def quote[T1, T2, T3, T4, T5, T6, T7, T8, T9, R](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9) => Quoted[R]): Quoted[(T1, T2, T3, T4, T5, T6, T7, T8, T9) => R] = macro Macro.quotedFunctionBody
  def quote[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R](func: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) => Quoted[R]): Quoted[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) => R] = macro Macro.quotedFunctionBody

  implicit def quote[T](body: T): Quoted[T] = macro Macro.quote[T]
  implicit def unquote[T](quoted: Quoted[T]): T = NonQuotedException()

  implicit class InfixInterpolator(val sc: StringContext) {
    def infix(args: Any*): InfixValue = NonQuotedException()
  }

  def mappedEncoding[I, O](f: I => O) = MappedEncoding(f)

  def Ord: OrdOps = NonQuotedException()

  implicit def implicitOrd[T]: Ord[T] = NonQuotedException()

  type Query[+T] = io.getquill.Query[T]
  type JoinQuery[A, B, R] = io.getquill.JoinQuery[A, B, R]
  type EntityQuery[T] = io.getquill.EntityQuery[T]
  type Schema[T] = io.getquill.Schema[T]
  type Action[T] = io.getquill.Action[T]
  type Insert[T] = io.getquill.Insert[T]
  type Update[T] = io.getquill.Update[T]
  type Delete[T] = io.getquill.Delete[T]
  type UnassignedAction[T] = io.getquill.UnassignedAction[T]
}
