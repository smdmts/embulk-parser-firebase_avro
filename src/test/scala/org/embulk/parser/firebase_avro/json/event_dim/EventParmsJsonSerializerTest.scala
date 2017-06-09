package org.embulk.parser.firebase_avro.json.event_dim

import org.embulk.parser.firebase_avro.define.root.event_dim.Params
import org.embulk.parser.firebase_avro.define.root.event_dim.params.Value

import org.scalatest._

class EventParmsJsonSerializerTest extends FlatSpec with MustMatchers {

  "parameter" should "be encoding" in {
    val string = Params(Option("key_string") , Option(Value(string_value = Some("abc"))))
    val int = Params(Option("key_int") , Option(Value(int_value = Some(1))))
    val double = Params(Option("key_double") , Option(Value(double_value = Some(10D))))
    val float = Params(Option("key_float") , Option(Value(float_value = Some(10F))))
    val given = List(string , int , double , float)
    val that = EventParmsJsonSerializer(given).map(_.noSpaces)
    that mustBe Some("{\"key_float\":10.0,\"key_int\":1,\"key_string\":\"abc\",\"key_double\":10.0}")
  }
}
