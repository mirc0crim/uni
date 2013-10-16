#!C:/Python27/python.exe -u
# -*- coding: ascii -*-
import helperMK

fedTrainPath = "D:\\uni\\snlp\\FederalistTraining.txt"
fedTestPath = "D:\\uni\\snlp\\FederalistTestAll.txt"
outputPath = "D:\\uni\\snlp\\output\\"
print "Path to Train Set", fedTrainPath
print "Path to Test Set", fedTestPath
print "Path for Output", outputPath

# [[hamiltonDocs][madisonDocs][JayDocs]]
train = helperMK.authorArray(helperMK.readFile(fedTrainPath))
test = helperMK.readFile(fedTestPath)
for i in range(3):
    train[i] = helperMK.extractText(train[i])
test = helperMK.extractText(test)
text = ["Hamilton Train:", "Madison Train:", "Jay Train:"]
for i in range(len(text)):
    print text[i]
    print helperMK.getNumberOfToken(train[i])
    print helperMK.getNumberOfWordTypes(train[i])
    print helperMK.getMostFrequentNWords(train[i],5)
    print helperMK.getAZDict(train[i])

print "Test:"
print helperMK.getNumberOfToken(test)
print helperMK.getNumberOfWordTypes(test)
print helperMK.getMostFrequentNWords(test,5)
print helperMK.getAZDict(test)