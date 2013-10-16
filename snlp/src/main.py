#!C:/Python27/python.exe -u
# -*- coding: ascii -*-
import helperMK

fedTrainPath = "D:\\uni\\snlp\\FederalistTraining.txt"
fedTestPath = "D:\\uni\\snlp\\FederalistTestAll.txt"
outputPath = "D:\\uni\\snlp\\output\\"
print "Path to Train Set", fedTrainPath
print "Path to Test Set", fedTestPath
print "Path for Output", outputPath

train = helperMK.readFile(fedTrainPath)
test = helperMK.readFile(fedTestPath)

print "Train:"
print helperMK.getNumberOfToken(train)
print helperMK.getNumberOfWordTypes(train)
print helperMK.getMostFrequentNWords(train,10)

print "Test:"
print helperMK.getNumberOfToken(test)
print helperMK.getNumberOfWordTypes(test)
print helperMK.getMostFrequentNWords(test,10)