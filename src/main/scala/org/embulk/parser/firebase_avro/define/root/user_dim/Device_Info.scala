package org.embulk.parser.firebase_avro.define.root.user_dim

case class Device_Info(device_category: Option[String],
                       mobile_brand_name: Option[String],
                       mobile_model_name: Option[String],
                       mobile_marketing_name: Option[String],
                       device_model: Option[String],
                       platform_version: Option[String],
                       device_id: Option[String],
                       resettable_device_id: Option[String],
                       user_default_language: Option[String],
                       device_time_zone_offset_seconds: Option[Long],
                       limited_ad_tracking: Option[Boolean])
