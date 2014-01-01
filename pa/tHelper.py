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

ma = input("ma=?\n")
mb = input("mb=?\n")
va = input("va=?\n")
vb = input("vb=?\n")
print "m=", m(va,vb)
print "t=", t(ma,mb,va,vb)

raw_input("")
