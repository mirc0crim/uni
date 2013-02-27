# -*- coding: ascii -*-
import sys
import re

myFile = raw_input("Enter path to file: \nDefault is same path as this program\n")

if myFile == "":
    myFile = "1.txt"
f = open(myFile)
t = f.read()


f = open("C:/Users/Mirco Kocher/Desktop/1.txt")
t = f.read()
t = t.replace("\n","")
a = 0
"""
print t.count("<DOCNO>")
m = re.search("(<(?P<name>.....)>)(\W*\w*)*?(</(?P=name)>)", t)
print m.group()
t = t.replace(m.group(), "")
print t.count("<DOCNO>")
m = re.search("(<(?P<name>.....)>)(\W*\w*)*?(</(?P=name)>)", t)
print m.group()
m = re.search("(</(\W*\w*)*?>)", t)
print m.group()
"""

m = re.search("(</(\W*\w*)*?>)", t)
while (m):
    a+=1
    t = t.replace(m.group(), "", 1)
    m = re.search("(</(\W*\w*)*?>)", t)

print a
