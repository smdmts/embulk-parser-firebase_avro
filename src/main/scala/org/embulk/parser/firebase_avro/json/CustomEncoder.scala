package org.embulk.parser.firebase_avro.json

import io.circe.{Encoder, Json}

object CustomEncoder {
  implicit val mapEncoder: Encoder[Map[String, Any]] =
    new Encoder[Map[String, Any]] {
      final def apply(a: Map[String, Any]): Json = {
        val jsonValues = a.keys.map { key =>
          a(key) match {
            case v: String => (key, Json.fromString(v))
            case v: Double =>
              (key, Json.fromDouble(v).getOrElse(Json.fromString(v.toString)))
            case v: Long => (key, Json.fromLong(v))
            case _ => (key, Json.Null)
          }
        }
        Json.fromFields(jsonValues)
      }
    }
}
