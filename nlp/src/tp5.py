#!C:/Python33/python.exe -u
# -*- coding: ascii -*-
import re
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
    print(" ----------------------------------------------------------------")
    print(" 1 = 4 letter Words without stemming from Hamilton.")
    print(" 2 = Check word using 1 missed letter or switching 2 letters.")
    print(" 3 = Check word using 1 substituted letter or moving any letters.")
    print(" Else Exit")
    print(" ----------------------------------------------------------------")

def task1():
    # Task 1
    a = fileMK.stemText(t[0])
    m = re.findall("[ \(\)<>]([a-zA-Z]{4})[ \(\)<>]", a)
    if m:
        mDict = fileMK.unsortedDictFromWords(m)   
        p = outputPath + "task1"
        fileMK.writeTextToFile(str(sorted(mDict.keys())), p)

def task2():
    # Task 2
    words = fileMK.readFile(outputPath + "task1")
    inp = input(" Enter a Word\n")
    if len(inp) == 3:
        s = " "
        for i in range(4):
            m = re.findall("\'(" + inp[:i] + "." + inp[i:] + ")\'", words)
            if m:
                s += str(m)
                s += ", "
        if len(s) > 2:
            print(" Found the following words with an additional letter:")
            print(s.replace("[", "").replace("]", "").replace("'", "")[:-2])
    if len(inp) == 4:
        print(" Two letter switching test")
    

def task3():
    # Task 3
    print("")


print("Path to Federalist Hamilton " + fedHamPath)
print("Path to Output " + outputPath)
choose()
