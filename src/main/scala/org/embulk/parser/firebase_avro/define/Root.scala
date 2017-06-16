package org.embulk.parser.firebase_avro.define

import org.embulk.parser.firebase_avro.define.root._

case class Root(user_dim: Option[User_Dim], event_dim: List[Event_Dim])
