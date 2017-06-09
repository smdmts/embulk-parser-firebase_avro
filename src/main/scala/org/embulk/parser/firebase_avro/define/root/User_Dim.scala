package org.embulk.parser.firebase_avro.define.root

case class User_Dim(user_id: Option[String],
                    first_open_timestamp_micros: Option[Long],
                    user_properties: List[user_dim.User_Properties],
                    device_info: Option[user_dim.Device_Info],
                    geo_info: Option[user_dim.Geo_Info],
                    app_info: Option[user_dim.App_Info],
                    traffic_source: Option[user_dim.Traffic_Source],
                    bundle_info: Option[user_dim.Bundle_Info],
                    ltv_info: Option[user_dim.Ltv_Info])
