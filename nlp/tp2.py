# -*- coding: ascii -*-
import sys
import re
import fileMK

path = "D:\\nlp"

t = []
t = fileMK.readFilesInArray(path)

# Task 1
for i in range(len(t)):
    print "Number of tags in document " + str(i+1)
    print fileMK.countAllTags(t[i])

print "------------------------"

# Task 2
for i in range(len(t)):
    h = fileMK.readAllHeadlines(t[i])
    print "Headlines in document " + str(i+1)
    for j in range(len(h)):
        print h[j]
    print str(len(h)) + " Headlines extracted"

print "------------------------"

# Task 3
search = raw_input("Enter a Docno like \"GH950102-000004\"\n")
for i in range(len(t)):
    d = []
    d = fileMK.splitToDocsArray(t[i])
    s = fileMK.searchTextWithDocno(d, search)
    if s != None:
        print s

print "------------------------"

# Task 4
n = 0
for i in range(len(t)):
    n += fileMK.numOfTokens(t[i], " ")
print str(n) + " tokens in corpus with " + str(len(t)) + " documents"
