package org.embulk.parser.firebase_avro.define.root.user_dim.user_properties.value

case class Value(string_value: Option[String] = None,
                 int_value: Option[Long] = None,
                 float_value: Option[Double] = None,
                 double_value: Option[Double] = None)
