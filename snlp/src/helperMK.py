# -*- coding: ascii -*-
import math
import re
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
    # -------------------------
    respectPrior = True;
    # -------------------------
    c = 0
    for i in range(len(m)):
        if re.findall("<AUTHOR> Alexander Hamilton </", m[i], di):
            if c < 14 or respectPrior:
                s[0].append(m[i])
                c += 1;
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

def evalProb(mean, std, val):
    # Probability density function 
    div = 1/(std * math.sqrt(2 * math.pi))
    pot = -((val - mean)**2 / (2 * std**2))
    pdf = div * math.e**(pot)
    return pdf

def logSum(array, i):
    s = 0
    for el in array:
        s += math.log(el[i])
    return s

def normalize(array):
    minVal = min(array)
    maxVal = max(array)
    for i in range(len(array)):
        array[i] = (array[i]-minVal)/(maxVal-minVal)
    return array

def getNumberOfWordTypes(t):
    return float(len(Counter(t.split())))

def getNumberOfToken(t):
    return float(len(t.split()))
