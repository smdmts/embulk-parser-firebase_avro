package org.embulk.parser.firebase_avro.define.root.user_dim

case class Geo_Info(continent: Option[String],
                    country: Option[String],
                    region: Option[String],
                    city: Option[String])
