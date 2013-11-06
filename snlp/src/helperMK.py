# -*- coding: ascii -*-
import math
import re
import string
from collections import Counter

di = re.DOTALL|re.IGNORECASE

def readFile(fileName):
    f = open(fileName)
    s = f.read()
    f.close()
    return s.lower()

def authorArray(text):
    s = [[],[],[],[]]
    m = re.findall("<DOCNO>.*?PUBLIUS", text, di)
    for i in range(len(m)):
        if re.findall("<AUTHOR> Alexander Hamilton </", m[i], di):
            s[0].append(m[i])
        elif re.findall("<AUTHOR> James Madison </", m[i], di):
            s[1].append(m[i])
        elif re.findall("<AUTHOR> John Jay </", m[i], di):
            s[2].append(m[i])
        else:
            s[3].append(m[i])
    if len(s[3]) <> 0:
        print s[3]
    return s

def testArray(text):
    m = re.findall("<DOCNO>.*?PUBLIUS", text, di)
    return m

def extractText(text):
    rep = ["DOCNO", "DOCID", "AUTHOR", "TITLE", "SOURCE"]
    for i in range(len(rep)):
        text = re.sub("<" + rep[i] + ">.*?</" + rep[i] + ">", "", text, 0, di)
        text = re.sub("\n\n","\n", text)
    text = re.sub("To the People of the State of New York:\n", "", text, 0, di)
    text = re.sub("PUBLIUS\n", "", text, 0, di)
    return text

def evalPDF(mean, std, val):
    # Probability density function  
    div = 1/(std * math.sqrt(2 * math.pi))
    pot = -((val - mean)**2 / (2 * std**2))
    return div * math.e**(pot)

def getMostFrequentNWords(t, n):
    return Counter(t.split()).most_common(n)

def getNumberOfWordTypes(t):
    return len(Counter(t.split()))

def getNumberOfToken(t):
    return len(t.split())

def getAZDict(text):
    d = {}
    c = Counter(text)
    for e in string.lowercase:
        if c.get(e):
            d[e] = c.get(e)
        else:
            d[e] = 0
    return d