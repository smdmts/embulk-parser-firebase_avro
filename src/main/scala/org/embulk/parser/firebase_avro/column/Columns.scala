package org.embulk.parser.firebase_avro.column

import org.embulk.spi.{Column => EmbulkColumn}

object Columns {
  val instance = Columns()
  private val map: Map[String, EmbulkColumn] = instance.map { c =>
    c.fullPath -> c.embulkColumn
  }.toMap

  private var cache: Map[String, EmbulkColumn] = Map.empty

  def find(className: String, fieldName: String): EmbulkColumn = {
    cache.get(s"$className.$fieldName") match {
      case Some(v) => v
      case None =>
        val detect = map.flatMap {
          case (name, column) =>
            if (name.contains(s"$className.$fieldName")) Some(column)
            else None
        }
        if (detect.size == 1) {
          val result = detect.head
          cache = cache ++ Map(s"$className.$fieldName" -> detect.head)
          result
        } else
          sys.error(s"could not find column. ${className + "." + fieldName}")
    }
  }

  def apply(): Seq[Column] = {
    val userColumns = UserDimension(0)
    val eventColumns = EventDimension(userColumns.size)
    userColumns ++ eventColumns
  }

  def generate(startIndex: Int)(s: Seq[((Int) => Seq[Column])]): Seq[Column] = {
    var index = startIndex
    s.foldLeft[List[Column]](Nil) {
      case (a, b) =>
        val additionalColumn = b(index)
        index = index + additionalColumn.size
        a ++ additionalColumn
    }
  }

}
