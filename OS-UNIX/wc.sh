#!/bin/bash

echo "Vnesi datoteka"

read foldername

if [ -d "$foldername" ]; then
	count=$(find "$foldername" -type f | wc -l)
        echo "Brojot na file-ovi vo direktoriumot $foldername e $count"
else
  	echo "$foldername ne e validen direktorium"
fi
