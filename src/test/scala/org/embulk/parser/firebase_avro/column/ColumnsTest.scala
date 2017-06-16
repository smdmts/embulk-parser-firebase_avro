package org.embulk.parser.firebase_avro.column

import org.scalatest._
import Matchers._

class ColumnsTest extends FlatSpec with MustMatchers {

  "columuns" should "be indexing" in {
    val columns = Columns()
    val summery = columns.indices.sum
    columns.map(_.embulkColumn).map(_.getIndex).sum should be (summery)
  }

  "columns" should "be finding" in {
    Columns.find("user_dim" , "first_open_timestamp_micros")
  }

}
