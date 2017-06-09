package org.embulk.parser.firebase_avro.define.root.user_dim

case class Traffic_Source(user_acquired_campaign: Option[String],
                          user_acquired_source: Option[String],
                          user_acquired_medium: Option[String])
