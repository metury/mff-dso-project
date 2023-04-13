#!/bin/bash

set -ueo pipefail

# This is build for linux, on other OS you may switch : for ;
javac -cp graphs.jar:. cuni/mff/maximalbipartitematching/*.java
java -cp graphs.jar:. cuni.mff.maximalbipartitematching.Main > RESULTS.txt
