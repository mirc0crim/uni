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
    s = " "
    if len(inp) == 3:
        for i in range(4):
            newInp = inp[:i] + "." + inp[i:]
            m = re.findall("\'(" + newInp + ")\'", words)
            if m:
                s += str(m)
                s += ", "
        if len(s) > 2:
            print(" Found the following words with an additional letter:")
            print(s.replace("[", "").replace("]", "").replace("'", "")[:-2])
        else:
            print(" No correction found with an additional letter")
    if len(inp) == 4:
        if (re.findall("\'(" + inp + ")\'", words)):
            print(" Word is written correctly")
            return
        newInp = []
        for i in range(4):
            l = len(inp)
            for j in range(4):
                a = list(inp)
                a[i] = inp[l - j - 1]
                a[l - j - 1] = inp[i]
                out = "".join(a)
                if out != inp:
                    newInp.append(out)
        for k in range(len(newInp)):
            m = re.findall("\'(" + newInp[k] + ")\'", words)
            if m:
                s += str(m)
                s += ", "
        if len(s) > 2:
            mDict = fileMK.unsortedDictFromWords(s.split(" "))
            s = str(sorted(mDict.keys())).replace("\"", "")
            print(" Found the following words with 2 switched letters:")
            print("", s.replace("[", "").replace("]", "").replace("'", ""))
        else:
            print(" No correction found with 2 switched letters")

def task3():
    # Task 3
    words = fileMK.readFile(outputPath + "task1")
    inp = input(" Enter a Word\n")
    if len(inp) == 4:
        s = " "
        if (re.findall("\'(" + inp + ")\'", words)):
            print(" Word is written correctly")
            return
        for i in range(4):
            newInp = inp[:i] + "." + inp[i+1:]
            m = re.findall("\'(" + newInp + ")\'", words)
            if m:
                s += str(m)
                s += ", "
        if len(s) > 2:
            print(" Found the following words with replaced letters:")
            print(s.replace("[", "").replace("]", "").replace("'", "")[:-2])
        else:
            print(" No correction found with replaced letters")
        s = " "
        a = fileMK.permutationString(inp).split(" ")
        for i in range(len(a)):
            m = re.findall("\'(" + a[i] + ")\'", words)
            if m:
                s += str(m)
                s += ", "
        if len(s) > 2:
            mDict = fileMK.unsortedDictFromWords(s.split(" "))
            s = str(sorted(mDict.keys())).replace("\"", "")
            print(" Found the following words with switched letters:")
            print("", s.replace("[", "").replace("]", "").replace("'", ""))
        else:
            print(" No correction found with switched letters")


print("Path to Federalist Hamilton " + fedHamPath)
print("Path to Output " + outputPath)
choose()
