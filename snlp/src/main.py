#!C:/Python27/python.exe -u
# -*- coding: ascii -*-
import numpy
import re
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

prior = []
featureMean = [[] for x in range(3)]
featureStd = [[] for x in range(3)]
for author in range(len(names)):
    print names[author]
    numToken = []
    numTypes = []
    lexDiv = []
    punct = [[] for x in range(6)]
    psum = []
    pron = []
    dictCount = helperMK.getAZDict(string.lowercase)
    chars = []
    wordLen = []
    prior.append(len(train[author]))
    for currDoc in range(len(train[author])):
        text = helperMK.extractText(train[author][currDoc])
        numToken.append(helperMK.getNumberOfToken(text))
        numTypes.append(helperMK.getNumberOfWordTypes(text))
        currDict = helperMK.getAZDict(text)
        for letter in string.lowercase:
            dictCount[letter] += currDict[letter]
        for k, punctSign in enumerate([text.count(x) for x in punctMarks]):
            punct[k].append(punctSign)
        psum.append(sum([text.count(punctSign) for punctSign in punctMarks]))
        pron.append(sum([text.count(pronoun) for pronoun in fPersPron])/numToken[-1])
        chars.append(sum(currDict.values()))
        wordLen.append(chars[-1]/numToken[-1])
        lexDiv.append(numToken[-1]/numTypes[-1])
    for letter in string.lowercase:
        dictCount[letter] -= 1
        dictCount[letter] /= sum(chars) / 26.0
    charList = []
    for letter in sorted(dictCount):
        charList.append(dictCount[letter])
    mpunct = []
    spunct = []
    for punctSign in range(len(punct)):
        for punctAuthor in range(len(punct[punctSign])):
            punct[punctSign][punctAuthor] /= float(psum[punctAuthor])
        mpunct.append(numpy.mean(punct[punctSign]))
        spunct.append(numpy.std(punct[punctSign]))
    featureMean[author] = [numpy.mean(lexDiv), numpy.mean(pron), numpy.mean(wordLen)]
    featureMean[author].extend(mpunct)
    featureStd[author] = [numpy.std(lexDiv), numpy.std(pron), numpy.std(wordLen)]
    featureStd[author].extend(spunct)
    print "mean", featureMean[author]
    print "std", featureStd[author]
    print "prior", prior[author]

for doc in range(len(test)):
    text = helperMK.extractText(test[doc])
    noOfToken = helperMK.getNumberOfToken(text)
    lDiv = noOfToken/helperMK.getNumberOfWordTypes(text)
    currDict = helperMK.getAZDict(text)
    chars = sum(currDict.values())
    wLen = chars/noOfToken
    prons = sum([text.count(x) for x in fPersPron])/noOfToken
    punct = [[] for x in range(6)]
    for k, punctSign in enumerate([text.count(x) for x in punctMarks]):
        punct[k] = punctSign
    psum = sum(punct)
    for i in range(len(punct)):
        punct[i] /= float(psum)
    print
    pLD = []
    for author in range(3):
        pLD.append(helperMK.evalProb(featureMean[author][0], featureStd[author][0], lDiv))
    pPron = []
    for author in range(3):
        pPron.append(helperMK.evalProb(featureMean[author][1], featureStd[author][1], prons))
    pWL = []
    for author in range(3):
        pWL.append(helperMK.evalProb(featureMean[author][2], featureStd[author][2], wLen))
    pPunct = [[] for x in range(6)]
    for i in range(3,9):
        for author in range(3):
            pPunct[i-3].append(helperMK.evalProb(featureMean[author][i], featureStd[author][i], punct[i-3]))
    probs = []
    for author in range(3):
        punc = 1;
        for punctSign in range(6):
            punc *= pPunct[punctSign][author]
        probs.append(pLD[author] * pPron[author] * pWL[author] * punc * prior[author]/float(sum(prior)))
    print "Document Number", re.search("(?<=docno>).*?(?=</docno>)", test[doc]).group()
    print "H", int(1000*probs[0]/sum(probs))/10.0, "%"
    print "M", int(1000*probs[1]/sum(probs))/10.0, "%"
    print "J", int(1000*probs[2]/sum(probs))/10.0, "%"
    
