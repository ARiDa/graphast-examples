#!/bin/bash
if [ $# -ne 1 ] && [ $# -ne 2 ] && [ $# -ne 3 ]; then
  echo "Wrong number of arguments. Correct usage: create-graph \"<pbf file> [<target dir>] mmap\""
  echo "Example: create-graph.sh \"src/test/resources/monaco-150112.osm.pbf /tmp/graphast/monaco true\""
else
  ORIGINAL_DIR=$PWD
  APP_DIR="$( cd -P "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
  cd $APP_DIR
  mvn exec:java -Dexec.mainClass="org.graphast.example.GraphHopperImporterExample" -Dexec.args="$@"
  cd $ORIGINAL_DIR
fi
