# -*- coding: ascii -*-
import re
import os
import collections
from itertools import permutations
import matplotlib.pyplot as pyplot
from stemming.porter2 import stem

def readFilesInArray(filePath, doc):
    t = []
    dirlist = os.listdir(filePath)
    for fname in dirlist:
        if (fname[0] != "~"):
            if (fname[-3:] != "doc" or doc):
                t.append(readFile(filePath + "\\" + fname))
    return t

def readFile(filePathName):
    if not filePathName[-4:] == ".txt":
        filePathName += ".txt"
    f = open(filePathName)
    return f.read().replace("\n", " ")

def readFileToArray(filePathName):
    if not filePathName[-4:] == ".txt":
        filePathName += ".txt"
    f = open(filePathName)
    return f.read().lower().split("\n")

def countAllTags(text):
    m = re.findall("</(.*?)>", text)
    return len(m)

def readAllHeadlines(text):
    m = re.findall("<TITLE>(.*?)</TITLE>", text)
    if len(m) == 0:
        m = re.findall("<HEADLINE>(.*?)</HEADLINE>", text)
    return m

def splitToDocsArray(test):
    return test.split("<DOCNO>")

def searchTextWithDocno(d, search):
    for i in range(len(d)):
        m = re.findall("^" + search + "</DOCNO>", d[i])
        if m:
            n = re.findall("<TEXT>(.*?)</TEXT>", d[i])
            if n:
                return n[0]
            else:
                n = re.findall("</SOURCE>(.*)", d[i])
                if n:
                    return n[0]

def numOfTokens(text, sep):
    return len(text.split(sep))

def unsortedDictFromWords(words):
    unsorted_dict = collections.defaultdict(int)
    for j in range(len(words)):
        w = replaceSigns(words[j])
        if (len(w) < 1):
            next
        if (not re.findall("<(.*?)>", w) and not re.match("^\d*?$", w)):
            if (w not in unsorted_dict):
                unsorted_dict[w] = 0
            unsorted_dict[w] += 1
    return unsorted_dict

def sortedDictFromWords(words):
    unsorted_dict = unsortedDictFromWords(words)
    return sorted(unsorted_dict.items(), key=lambda item: -item[1])

def replaceSigns(word):
    w = word.replace(",", "").replace(".","").replace("?", "")
    w = w.replace(":", "").replace(";","").replace("!", "").replace("\"", "")
    return w

def writeTextToFile(text, path):
    if not path[-4:] == ".txt":
        path += ".txt"
    f = open(path, "w+")
    f.write(text)
    print(" Text written to File: " + path)
    f.close()

def plotZipf(x, y, t):
    pyplot.plot(x, y, "o")
    pyplot.ylabel("Frequency")
    pyplot.xlabel("Rank")
    pyplot.title(t)
    x20 = max(x)/50
    y20 = max(y)/50
    pyplot.axis([-x20, max(x)+x20, -y20, max(y)+y20])
    pyplot.show()

def get2FollowingTerms(t, s):
    w = []
    for i in range(len(t)):
        w += t[i].lower().split(" ")
    f = []
    for i in range(len(w)):
        if (w[i] == s):
            f.append(w[i+1] + " " + w[i+2])
    return sorted(f)

def stemText(text):
    return stem(text)

def permutationString(myString):
    perms = (p for p in permutations(myString))
    s = ""
    for p in perms:
        s += ''.join(p)
        s += " "
    return s[:-1]

def removeStopWords(text, words):
    if not words:
        words = ["the", "to", "of", "and", "or", "a", "an", "in", "he", "it", "for", "is", "this", "his"]
    print(" Stopwords:", words)
    for w in words:
        text = text.replace(" " + w + " ", " ")
    return text

def extract2gram(text):
    g = []
    t = text.split(" ")
    for i in range(len(t)-1):
        if len(t[i]) > 1 and len(t[i+1]) > 1:
            g.append(t[i] + " " + t[i+1])
    return g
