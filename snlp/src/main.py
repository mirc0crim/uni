#!C:/Python27/python.exe -u
# -*- coding: ascii -*-
import math
import numpy
import random
import re
import string
import time
import helperMK

startTime = time.time()

fedTrainPath = "D:\\uni\\snlp\\FederalistTraining.txt"
fedTestPath = "D:\\uni\\snlp\\FederalistTestAll.txt"
outputPath = "D:\\uni\\snlp\\output\\"
names = ["Hamilton Train:", "Madison Train:", "Jay Train:"]
punctMarks = ["?","!",".",";",":",","]
alphabet = [character for character in string.lowercase[:26]]
completeAlphabet = [character for character in string.lowercase[:26]]
probabilistic = True
vowels = False
if probabilistic and not vowels:
    randInt = list(set([random.randint(0,25) for x in range(5)]))
    a = []
    print randInt
    for i in range(len(randInt)):
        a.append(alphabet[randInt[i]])
    alphabet = a
    print alphabet
if vowels and not probabilistic:
    alphabet = ["a","e","i","o","u"]
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
    # TRAINING
    print names[author]
    numToken = []
    numTypes = []
    lexDiv = []
    punct = [[] for x in range(6)]
    psum = []
    pron = []
    characters = [[] for x in range(len(alphabet))]
    charsum = []
    wordLen = []
    sentLen = []
    prior.append(len(train[author]))
    for currDoc in range(len(train[author])):
        text = train[author][currDoc]
        numToken.append(helperMK.getNumberOfToken(text))
        numTypes.append(helperMK.getNumberOfWordTypes(text))
        for k, letter in enumerate([text.count(x) for x in alphabet]):
            characters[k].append(letter)
        charsum.append(sum([text.count(letter) for letter in completeAlphabet]))
        for k, punctSign in enumerate([text.count(x) for x in punctMarks]):
            punct[k].append(punctSign)
        psum.append(sum([text.count(punctSign) for punctSign in punctMarks]))
        pron.append(sum([text.count(pronoun) for pronoun in fPersPron])/numToken[-1])
        wordLen.append(charsum[-1]/numToken[-1])
        sentLen.append(charsum[-1]/float(sum([text.count(x) for x in [".", "!", "?"]]))/wordLen[-1])
        lexDiv.append(numToken[-1]/numTypes[-1])
    meanChar = []
    stdChar = []
    for letter in range(len(characters)):
        for charDoc in range(len(characters[letter])):
            characters[letter][charDoc] *= 26/float(charsum[charDoc])
        meanChar.append(numpy.mean(characters[letter]))
        stdChar.append(numpy.std(characters[letter]))
    meanPunct = []
    stdPunct = []
    for punctSign in range(len(punct)):
        for punctDoc in range(len(punct[punctSign])):
            punct[punctSign][punctDoc] /= float(psum[punctDoc])
        meanPunct.append(numpy.mean(punct[punctSign]))
        stdPunct.append(numpy.std(punct[punctSign]))
    featureMean[author] = [numpy.mean(lexDiv), numpy.mean(pron), numpy.mean(wordLen)]
    featureMean[author].extend(meanPunct)
    featureMean[author].extend(meanChar)
    featureMean[author].append(numpy.mean(sentLen))
    featureStd[author] = [numpy.std(lexDiv), numpy.std(pron), numpy.std(wordLen)]
    featureStd[author].extend(stdPunct)
    featureStd[author].extend(stdChar)
    featureStd[author].append(numpy.std(sentLen))
    print "mean", featureMean[author]
    print "std", featureStd[author]
    print "prior", prior[author]

for doc in range(len(test)):
    # TESTING
    text = test[doc]
    noOfToken = helperMK.getNumberOfToken(text)
    lDiv = noOfToken/helperMK.getNumberOfWordTypes(text)
    prons = sum([text.count(x) for x in fPersPron])/noOfToken
    punct = [[] for x in range(6)]
    for k, punctSign in enumerate([text.count(x) for x in punctMarks]):
        punct[k] = punctSign
    psum = sum(punct)
    for punctSign in range(len(punct)):
        punct[punctSign] /= float(psum)
    characters = [[] for x in range(len(alphabet))]
    for k, letter in enumerate([text.count(x) for x in alphabet]):
        characters[k] = letter
    charsum = sum([text.count(x) for x in completeAlphabet])
    for letter in range(len(characters)):
        characters[letter] *= 26/float(charsum)
    wLen = charsum/noOfToken
    sLen = charsum/float(sum([text.count(x) for x in [".", "!", "?"]]))/wLen
    print
    pLD = []
    dLD = []
    for author in range(3):
        pLD.append(helperMK.evalProb(featureMean[author][0], featureStd[author][0], lDiv))
        dLD.append(abs(featureMean[author][0] - lDiv))
    dLD = helperMK.normalize(dLD)
    pPron = []
    dPron = []
    for author in range(3):
        pPron.append(helperMK.evalProb(featureMean[author][1], featureStd[author][1], prons))
        dPron.append(abs(featureMean[author][1] - prons))
    dPron = helperMK.normalize(dPron)
    pWL = []
    dWL = []
    for author in range(3):
        pWL.append(helperMK.evalProb(featureMean[author][2], featureStd[author][2], wLen))
        dWL.append(abs(featureMean[author][2] - wLen))
    dWL = helperMK.normalize(dWL)
    pPunct = []
    dPunct = []
    product = [0 for x in range(3)]
    mySum = [[0 for x in range(3)] for y in range(6)]
    for punctSign in range(3,9):
        for author in range(3):
            product[author] += math.log(helperMK.evalProb(featureMean[author][punctSign], featureStd[author][punctSign], punct[punctSign-3]))
            mySum[punctSign-3][author] = abs(featureMean[author][punctSign] - punct[punctSign-3])
    for punctSign in range(6):
        mySum[punctSign] = helperMK.normalize(mySum[punctSign])
    for author in range(3):
        pPunct.append(math.e**product[author])
        su = 0
        for punctSign in range(6):
            su += mySum[punctSign][author]
        dPunct.append(su)
    pChar = []
    dChar = []
    product = [0 for x in range(3)]
    mySum = [[0 for x in range(3)] for y in range(len(alphabet))]
    for letter in range(9,9+len(alphabet)):
        for author in range(3):
            product[author] += math.log(helperMK.evalProb(featureMean[author][letter], featureStd[author][letter], characters[letter-9]))
            mySum[letter-9][author] = abs(featureMean[author][letter] - characters[letter-9])
    for letter in range(len(alphabet)):
        mySum[letter] = helperMK.normalize(mySum[letter])
    for author in range(3):
        pChar.append(math.e**product[author])
        su = 0
        for letter in range(len(alphabet)):
            su += mySum[letter][author]
        dChar.append(su)
    pSL = []
    dSL = []
    for author in range(3):
        pSL.append(helperMK.evalProb(featureMean[author][9+len(alphabet)], featureStd[author][9+len(alphabet)], sLen))
        dSL.append(abs(featureMean[author][9+len(alphabet)] - sLen))
    dSL = helperMK.normalize(dSL)
    pPrior = []
    for author in range(3):
        pPrior.append(prior[author]/float(sum(prior)))
    probabilities = []
    difference = []
    for author in range(3):
        probabilities.append(helperMK.logSum([pLD, pPron, pWL, pPunct, pChar, pSL, pPrior], author))
        difference.append(dLD[author] + dPron[author] + dWL[author] + dPunct[author] + dChar[author] + dSL[author])
    print "Document Number", re.search("(?<=docno>).*?(?=</docno>)", test[doc]).group()
    eProbabilities = []
    for el in probabilities:
        eProbabilities.append(math.e**el) #recover probabilities
    print "H", int(1000*eProbabilities[0]/sum(eProbabilities))/10.0, "%"
    print "M", int(1000*eProbabilities[1]/sum(eProbabilities))/10.0, "%"
    print "J", int(1000*eProbabilities[2]/sum(eProbabilities))/10.0, "%"
    print
    print "H", difference[0]
    print "M", difference[1]
    print "J", difference[2]
    
print
print
print "Runtime:", time.time()-startTime