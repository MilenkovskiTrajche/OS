#!/bin/bash

p1=$1
p2=$2
p3=$3

prosek=$(((p1 + p2 + p3) * 60 / 3))

echo "Average execution time: $prosek"
echo "Count of executions: $#"

if [ "$#" -ge 5 ]; then
	echo "The testing is done"
else
	echo "More testing is needed"
fi
