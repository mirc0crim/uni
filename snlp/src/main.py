#!C:/Python27/python.exe -u
# -*- coding: ascii -*-
import numpy
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
test = helperMK.testArray(helperMK.readFile(fedTestPath))

featureMean = [[],[],[]]
featureStd = [[],[],[]]
for author in range(len(names)):
    print names[author]
    numToken = []
    numTypes = []
    lexDiv = []
    punct = [[],[],[],[],[],[]]
    psum = []
    pron = []
    dictCount = helperMK.getAZDict(string.lowercase)
    chars = []
    wordLen = []
    for currDoc in range(len(train[author])):
        train[author][currDoc] = helperMK.extractText(train[author][currDoc])
        numToken.append(helperMK.getNumberOfToken(train[author][currDoc]))
        numTypes.append(helperMK.getNumberOfWordTypes(train[author][currDoc]))
        currDict = helperMK.getAZDict(train[author][currDoc])
        for e in string.lowercase:
            dictCount[e] += currDict[e]
        for k, e in enumerate([train[author][currDoc].count(x) for x in punctMarks]):
            punct[k].append(e)
        psum.append(sum([train[author][currDoc].count(x) for x in punctMarks]))
        pron.append(sum([train[author][currDoc].count(x) for x in fPersPron]))
        chars.append(sum(currDict.values()))
        wordLen.append(chars[-1]/float(numToken[-1]))
        lexDiv.append(numToken[-1]/float(numTypes[-1]))
    for e in string.lowercase:
        dictCount[e] -= 1
        dictCount[e] /= sum(chars) / 26.0
    charList = []
    for e in sorted(dictCount):
        charList.append(dictCount[e])
    mpunct = []
    spunct = []
    for i in range(len(punct)):
        for j in range(len(punct[i])):
            punct[i][j] /= float(psum[j])
        mpunct.append(numpy.mean(punct[i]))
        spunct.append(numpy.std(punct[i]))
    featureMean[author] = [numpy.mean(lexDiv), numpy.mean(pron), numpy.mean(wordLen), mpunct]
    featureStd[author] = [numpy.std(lexDiv), numpy.std(pron), numpy.std(wordLen), spunct]
    print "mean", featureMean[author]
    print "std", featureStd[author]

for doc in range(len(test)):    
    text = helperMK.extractText(test[doc])
    lDiv = helperMK.getNumberOfToken(text)/float(helperMK.getNumberOfWordTypes(text))
    currDict = helperMK.getAZDict(text)
    chars = sum(currDict.values())
    wLen = chars/float(helperMK.getNumberOfToken(text))
    print
    print
    mode = 1
    if mode == 1:
        pdf1 = helperMK.evalPDF(featureMean[0][0], featureStd[0][0], lDiv)
        pdf2 = helperMK.evalPDF(featureMean[1][0], featureStd[1][0], lDiv)
        pdf3 = helperMK.evalPDF(featureMean[2][0], featureStd[2][0], lDiv)
    else:
        pdf1 = helperMK.evalPDF(featureMean[0][2], featureStd[0][2], wLen)
        pdf2 = helperMK.evalPDF(featureMean[1][2], featureStd[1][2], wLen)
        pdf3 = helperMK.evalPDF(featureMean[2][2], featureStd[2][2], wLen)
    print 100*pdf1 / (pdf1+pdf2+pdf3)
    print 100*pdf2 / (pdf1+pdf2+pdf3)
    print 100*pdf3 / (pdf1+pdf2+pdf3)