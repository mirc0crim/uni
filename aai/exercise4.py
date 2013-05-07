import Image
import numpy
import os
import random
import re
import scipy
from numpy import *

hash = []
names = []

inc = []
char = []
sect = []
corr = 0
fals = 0
resize = (8,8)

outPath = "D:\\aai\\apa\\output\\"

def init(f):
    global inc, char, sect
    
    a = open(f).read()
    inc = re.findall("\.INCLUDE apa/data/([\w/]*)", a)
    inc = inc[0].replace("/","\\")
    char = re.findall("\.SEGMENT CHARACTER .* \? (.*)", a)
    sect = re.findall("\.SEGMENT CHARACTER (.*) \? .*", a)

def output(num):
    global char, hash, inc

    Img2=Image.fromarray(numpy.uint8(makeImage(num)))
    name = inc[-2:] + "-" + str(num) + "-" + char[num].replace("\"","")
    Img2.save(outPath + name + ".png")
    Img2 = Img2.resize(resize, Image.ANTIALIAS)
    c = [k[0] for k in list(Img2.getdata())]
    hash.append(calcDHash(c))
    names.append(name + ".png")

def compareMe(num):
    global char, hash, corr, fals

    Img2=Image.fromarray(numpy.uint8(makeImage(num)))
    Img2.save(outPath + "test-" + char[num].replace("\"","") + ".png")
    Img2 = Img2.resize(resize, Image.ANTIALIAS)
    c = [k[0] for k in list(Img2.getdata())]
    diff = compHash(calcDHash(c), hash)

    n = char[num].replace("\"","")
    outText = "test-" + n +".png matched " + names[argmin(diff)]
    if (n == names[argmin(diff)][-5]):
        outText += " correctly"
        corr += 1
    else:
        outText += " falsely"
        fals += 1
    print outText, "w/ dist", str(min(diff))

def imgCompare():
    Img3 = Image.open("D:\\aai\\apa\\hand.png")
    Img3 = Img3.resize(resize, Image.ANTIALIAS)
    c = [k[0] for k in list(Img3.getdata())]
    diff = compHash(calcDHash(c), hash)
    print diff
    outText = "hand.png matched " + names[argmin(diff)]
    print outText, "w/ dist", str(min(diff))
    
def compHash(thisHash, hash):
    diff = []
    for i in range(len(hash)):
        d = 0
        for j in range(len(hash[i])):
            if hash[i][j] <> thisHash[j]:
                d += 1
        diff.append(d)
    return diff

def makeImage(num):
    global sect, inc
    
    b = []
    r = []
    if sect[num].find("-") != -1:
        sect1 = sect[num][:sect[num].find("-")]
        sect2 = sect[num][sect[num].find("-")+1:]
    else:
        sect1 = sect[num]
        sect2 = -1
    a = open("D:\\aai\\apa\\data\\" + inc + ".dat").read()
    pe = re.findall("\.PEN_DOWN([.*\d*\n*\s*]*)\.PEN_UP", a)
    asdf1 = re.findall("[\s*](\d{1,})[\s*](\d{1,})", pe[int(sect1)])
    for i in range(len(asdf1)):
        if len(asdf1[i]) > 0:
            r.append(asdf1[i])
    if sect2 != -1:
        asdf2 = re.findall("[\s*](\d{1,})[\s*](\d{1,})", pe[int(sect2)])
        for i in range(len(asdf2)):
            if len(asdf2[i]) > 0:
                r.append(asdf2[i])

    maxX = max([int(k[0]) for k in r])
    maxY = max([int(k[1]) for k in r])
    minX = min([int(k[0]) for k in r])
    minY = min([int(k[1]) for k in r])

    for i in range(max(maxX-minX + 1, 9)):
        a = []
        for j in range(max(maxY-minY + 1, 9)):
            a.append([255,255,255])
        b.append(a)
    for i in range(len(r)):
        b[int(r[i][0]) - minX][int(r[i][1]) - minY] = [0,0,0]
        if int(r[i][0]) - minX - 1 >= 0:
            b[int(r[i][0]) - minX - 1][int(r[i][1]) - minY] = [0,0,0]
        if int(r[i][1]) - minY - 1 >= 0:
            b[int(r[i][0]) - minX][int(r[i][1]) - minY - 1] = [0,0,0]
        if maxX - int(r[i][0]) - 1 >= 0:
            b[int(r[i][0]) - minX + 1][int(r[i][1]) - minY] = [0,0,0]
        if maxY - int(r[i][1]) - 1 >= 0:
            b[int(r[i][0]) - minX][int(r[i][1]) - minY + 1] = [0,0,0]
    
    return b

def calcDHash(w):
    bits = []
    for i in range(len(w)-1):
        if w[i] < w[i+1]:
            bits.append(1)
        else:
            bits.append(0)
    return bits

for root, dirs, files in os.walk("D:\\aai\\apa\\apa\\"):
    for currFile in files:
        init(root + "\\" + currFile)
        if "apa19" not in str(root) and "apa20" not in str(root):
            print "train", root + "\\" + currFile
            for i in range(len(char)):
                output(i)
        else:
            print "\ntest", root + "\\" + currFile
            for i in range(len(char)):
                compareMe(i)

imgCompare()

print "\ncorrect vs. false" 
print "  ", corr, "  :  ", fals
