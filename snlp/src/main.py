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


feature = [[[],[],[],[]], [[],[],[],[]], [[],[],[],[]]]
for i in range(len(names)):
    print names[i]
    n1 = 0.0
    n2 = 0.0
    n3 = 0.0
    d = helperMK.getAZDict(string.lowercase)
    for j in range(len(train[i])):
        train[i][j] = helperMK.extractText(train[i][j])
        n1 += helperMK.getNumberOfToken(train[i][j])
        n2 += helperMK.getNumberOfWordTypes(train[i][j])
        #n3 += helperMK.getMostFrequentNWords(train[i][j],5)
        currDict = helperMK.getAZDict(train[i][j])
        for e in string.lowercase:
            d[e] = d[e] + currDict[e]
    for e in string.lowercase:
        d[e] = d[e] / float(len(train[i]))
    feature[i] = [n1/len(train[i]),n2/len(train[i]),n3/len(train[i]), d.values()]
    print feature[i]
