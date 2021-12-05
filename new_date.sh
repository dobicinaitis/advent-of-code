#!/bin/bash

# Creates files for Day X from Day1 classes, tests, inputs
#
# Usage ./new_date.sh DAY_NUMBER
# Example: ./new_date.sh 2

day_number=$1
template_date=1

if ! [[ $day_number =~ ^[0-9]+$ ]]; then
    echo "Please provide a day number as 1st argument"
    exit 1
fi

if [[ "$day_number" -lt 2 || "$day_number" -gt 24 ]]; then
    echo "Please provide a day number between 2 and 24"
    exit 1
fi

script_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$script_path"

source_directory=src/main/java/dev/dobicinaitis/advent
test_directory=src/test/java/dev/dobicinaitis/advent
input_directory=src/main/resources/inputs

# main
cp -n $source_directory/Day"$template_date".java $source_directory/Day"$day_number".java
sed -i "s/$template_date/$day_number/g" $source_directory/Day"$day_number".java

# tests
cp -n $test_directory/Day"$template_date"Test.java $test_directory/Day"$day_number"Test.java
sed -i "s/$template_date/$day_number/g" $test_directory/Day"$day_number"Test.java

# resources
touch $input_directory/day"$day_number"_example_input.txt
touch $input_directory/day"$day_number"_my_input.txt

echo "Created files for Day $day_number"