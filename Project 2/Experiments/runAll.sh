#!/bin/sh
# Source: http://stackoverflow.com/a/29248777/2445901
mkdir ResultFiles
echo "Started learning: $(date)" > progress
for zip in *.zip
do
  dirname=`echo $zip | sed 's/\.zip$//'`
  unzip "$zip"
  cd "$dirname"/scripts/
  java -jar RunKeel.jar
  cd ../../
  cp -r "$dirname"/results/Vis-Clas-Check/* ResultFiles
  echo "Finished with $dirname: $(date)" >> progress
  # rm -f $zip # Uncomment to delete the original zip file
done
echo "Finished learning: $(date)" >> progress
echo -e "\n"
cat progress