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

outPath = "D:\\aai\\apa\\output\\"

def init(f):
    m = open(f)
    global inc, char, sect
    a = m.read()
    inc = re.findall("\.INCLUDE apa/data/([\w/]*)", a)
    inc = inc[0].replace("/","\\")
    char = re.findall("\.SEGMENT CHARACTER .* \? (.*)", a)
    sect = re.findall("\.SEGMENT CHARACTER (.*) \? .*", a)

def output(num):
    global char, sect, hash, inc
    b = makeImage(num)

    Img2=Image.fromarray(numpy.uint8(b))
    name = inc[-2:] + "-" + str(num) + "-" + char[num].replace("\"","")
    Img2.save(outPath + name + ".png")
    Img2 = Img2.resize((10,10), Image.ANTIALIAS)
    c = [k[0] for k in list(Img2.getdata())]
    hash.append(calcDHash(c))
    names.append(name + ".png")
    #print "saved", name

def compareMe(num):
    global char, sect, hash, inc, corr, fals
    b = makeImage(num)

    Img2=Image.fromarray(numpy.uint8(b))
    Img2.save(outPath + "test-" + char[num].replace("\"","") + ".png")
    #Img2.show()
    Img2 = Img2.resize((10,10), Image.ANTIALIAS)
    c = [k[0] for k in list(Img2.getdata())]
    thisHash = calcDHash(c)
    diff = []
    for i in range(len(hash)):
        d = 0
        for j in range(len(hash[i])):
            if hash[i][j] <> thisHash[j]:
                d += 1
        diff.append(d)

    print diff
    n = char[num].replace("\"","")
    outText = "Input test-" + n +".png matched " + names[argmin(diff)]
    if (n == names[argmin(diff)][-5]):
        print outText, "correctly"
        corr += 1
    else:
        print outText, "falsely"
        fals += 1
        print "dHash Hamming distance", str(min(diff))
    #Img1 = Image.open("D:\\aai\\apa\\output\\" + names[num])
    #Img1.show()

def makeImage(num):
    b = []
    r = []
    global sect, inc
    if sect[num].find("-") != -1:
        sect1 = sect[num][:sect[num].find("-")]
        sect2 = sect[num][sect[num].find("-")+1:]
    else:
        sect1 = sect[num]
        sect2 = -1
    f = open("D:\\aai\\apa\\data\\" + inc + ".dat")
    a = f.read()
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

    for i in range(maxX-minX + 1):
        a = []
        for j in range(maxY-minY + 1):
            a.append([255,255,255])
        b.append(a)

    for i in range(len(r)):
        b[int(r[i][0]) - minX][int(r[i][1]) - minY] = [0,0,0]
    
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
        if "apa19" not in str(root) and "apa20" not in str(root):
            print "train", root + "\\" + currFile
            init(root + "\\" + currFile)
            for i in range(len(char)):
                output(i)
        else:
            print "\ntest", root + "\\" + currFile
            init(root + "\\" + currFile)
            for i in range(len(char)):
                compareMe(i)

print "correct vs. false" 
print "    ", corr, " : ", fals
