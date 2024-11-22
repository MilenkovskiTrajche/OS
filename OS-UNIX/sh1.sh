#!/bin/bash

#Да се пребараат сите датотеки во специфициран фолдер според внесената екстензија
# и да ги испечати имињата на сите датотеки со таа екстензија и колку линии текст секоја датотеки има во себе
#

searchDirectory="$1"
extension="$2"

if [ -z "$searchDirectory" ] || [ -z "$extension" ]; then
	echo "invalid numeber of arguments"
	exit 1
fi

if [ ! -d "$searchDirectory" ]; then
	echo "'$searchDirectory' does not exists"
	exit 1
fi 

case "$extension" in
	"txt")
		ext='.txt'
		;;
	"sh")
		ext='.sh'
		;;
	"dat")
		ext='.dat'
		;;
	*)
		echo "Invalid extension"
		exit 1	
		;;
esac

find "$searchDirectory" -type f -name "*$ext" | while read -r file; do
	counter=$(grep -c ''  "$file")
	echo "$file - Lines: $counter"
done
