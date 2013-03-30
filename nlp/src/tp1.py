#!C:/Python33/python.exe -u
# -*- coding: ascii -*-
import sys
import re

def choose():
        print("")
        print(" --------------------------------------------------------------------")
        print(" 1 = Numbers from 1 to 100.")
        print(" 2 = Regex for a string containing both \"blue\" & \"green\" somewhere.")
        print(" 3 = Regex for a sentence starting with \"A\" and ending with \"end.\".")
        print(" 4 = Regex for a string containing a number that is a multiple of 5.")
        print(" Else Exit")
        print(" --------------------------------------------------------------------")
        ex = input()
        if (ex == "1"):
                ex1()
        if (ex == "2"):
                ex2()
        if (ex == "3"):
                ex3()
        if (ex == "4"):
                ex4()

def ex1():
        print("")
        sys.stdout.write(" ")
        for i in range(1,101):
                if (i<10):
                        sys.stdout.write(" " + str(i) + " ")
                else:
                        sys.stdout.write(str(i) + " ")
                if (i%10 == 0):
                        sys.stdout.write("\n ")
        choose()

def ex2():
        print("")
        line = input(" Enter String\n")
        m = re.search("(blue|green)(\W*\w*)*(blue|green)" , line)
        if m:
                print(" String found: " + m.group())
        else:
                print(" No match found")
        choose()

def ex3():
        print("")
        line = input(" Enter Sentence\n")
        m = re.search("^A(\W*\w*)*end\.$" , line)
        if m:
                print(" Sentence found: " + m.group())
        else:
                print(" No match found")
        choose()

def ex4():
        print("")
        line = input(" Enter String\n")
        m = re.search("[0-9]*[0,5]" , line)
        if m:
                print(" String found: " + m.group())
        else:
                print(" No match found")
        choose()

choose()
