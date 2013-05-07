import Image
import os
from numpy import *

print "started\n"

hash = []
names = []
corr = 0
fals = 0
resize = (8,8)
trainPath = "D:\\aai\\cedar\\train\\bindigis\\"
testPath1 = "D:\\aai\\cedar\\test\\bindigis\\goodbs\\"
testPath2 = "D:\\aai\\cedar\\test\\bindigis\\bs\\"
    
def compHash(thisHash, hash):
    diff = []
    for i in range(len(hash)):
        d = 0
        for j in range(len(hash[i])):
            if hash[i][j] <> thisHash[j]:
                d += 1
        diff.append(d)
    return diff

def calcDHash(w):
    bits = []
    for i in range(len(w)-1):
        if w[i] < w[i+1]:
            bits.append(1)
        else:
            bits.append(0)
    return bits

def imgHash(fileName):
    global hash, names
    Img = Image.open(trainPath + fileName)
    Img = Img.resize(resize, Image.ANTIALIAS)
    c = [k for k in list(Img.getdata())[::3]]
    hash.append(calcDHash(c))
    names.append(fileName)
    
def compImg(testP, fileName):
    global corr, fals
    Img = Image.open(testP + fileName)
    Img = Img.resize(resize, Image.ANTIALIAS)
    c = [k for k in list(Img.getdata())[::3]]
    diff = compHash(calcDHash(c), hash)
    n = fileName[7:8]
    if (n == names[argmin(diff)][7:8]):
        corr += 1
    else:
        fals += 1

print "training \\train\\bindigis\\"
for file in os.listdir(trainPath):
    if file[-4:] == ".png":
        imgHash(file)

print "\ntesting 1 \\test\\bindigis\\goodbs\\"
for file in os.listdir(testPath1):
    if file[-4:] == ".png":
        compImg(testPath1, file)

print "correct vs. false" 
print "  ", corr, "  :  ", fals
corr = 0
fals = 0

print "\ntesting 2 \\test\\bindigis\\bs\\"
for file in os.listdir(testPath2):
    if file[-4:] == ".png":
        compImg(testPath2, file)

print "correct vs. false" 
print "  ", corr, "  :  ", fals
print "\ndone"