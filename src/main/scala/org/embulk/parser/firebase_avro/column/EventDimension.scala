package org.embulk.parser.firebase_avro.column

import org.embulk.spi.{Column => EmbulkColumn}
import org.embulk.spi.`type`.Types

object EventDimension {
  def apply(index: Int): List[Column] = {
    Columns.generate(index) {
      List(
        Column
          .getGeneral[org.embulk.parser.firebase_avro.define.root.Event_Dim],
        (i: Int) =>
          List(
            Column(
              "org.embulk.parser.firebase_avro.define.root.event_dim.params",
              new EmbulkColumn(i, "event_dim.params", Types.JSON)))
      )
    }
  }
}
