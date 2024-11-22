#!/bin/bash


if [ -z "$1" ]; then
	echo "Insert name or file!"
	exit 1
fi

if [ $# -ne 1 ]; then
	echo "Too many input arguments"
	exit 1
fi

for file in $(find . -type f -name "*.txt")
do
	if [ -r "$file" ]; then
		cat "$file" >> "$1"
		echo >> "$1"
	fi

done
