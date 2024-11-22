#!/bin/bash
echo "vnesi broj"

read n

if [ $n -gt 0 ]; then
 echo "Pozitiven"
elif [ $n -lt 0 ]; then
 echo "Negativen"
else
 echo "Brojot e nula"
fi
