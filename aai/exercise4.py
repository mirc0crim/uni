import Image
import numpy
import random
import re
import scipy

xsize = 1000
ysize = 500

m = open("apa\\apa\\apa00\\app0034.dat")
a = m.read()
inc = re.findall("\.INCLUDE apa/data/([\w/]*)", a)
inc = inc[0].replace("/","\\")
char = re.findall("\.SEGMENT CHARACTER .* \? (.*)", a)
sect = re.findall("\.SEGMENT CHARACTER (.*) \? .*", a)

def output(num, inc, char, sect):
    b = []
    r = []
    if sect[num].find("-") != -1:
        sect1 = sect[num][:sect[num].find("-")]
        sect2 = sect[num][sect[num].find("-")+1:]
    else:
        sect1 = sect[num]
        sect2 = -1
    f = open("apa\\data\\" + inc + ".dat")
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
            a.append([0,0,0])
        b.append(a)

    for i in range(len(r)):
        b[int(r[i][0]) - minX][int(r[i][1]) - minY] = [255,255,255]

    Img2=Image.fromarray(numpy.uint8(b))
    Img2.save(str(num) + "-" + char[num].replace("\"","") + ".png")
    print "saved", str(num) + "-" + char[num].replace("\"","") + ".png"

for i in range(len(char)):
    output(i, inc, char, sect)
