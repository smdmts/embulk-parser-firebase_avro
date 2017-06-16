package org.embulk.parser.firebase_avro.column

import org.embulk.parser.firebase_avro.define.root.user_dim._
import org.embulk.spi.{Column => EmbulkColumn}
import org.embulk.spi.`type`.Types

object UserDimension {
  def apply(index: Int): List[Column] = {
    Columns.generate(index) {
      List(
        Column
          .getGeneral[org.embulk.parser.firebase_avro.define.root.User_Dim],
        (i: Int) =>
          List(
            Column(
              "org.embulk.parser.firebase_avro.define.root.user_dim.user_properties",
              new EmbulkColumn(i, "user_dim.user_property", Types.JSON))),
        Column.getGeneral[Device_Info],
        Column.getGeneral[Geo_Info],
        Column.getGeneral[App_Info],
        Column.getGeneral[Traffic_Source],
        Column.getGeneral[Bundle_Info],
        Column.getGeneral[Ltv_Info]
      )
    }

  }

}
