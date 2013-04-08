import Image
import numpy
import random
import re
import scipy

b = []
xsize = 1000
ysize = 500
r = []

f = open("0.dat")
for line in f:
    if line[0] != ".":
        asdf = re.findall("[\s*](\d*)[\s*](\d*)", line)
        if len(asdf) > 0:
            r.append(asdf[0])
f.close()

for i in range(xsize):
    a = []
    for j in range(ysize):
        a.append([0,0,0])
    b.append(a)

for i in range(len(r)):
    b[int(r[i][0])][int(r[i][1])] = [255,255,255]

Img2=Image.fromarray(numpy.uint8(b))
Img2.save("tdst.png")
