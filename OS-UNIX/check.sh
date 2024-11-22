#!/bin/bash

echo "Vnesete pateka (vklucitelno ime na file/folder)"

read filePath

if [ -e "$filePath" ]; then
 echo "$filePath exists."
 if [ -f "$filePath" ]; then
  echo "$filePath" is file"
 elif [ -d "$filePath" ]; then
  echo "$filePath is directory"
 else 
   echo "$filePath is not a file or a directory."
 fi

else 
   echo "$filePath does not exists"
fi
