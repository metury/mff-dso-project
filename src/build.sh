#!/bin/bash

set -ueo pipefail

# This is build for linux, on other OS you may switch : for ;
javac -cp graphs.jar:. cz/cuni/mff/opt/cpp/*.java
java -cp graphs.jar:. cz.cuni.mff.opt.cpp.Main
