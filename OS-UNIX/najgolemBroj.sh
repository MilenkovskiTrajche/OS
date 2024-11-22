#!/bin/bash

#Programa koja naoga najgolem broj od vneseni 3 broja

echo "Vnesi 3 broja"
read n1 n2 n3

max=$n1

if [ $n2 -gt $max ]; then
	max=$n2
fi

if [ $n3 -gt $max ]; then
        max=$n3
fi
echo "Najgolemiot broj e $max"
