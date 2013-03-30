#!C:/Python33/python.exe -u
# -*- coding: ascii -*-
import fileMK

corpusPath = "D:\\nlp\\corpus\\"
outputPath = "D:\\nlp\\output\\"
fedHamPath = "D:\\nlp\\corpus\\Federalist Hamilton.txt"

t = []
t.append(fileMK.readFile(fedHamPath))

def choose():
    printInfo()
    ex = input()
    if (ex == "1"):
        task1()
        choose()
    elif (ex == "2"):
        task2()
        choose()
    elif (ex == "3"):
        task3()
        choose()

def printInfo():
    print("")
    print(" ------------------------------------------------------------------")
    print(" 1 = 4 letter Words without stemming from Hamilton.")
    print(" 2 = Check word using 1 missed letter or switching 2 letters.")
    print(" 3 = Check word using 1 substituted letter or moving any letters.")
    print(" Else Exit")
    print(" ------------------------------------------------------------------")

def task1():
    # Task 1
    print(fileMK.stemText(t[0]))

def task2():
    # Task 2
    print("")

def task3():
    # Task 3
    print("")


print("Path to Federalist Hamilton " + fedHamPath)
print("Path to Output " + outputPath)
choose()
