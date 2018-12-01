#!/bin/sh

#FXML_PATH="../src/main/resources/"

function generate() {
    echo "Generating ${2}${4}.${3}"
    while IFS= read -r line; do
         local text=${line//temp/$4}
         echo $text
         echo "$text" >> "${2}${4}${5}.${3}"
    done < ${1}
}


generate "template1" "../src/main/resources/fxml/" "fxml" "$1" ""
generate "template2" "../src/main/resources/style/" "css" "$1" ""
generate "template3" "../src/main/java/main/gui/noteuis/" "java" "$1" "Pane"
