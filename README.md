# Firebase Avro parser plugin for Embulk

This parser plug-in supported to Firebase extracted file of *AVRO* format from Google-BigQuery.  
Currently (2017/6), Firebase could extract only GCS(Google Cloud Storage) to JSON or AVRO format.  
If you want to export Firebase's data, it has to need data pipelining for in the below steps.

1. Link BigQuery with Firebase.  
    - https://support.google.com/firebase/answer/6318765  
1. Dump Firebase Data to Google Cloud Storage.  
    - Use data pipelining with job scheduler and extract data from BigQuery.  
    - https://support.google.com/firebase/answer/7029846  
    
1. Use input-gcs plugin with this parser-plugin.
    - https://github.com/embulk/embulk-input-gcs  


## Overview

* **Plugin type**: parser
* **Guess supported**: no

## Configuration
- No configuration

## Example

```yaml
in:
  type: any file input plugin type
  parser:
    type: firebase_avro
```


```
$ embulk gem install embulk-parser-firebase_avro
```

## Build

```
$ ./gradlew gem  # -t to watch change of files and rebuild continuously
```

## Developing or Testing

This plug-in is written by Scala. You could use sbt.

```
$ ./sbt 
$ ./sbt test
```