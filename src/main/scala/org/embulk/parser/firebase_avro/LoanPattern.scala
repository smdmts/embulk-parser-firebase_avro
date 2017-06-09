package org.embulk.parser.firebase_avro

import scala.util.control.Exception.ignoring

object LoanPattern {

  type Closable = { def close(): Unit }

  def apply[R <: Closable, A](resource: R)(f: R => A): A = {
    try {
      f(resource)
    } finally {
      ignoring(classOf[Throwable]) apply {
        resource.close()
      }
    }
  }

}
