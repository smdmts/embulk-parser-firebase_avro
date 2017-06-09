package org.embulk.parser.firebase_avro.column

import org.embulk.spi.{Column => EmbulkColumn}
import org.embulk.spi.`type`.Types

import scala.language.implicitConversions

object Column {

  import scala.reflect.runtime.universe._

  def getGeneral[A](index: Int)(implicit tpe: TypeTag[A]): List[Column] = {
    var counter = index
    typeOf[A].members.filter(!_.isMethod).flatMap { implicit member =>
      val column = member.typeSignature match {
        case t if t =:= typeOf[Option[String]] =>
          Some(factory(counter, Types.STRING))
        case t if t =:= typeOf[Option[Int]] =>
          Some(factory(counter, Types.LONG))
        case t if t =:= typeOf[Option[Long]] =>
          Some(factory(counter, Types.LONG))
        case t if t =:= typeOf[Option[Double]] =>
          Some(factory(counter, Types.DOUBLE))
        case t if t =:= typeOf[Option[Boolean]] =>
          Some(factory(counter, Types.BOOLEAN))
        case t if t =:= typeOf[String] =>
          Some(factory(counter, Types.STRING))
        case t if t =:= typeOf[Int] =>
          Some(factory(counter, Types.LONG))
        case t if t =:= typeOf[Long] =>
          Some(factory(counter, Types.LONG))
        case t if t =:= typeOf[Double] =>
          Some(factory(counter, Types.DOUBLE))
        case t if t =:= typeOf[Boolean] =>
          Some(factory(counter, Types.BOOLEAN))
        case _ =>
          counter = counter - 1
          None
      }
      counter = counter + 1
      column
    }
  }.toList

  private def factory[A](counter: Int, tpe: org.embulk.spi.`type`.Type)(
      implicit tt: TypeTag[A],
      symbol: Symbol): Column = {
    val fullpath = tt.tpe.etaExpand.toString.toLowerCase + "." + symbol.name.toString
    Column(fullpath, new EmbulkColumn(counter, name, tpe))
  }

  def name[A](implicit tpe: TypeTag[A], symbol: Symbol): String = {
    val fullpath = tpe.tpe.etaExpand.toString
    val lessLength = "org.embulk.parser.firebase_avro.define.root.".length
    fullpath.substring(lessLength).toLowerCase + "." + symbol.name.toString
  }
}

case class Column(fullPath: String, embulkColumn: EmbulkColumn)
