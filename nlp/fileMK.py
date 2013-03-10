# -*- coding: ascii -*-
import re
import os

def readFilesInArray(filePath):
    t = []
    dirlist = os.listdir(filePath)
    for fname in dirlist:
        if (fname[0] != "~"):
            f = open(filePath + "\\" + fname)
            t.append(f.read().replace("\n", " "))
    return t

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
