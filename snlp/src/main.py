#!C:/Python27/python.exe -u
# -*- coding: ascii -*-
import string
import helperMK

fedTrainPath = "D:\\uni\\snlp\\FederalistTraining.txt"
fedTestPath = "D:\\uni\\snlp\\FederalistTestAll.txt"
outputPath = "D:\\uni\\snlp\\output\\"
names = ["Hamilton Train:", "Madison Train:", "Jay Train:"]
punctMarks = ["?","!",".",";",":",","]
fPersPron = ["i","me","my","mine","we","us","our","ours"]

print "Path to Train Set", fedTrainPath
print "Path to Test Set", fedTestPath
print "Path for Output", outputPath

# [[hamiltonDocs][madisonDocs][JayDocs]]
train = helperMK.authorArray(helperMK.readFile(fedTrainPath))
test = helperMK.readFile(fedTestPath)

feature = [[[],[],[]], [[],[],[]], [[],[],[]]]
for i in range(len(names)):
    print names[i]
    numToken = 0.0
    numTypes = 0.0
    punct = 0.0
    pron = 0.0
    dictCount = helperMK.getAZDict(string.lowercase)
    chars = 0.0
    docSize = len(train[i])
    for j in range(docSize):
        train[i][j] = helperMK.extractText(train[i][j])
        numToken += helperMK.getNumberOfToken(train[i][j])
        numTypes += helperMK.getNumberOfWordTypes(train[i][j])
        currDict = helperMK.getAZDict(train[i][j])
        for e in string.lowercase:
            dictCount[e] = dictCount[e] + currDict[e]
        punct += sum([train[i][j].count(x) for x in punctMarks]) #make 6 dim vector
        pron += sum([train[i][j].count(x) for x in fPersPron])
        chars += sum(currDict.values())
    for e in string.lowercase:
        dictCount[e] /= chars / 26
    numToken /= docSize
    numTypes /= docSize
    punct /= docSize
    pron /= docSize * numToken
    feature[i] = [numToken, numTypes, dictCount.values(), punct, pron]
    print feature[i]