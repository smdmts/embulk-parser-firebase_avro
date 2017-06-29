package org.embulk.parser.firebase_avro.json.event_dim

import org.embulk.parser.firebase_avro.define.root.event_dim.Params

import scala.collection.mutable
import io.circe._
import io.circe.syntax._
import org.embulk.parser.firebase_avro.json.CustomEncoder.mapEncoder

object EventParmsJsonSerializer {
  def apply(params: List[Params]): Option[Json] = {
    val map = mutable.Map[String, Any]()
    params.foreach { p =>
      val value: Option[Any] = p.value.flatMap { v =>
        v.float_value
          .orElse(v.double_value)
          .orElse(v.int_value)
          .orElse(v.string_value)
      }
      for {
        key   <- p.key
        value <- value
      } yield map.put(key, value)
    }
    if (map.nonEmpty) {
      Some(map.toMap.asJson)
    } else None
  }
}
