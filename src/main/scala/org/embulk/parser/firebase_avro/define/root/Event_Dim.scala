package org.embulk.parser.firebase_avro.define.root

case class Event_Dim(date: Option[String],
                     name: Option[String],
                     params: List[event_dim.Params],
                     timestamp_micros: Option[Long],
                     previous_timestamp_micros: Option[Long],
                     value_in_usd: Option[Double])
