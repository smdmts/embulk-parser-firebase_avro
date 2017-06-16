package org.embulk.parser.firebase_avro.define.root.user_dim

case class App_Info(app_version: Option[String],
                    app_instance_id: Option[String],
                    app_store: Option[String],
                    app_platform: Option[String],
                    app_id: Option[String])
