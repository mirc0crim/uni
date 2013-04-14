#!C:/Python33/python.exe -u
# -*- coding: ascii -*-
import math
import fileMK

outputPath = "D:\\nlp\\output\\"
fedHamPath = "D:\\nlp\\corpus\\Federalist Hamilton.txt"

t = []
t.append(fileMK.readFile(fedHamPath))

print()
print(" Path to Federalist Hamilton " + fedHamPath)
print(" Path to Output " + outputPath)
print()
print(" ----------------------------------------------------------------")
print(" 100 most Significant Two-Word Phrases (w/o stemming & Stopwords)")
print(" ----------------------------------------------------------------")
print()

lower = t[0].lower()
noPunct = fileMK.replaceSigns(lower)
stemmed = fileMK.stemText(noPunct)
noStop = fileMK.removeStopWords(stemmed, None)
g = fileMK.extract2gram(noStop)
dict2words = fileMK.sortedDictFromWords(g)
s = ""
for i in range(100):
    s += str(dict2words[i][0]) + "\n"
fileMK.writeTextToFile(s, outputPath + "task1a")    

significance = 2.576

print()
print(" ----------------------------------------------------------------")
print(" T-Test w/ alpha 0.5% ->", significance)
print(" Total Two-Word-Phrases:", len(dict2words))
print(" ----------------------------------------------------------------")
print()

dict1word = fileMK.unsortedDictFromWords(noStop.split(" "))

tens = [k for k in dict2words if k[1] == 10]
score = [0,0]
colls = ["", ""]

for i in range(len(tens)):
    words = tens[i][0].split(" ")
    N = len(noStop.split(" "))
    pOne = dict1word[words[0]] / N
    pTwo = dict1word[words[1]] / N
    Mzero = pOne * pTwo
    x = tens[i][1] / N
    s2 = x*(1-x)
    t = (x-Mzero) / (math.sqrt(s2/N))
    if t < significance:
        score[0] += 1
        colls[0] += tens[i][0] + ", "
    else:
        score[1] += 1
        colls[1] += tens[i][0] + ", "

s = str(score[0]) + " no collocation:\n" + colls[0]
s += "\n" + str(score[1]) + " collocation:\n" + colls[1]
fileMK.writeTextToFile(s, outputPath + "task1b")