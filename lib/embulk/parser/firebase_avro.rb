Embulk::JavaPlugin.register_parser(
  "firebase_avro", "org.embulk.parser.firebase_avro.FirebaseAvroParserPlugin",
  File.expand_path('../../../../classpath', __FILE__))
