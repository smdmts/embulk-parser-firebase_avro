package org.embulk.parser.firebase_avro

import org.embulk.parser.firebase_avro.define.Root
import org.scalacheck.Shapeless._
import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks

class ParserTest extends PropSpec with PropertyChecks with MustMatchers {
  property("could be parsing.") {
    forAll(minSize(1000)) { (root: Root) =>
      root.user_dim match {
        case Some(v) =>
          // could be parse.
          val result = Parser(root)
        case None =>
          intercept[RuntimeException] {
            Parser(root)
          }
      }
    }
  }
}
