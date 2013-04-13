#!C:/Python33/python.exe -u
# -*- coding: ascii -*-
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
sorted_dict = fileMK.sortedDictFromWords(g)
s = ""
for i in range(100):
    s += str(sorted_dict[i][0]) + "\n"
fileMK.writeTextToFile(s, outputPath + "task1")

# TODO: t-Test with 5% significance    
