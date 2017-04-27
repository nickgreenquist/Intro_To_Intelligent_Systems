#!/usr/bin/python
# Author: Alex Hedges
# This is written for Python 3.6.1.
# Usage: statToCsv.py resultDirectory outputFile type
# resultDirectory: directory of files in stat format
# outputFile: file to store CSV file format
# type: type of data being extracted, can be either "test" or "train"
# This will not work properly if a different input format is used.

import linecache
import os
import re
import sys

USAGE_MESSAGE = "Usage: statToCsv.py resultDirectory outputFile type"
INPUT_REGEX = r"^Classifier= (\S*)$"

TESTS=["TSTAdaBoost.NC-C", "TSTC45-C", "TSTChi-RW-C", "TSTFURIA-C", "TSTGAssist-Intervalar-C", "TSTGFS-GCCL-C", "TSTRipper-C"]
DATASETS=["abalone", "australian", "balance", "bupa", "car", "chess", "coil2000", "contraceptive", "ecoli", "flare", "german", "glass", "haberman", "heart", "ionosphere", "iris", "led7digit", "lymphography", "monk-2", "newthyroid", "pima", "ring", "saheart", "splice", "thyroid", "tic-tac-toe", "titanic", "vehicle", "vowel", "wine"]

def processFile(fileName: str, type: str) -> (str, float):
	datasetLine = linecache.getline(fileName, 3).strip()
	if type == "test":
		scoreLine = linecache.getline(fileName, 19).strip()
	elif type == "train":
		scoreLine = linecache.getline(fileName, 42).strip()
	else:
		raise RuntimeError("That is not a valid experiment type!")
	dataset = re.search(INPUT_REGEX, datasetLine, re.M|re.I).group(1)
	score = float(scoreLine) * 100
	return (dataset, score)

def main():
	if len(sys.argv) != 4: # Test for arguments
		print(USAGE_MESSAGE)
		sys.exit()
	resultDirectory = os.path.join(os.getcwd(), sys.argv[1])
	if not os.path.isdir(resultDirectory):
		raise RuntimeError("The given directory does not exist!")
	outputFile = os.path.join(os.getcwd(), sys.argv[2])
	type = sys.argv[3]
	results = {} # Initialize dictionary
	for dataset in DATASETS:
		results[dataset] = {}
	for test in TESTS: # Read results into dictionary
		testDir = os.path.join(resultDirectory, test)
		for i in range(0, len(os.listdir(testDir))):
			dataset, score = processFile(os.path.join(testDir, "result{}s0.stat".format(i)), type)
			results[dataset][test] = score
	with open(outputFile, "w", encoding="utf8") as output:
		output.write("Dataset") # Write header row
		for test in TESTS:
			output.write(" ; {}".format(test))
		output.write("\n")
		for dataset in DATASETS: # Write individual rows
			output.write(dataset)
			for test in TESTS:
				output.write(" ; {}".format(results[dataset][test]))
			output.write("\n")

if __name__ == "__main__":
    main()