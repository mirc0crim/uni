import math

def m(a,b):
        n=40
        top=((a+b)/n)**2
        middle=(a**2+b**2)/n**2
        m=top/(middle/n+1)-2
        return m

def t(ma,mb,va,vb):
	n=40
	top=ma-mb
	bottom=(va+vb)/n
	t=top/math.sqrt(bottom)
	return t

mode = input("1=m,2=t\n")
if mode == 1:
        a = input("ma=?\n")
        b = input("mb=?\n")
        print "m=", m(a,b)
if mode == 2:
        ma = input("ma=?\n")
        mb = input("mb=?\n")
        va = input("va=?\n")
        vb = input("vb=?\n")
        print "t=", t(ma,mb,va,vb)

raw_input("")
