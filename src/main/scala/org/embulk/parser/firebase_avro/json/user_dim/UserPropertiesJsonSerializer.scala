package org.embulk.parser.firebase_avro.json.user_dim

import io.circe.Json
import io.circe.syntax._
import org.embulk.parser.firebase_avro.define.root.user_dim.User_Properties
import org.embulk.parser.firebase_avro.json.CustomEncoder.mapEncoder

import scala.collection.mutable

object UserPropertiesJsonSerializer {
  def apply(properties: List[User_Properties]): Option[Json] = {
    val map = mutable.Map[String, Any]()
    properties.foreach { p =>
      val value: Option[Any] = for {
        v  <- p.value
        iv <- v.value
      } yield {
        v.set_timestamp_usec.foreach(a => map.put("set_timestamp_usec", a))
        v.index.foreach(a => map.put("index", a))
        iv.float_value
          .orElse(iv.double_value)
          .orElse(iv.int_value)
          .orElse(iv.string_value)
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
