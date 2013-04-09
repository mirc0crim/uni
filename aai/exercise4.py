import Image
import numpy
import random
import re
import scipy

xsize = 1000
ysize = 500

def output(num):
    b = []
    r = []
    m = open("apa\\apa\\apa00\\app0032.dat")
    a = m.read()
    inc = re.findall("\.INCLUDE apa/data/([\w/]*)", a)
    inc = inc[0].replace("/","\\")
    char = re.findall("\.SEGMENT CHARACTER .* \? (.*)", a)
    sect = re.findall("\.SEGMENT CHARACTER (.*) \? .*", a)
    sect = sect[1][:sect[1].find("-")]
    f = open("apa\\data\\" + inc + ".dat")
    a = f.read()
    pe = re.findall("\.PEN_DOWN([.*\d*\n*\s*]*)\.PEN_UP", a)
    asdf = re.findall("[\s*](\d{1,})[\s*](\d{1,})", pe[int(sect)])
    for i in range(len(asdf)):
        if len(asdf[i]) > 0:
            r.append(asdf[i])

    for i in range(xsize):
        a = []
        for j in range(ysize):
            a.append([0,0,0])
        b.append(a)

    for i in range(len(r)):
        b[int(r[i][0])][int(r[i][1])] = [255,255,255]

    Img2=Image.fromarray(numpy.uint8(b))
    Img2.save(char[num].replace("\"","") + ".png")

for i in range(4):
    output(i)
