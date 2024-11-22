#!/bin/bash


#Да се напише процедура која што добива еден параметар кој претставува име на датотека (.txt).
# Процедурата треба да ги избори по колку пати се појавува секој збор во датотеката и да ги испечати резултатите сортирано лексикографски
#
if [ $# -ne 1 ]; then
	echo "Nema parametri"
	exit 1
fi

if [ ! -f "$1" ]; then
	echo "Treba da e datoteka"
	exit 1
fi

awk '{ for(i=1;i<=NF;i++) words[$i]++ } END { for(word in words) print word, words[word] }' "$1" | sort
