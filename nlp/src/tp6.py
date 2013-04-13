#!C:/Python33/python.exe -u
# -*- coding: ascii -*-
import fileMK

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

def printInfo():
    print("")
    print(" ----------------------------------------------------------------")
    print(" 1 = 100 most Significant Two-Word Phrases.")
    print(" Else Exit")
    print(" ----------------------------------------------------------------")

def task1():
    # Task 1
    # TODO: rem punctuation; stem; stop words; t-Test with 5% significance
    a = fileMK.stemText(t[0])


print("Path to Federalist Hamilton " + fedHamPath)
print("Path to Output " + outputPath)
choose()
