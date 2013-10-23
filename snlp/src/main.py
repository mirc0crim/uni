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
print

# [[hamiltonDocs][madisonDocs][JayDocs]]
train = helperMK.authorArray(helperMK.readFile(fedTrainPath))
test = helperMK.readFile(fedTestPath)

feature = [[],[],[]]
for author in range(len(names)):
    print names[author]
    numToken = 0.0
    numTypes = 0.0
    punct = [0,0,0,0,0,0]
    psum = 0.0
    pron = 0.0
    dictCount = helperMK.getAZDict(string.lowercase)
    chars = 0.0
    for currDoc in range(len(train[author])):
        train[author][currDoc] = helperMK.extractText(train[author][currDoc])
        numToken += helperMK.getNumberOfToken(train[author][currDoc])
        numTypes += helperMK.getNumberOfWordTypes(train[author][currDoc])
        currDict = helperMK.getAZDict(train[author][currDoc])
        for e in string.lowercase:
            dictCount[e] += currDict[e]
        for k, e in enumerate([train[author][currDoc].count(x) for x in punctMarks]):
            punct[k] += e
        psum += sum([train[author][currDoc].count(x) for x in punctMarks])
        pron += sum([train[author][currDoc].count(x) for x in fPersPron])
        chars += sum(currDict.values())
    for e in string.lowercase:
        dictCount[e] -= 1
        dictCount[e] /= chars / 26
    charList = []
    for e in sorted(dictCount):
        charList.append(dictCount[e])
    for currDoc in range(6):
        punct[currDoc] /= psum
    pron /= numToken
    wordLen = chars / numToken
    feature[author] = [numToken/numTypes, charList, punct, pron, wordLen]
    print feature[author]