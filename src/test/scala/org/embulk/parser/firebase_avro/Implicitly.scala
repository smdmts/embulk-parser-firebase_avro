package org.embulk.parser.firebase_avro

import org.embulk.parser.firebase_avro.define.Root
import org.scalacheck.Arbitrary
import org.scalacheck.Shapeless._

object Implicitly {
  implicitly[Arbitrary[Root]]
}
