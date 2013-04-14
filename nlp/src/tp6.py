#!C:/Python33/python.exe -u
# -*- coding: ascii -*-
import math
import fileMK

outputPath = "D:\\nlp\\output\\"
fedHamPath = "D:\\nlp\\corpus\\Federalist Hamilton.txt"

t = []
t.append(fileMK.readFile(fedHamPath))

print()
print(" ----------------------------------------------------------------")
print(" 100 most Significant Two-Word Phrases (w/o stemming & Stopwords)")
print(" ----------------------------------------------------------------")
print()
print(" Path to Federalist Hamilton " + fedHamPath)
print(" Path to Output " + outputPath)
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
fileMK.writeTextToFile(s, outputPath + "task1")    

print()
print(" ----------------------------------------------------------------")
print(" T-Test w/ alpha 5% -> 1.65")
print(" Total Two-Word-Phrases:", len(dict2words))
print(" ----------------------------------------------------------------")
print()

dict1word = fileMK.unsortedDictFromWords(noStop.split(" "))

for i in range(5):
    print("Two-Word Phrase at rank",i+1 , dict2words[i][0])
    words = dict2words[i][0].split(" ")
    N = len(noStop.split(" "))
    pOne = dict1word[words[0]] / N
    pTwo = dict1word[words[1]] / N
    Mzero = pOne * pTwo
    x = dict2words[i][1] / N
    s2 = x*(1-x)
    t = (x-Mzero) / (math.sqrt(s2/N))
    print(t)