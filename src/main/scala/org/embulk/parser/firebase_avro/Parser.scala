package org.embulk.parser.firebase_avro

import org.embulk.parser.firebase_avro.column.Columns
import org.embulk.parser.firebase_avro.define.Root
import org.embulk.parser.firebase_avro.define.root.{Event_Dim, User_Dim}
import org.embulk.parser.firebase_avro.json.event_dim.EventParmsJsonSerializer
import org.embulk.parser.firebase_avro.json.user_dim.UserPropertiesJsonSerializer

import scala.language.reflectiveCalls

object Parser {

  def apply(record: Root): Seq[Seq[ValueHolder[_]]] = {
    val userFields = userDims(
      record.user_dim.getOrElse(sys.error("could not get user")))
    if (record.event_dim.isEmpty) sys.error("empty event")
    record.event_dim.map {
      userFields ++ eventDims(_)
    }
  }

  private def eventDims(eventDim: Event_Dim) = {
    val c = Columns.find("event_dim", _: String)
    Seq(
      ValueHolder(c("name"), eventDim.name),
      ValueHolder(c("date"), eventDim.date),
      ValueHolder(c("timestamp_micros"), eventDim.timestamp_micros),
      ValueHolder(c("previous_timestamp_micros"),
                  eventDim.previous_timestamp_micros),
      ValueHolder(c("value_in_usd"), eventDim.value_in_usd),
      ValueHolder(c("params"), EventParmsJsonSerializer(eventDim.params))
    )
  }

  private def userDims(userDim: User_Dim) = {
    val userFields = {
      val c = Columns.find("user_dim", _: String)
      Seq(
        ValueHolder(c("user_id"), userDim.user_id),
        ValueHolder(c("first_open_timestamp_micros"),
                    userDim.first_open_timestamp_micros),
        ValueHolder(c("user_properties"),
                    UserPropertiesJsonSerializer(userDim.user_properties))
      )
    }

    val appInfo = {
      val c = Columns.find("app_info", _: String)
      val that = userDim.app_info
      Seq(
        ValueHolder(c("app_id"), that.flatMap(_.app_id)),
        ValueHolder(c("app_instance_id"), that.flatMap(_.app_instance_id)),
        ValueHolder(c("app_platform"), that.flatMap(_.app_platform)),
        ValueHolder(c("app_store"), that.flatMap(_.app_store)),
        ValueHolder(c("app_version"), that.flatMap(_.app_version))
      )
    }

    val bundleInfo = {
      val c = Columns.find("bundle_info", _: String)
      val that = userDim.bundle_info
      Seq(
        ValueHolder(c("bundle_sequence_id"), that.flatMap(_.bundle_sequence_id)),
        ValueHolder(c("server_timestamp_offset_micros"),
                    that.flatMap(_.server_timestamp_offset_micros))
      )
    }

    val geoInfo = {
      val c = Columns.find("geo_info", _: String)
      val that = userDim.geo_info
      Seq(
        ValueHolder(c("city"), that.flatMap(_.city)),
        ValueHolder(c("continent"), that.flatMap(_.continent)),
        ValueHolder(c("country"), that.flatMap(_.country)),
        ValueHolder(c("region"), that.flatMap(_.region))
      )
    }

    val deviceInfo = {
      val c = Columns.find("device_info", _: String)
      val that = userDim.device_info
      Seq(
        ValueHolder(c("device_category"), that.flatMap(_.device_category)),
        ValueHolder(c("device_id"), that.flatMap(_.device_id)),
        ValueHolder(c("device_model"), that.flatMap(_.device_model)),
        ValueHolder(c("device_time_zone_offset_seconds"),
                    that.flatMap(_.device_time_zone_offset_seconds)),
        ValueHolder(c("limited_ad_tracking"), that.flatMap(_.limited_ad_tracking)),
        ValueHolder(c("mobile_brand_name"), that.flatMap(_.mobile_brand_name)),
        ValueHolder(c("mobile_marketing_name"),
                    that.flatMap(_.mobile_marketing_name)),
        ValueHolder(c("mobile_model_name"), that.flatMap(_.mobile_model_name)),
        ValueHolder(c("platform_version"), that.flatMap(_.platform_version)),
        ValueHolder(c("resettable_device_id"),
                    that.flatMap(_.resettable_device_id)),
        ValueHolder(c("user_default_language"),
                    that.flatMap(_.user_default_language))
      )
    }

    val trafficSource = {
      val c = Columns.find("traffic_source", _: String)
      val that = userDim.traffic_source
      Seq(
        ValueHolder(c("user_acquired_campaign"),
                    that.flatMap(_.user_acquired_campaign)),
        ValueHolder(c("user_acquired_medium"),
                    that.flatMap(_.user_acquired_medium)),
        ValueHolder(c("user_acquired_source"),
                    that.flatMap(_.user_acquired_source))
      )
    }

    val ltvInfo = {
      val c = Columns.find("ltv_info", _: String)
      val that = userDim.ltv_info
      Seq(ValueHolder(c("currency"), that.flatMap(_.currency)),
           ValueHolder(c("revenue"), that.flatMap(_.revenue)))
    }

    userFields ++ appInfo ++ bundleInfo ++ geoInfo ++ deviceInfo ++ trafficSource ++ ltvInfo
  }
}
