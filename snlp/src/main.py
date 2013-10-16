#!C:/Python27/python.exe -u
# -*- coding: ascii -*-
import string
import helperMK

fedTrainPath = "D:\\uni\\snlp\\FederalistTraining.txt"
fedTestPath = "D:\\uni\\snlp\\FederalistTestAll.txt"
outputPath = "D:\\uni\\snlp\\output\\"
names = ["Hamilton Train:", "Madison Train:", "Jay Train:"]

print "Path to Train Set", fedTrainPath
print "Path to Test Set", fedTestPath
print "Path for Output", outputPath

# [[hamiltonDocs][madisonDocs][JayDocs]]
train = helperMK.authorArray(helperMK.readFile(fedTrainPath))
test = helperMK.readFile(fedTestPath)

feature = [[[],[],[]], [[],[],[]], [[],[],[]]]
for i in range(len(names)):
    print names[i]
    n1 = 0.0
    n2 = 0.0
    d = helperMK.getAZDict(string.lowercase)
    chars = 0.0
    for j in range(len(train[i])):
        train[i][j] = helperMK.extractText(train[i][j])
        n1 += helperMK.getNumberOfToken(train[i][j])
        n2 += helperMK.getNumberOfWordTypes(train[i][j])
        currDict = helperMK.getAZDict(train[i][j])
        for e in string.lowercase:
            d[e] = d[e] + currDict[e]
        chars += len(train[i][j])
    for e in string.lowercase:
        d[e] = d[e] / chars * 26
    feature[i] = [n1/len(train[i]), n2/len(train[i]), d.values()]
    print feature[i]