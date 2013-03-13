# -*- coding: ascii -*-
import re
import os
import collections
from pylab import *

def readFilesInArray(filePath, doc):
    t = []
    dirlist = os.listdir(filePath)
    for fname in dirlist:
        if (fname[0] != "~"):
            if (fname[-3:] != "doc" or doc):
                t.append(readFile(filePath + "\\" + fname))
    return t

def readFile(filePathName):
    f = open(filePathName)
    return f.read().replace("\n", " ")

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
    return sorted(unsorted_dict.iteritems(), key=lambda item: -item[1])

def replaceSigns(word):
    w = word.replace(",", "").replace(".","").replace("?", "")
    w = w.replace(":", "").replace(";","").replace("!", "").replace("\"", "")
    return w

def writeTextToFile(text, path):
    if not path[-4:] == ".txt":
        path += ".txt"
    f = open(path, "w+")
    f.write(text)
    print " Text written to File: " + path
    f.close()

def plotZipf(x, y, t):
    plot(x, y)
    ylabel("Frequency")
    xlabel("Rank")
    title(t)
    show()
