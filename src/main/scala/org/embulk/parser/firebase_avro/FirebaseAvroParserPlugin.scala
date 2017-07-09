package org.embulk.parser.firebase_avro

import com.google.common.io.ByteStreams

import scala.collection.JavaConverters._
import com.sksamuel.avro4s.AvroInputStream
import io.circe.Json
import org.embulk.config.ConfigSource
import org.embulk.config.TaskSource
import org.embulk.parser.firebase_avro.column.Columns
import org.embulk.parser.firebase_avro.define.Root
import org.embulk.spi._
import org.embulk.spi.json.JsonParser
import org.embulk.spi.time.Timestamp
import org.embulk.spi.util.FileInputInputStream

object FirebaseAvroParserPlugin {
  def buildColumn(): Schema = {
    new Schema(Columns.instance.map(_.embulkColumn).asJava)
  }
}

class FirebaseAvroParserPlugin extends ParserPlugin {
  val logger = Exec.getLogger(classOf[FirebaseAvroParserPlugin])
  override def transaction(config: ConfigSource, control: ParserPlugin.Control): Unit = {
    val task = config.loadConfig(classOf[PluginTask])
    control.run(task.dump, FirebaseAvroParserPlugin.buildColumn())
  }

  override def run(taskSource: TaskSource, schema: Schema, input: FileInput, output: PageOutput): Unit =
    LoanPattern(new FileInputInputStream(input)) { efis =>
      LoanPattern(new PageBuilder(Exec.getBufferAllocator, schema, output)) { pb =>
        while (efis.nextFile()) {
          convertToSeq(efis).foreach { v =>
            addRecords(pb, v)
          }
        }
        pb.finish()
      }
    }

  def convertToSeq(is: FileInputInputStream): Seq[Root] =
    AvroInputStream.data[Root](ByteStreams.toByteArray(is)).iterator().toSeq

  def addRecords(pb: PageBuilder, record: Root): Unit =
    Parser(record).foreach { rows =>
      rows.foreach {
        case ValueHolder(c, Some(x: Int)) =>
          pb.setLong(c, x)
        case ValueHolder(c, Some(x: Long)) =>
          pb.setLong(c, x)
        case ValueHolder(c, Some(x: Double)) =>
          pb.setDouble(c, x)
        case ValueHolder(c, Some(x: Float)) =>
          pb.setDouble(c, x)
        case ValueHolder(c, Some(x: Boolean)) =>
          pb.setBoolean(c, x)
        case ValueHolder(c, Some(x: String)) =>
          pb.setString(c, x)
        case ValueHolder(c, Some(x: Json)) =>
          pb.setJson(c, new JsonParser().parse(x.noSpaces))
        case ValueHolder(c, Some(x: Timestamp)) =>
          pb.setTimestamp(c, x)
        case ValueHolder(c, None) =>
          pb.setNull(c)
      }
      pb.addRecord()
    }
}
