package org.embulk.parser.firebase_avro

import org.embulk.spi.Column

case class ValueHolder[+A](column: Column, value: Option[A] = None)
