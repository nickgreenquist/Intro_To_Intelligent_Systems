#!/bin/sh
# Source: http://stackoverflow.com/a/29248777/2445901
mkdir ResultFiles
echo "Started learning: $(date)" > progress
for D in *; do
    if [ -d "${D}" ]; then
        echo "${D}"
        cd "$D"/scripts/
        java -jar RunKeel.jar
        cd ../../
        cp -r "$D"/results/Vis-Clas-Check/* ResultFiles
        echo "Finished with $D: $(date)" >> progress
    fi
done
echo "Finished learning: $(date)" >> progress
echo -e "\n"
cat progress