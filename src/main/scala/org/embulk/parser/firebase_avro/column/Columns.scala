package org.embulk.parser.firebase_avro.column

import org.embulk.spi.{Column => EmbulkColumn}

object Columns {
  val instance = Columns()
  val map: Map[String, EmbulkColumn] = instance.map { c =>
    c.fullPath -> c.embulkColumn
  }.toMap

  def find(className: String, fieldName: String): EmbulkColumn = {
    val detect = map.flatMap {
      case (name, column) =>
        if (name.contains(s"$className.$fieldName")) Some(column)
        else None
    }
    if (detect.size == 1) {
      detect.head
    } else sys.error(s"could not find column. ${className + "." + fieldName}")
  }

  def apply(): List[Column] = {
    val userColumns = UserDimension(0)
    val eventColumns = EventDimension(userColumns.size)
    userColumns ::: eventColumns
  }

  def generate(startIndex: Int)(
      s: Seq[((Int) => List[Column])]): List[Column] = {
    var index = startIndex
    s.foldLeft[List[Column]](Nil) {
      case (a, b) =>
        val additionalColumn = b(index)
        index = index + additionalColumn.size
        a ::: additionalColumn
    }
  }

}
