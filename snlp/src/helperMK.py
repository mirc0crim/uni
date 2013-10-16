# -*- coding: ascii -*-
from collections import Counter

def readFile(fileName):
    f = open(fileName)
    s = f.read()
    f.close()
    return s

def getMostFrequentNWords(t, n):
    return Counter(t.split()).most_common(n)

def getNumberOfWordTypes(t):
    return len(Counter(t.split()))

def getNumberOfToken(t):
    return len(t.split())