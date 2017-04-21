#!/bin/sh
# Source: http://stackoverflow.com/a/29248777/2445901
mkdir ResultFiles_Nick
echo "Started learning: $(date)" > progress_Nick
for D in *; do
    if [ -d "${D}" ]; then
        echo "${D}"
        cd "$D"/scripts/
        java -jar RunKeel.jar
        cd ../../
        cp -r "$D"/results/Vis-Clas-Check/* ResultFiles_Nick
        echo "Finished with $D: $(date)" >> progress_Nick
    fi
done
echo "Finished learning: $(date)" >> progress_Nick
echo -e "\n"
cat progress