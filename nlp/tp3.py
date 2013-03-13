# -*- coding: ascii -*-
import sys
import re
import collections
import operator
import fileMK

path = "D:\\nlp"

t = []
t = fileMK.readFilesInArray(path)

def choose():
        print ""
        print " --------------------------------------------------------------------"
        print " 1 = 50 Terms with highest Frequencies."
        print " 2 = Number of Terms."
        print " 3 = Terms appearing once, twice & three times."
        print " 4 = Number of Tokens in Corpus."
        print " 5 = Verify Zipf's law. Plot and log-log-Plot."
        print " 6 = 15 most Frequent Word Types by Hamilton vs Madison."
        print " Else Exit"
        print " --------------------------------------------------------------------"
        ex = raw_input()
        if (ex == "1"):
                task1()
        if (ex == "2"):
                task2()
        if (ex == "3"):
                task3()
        if (ex == "4"):
                task4()
        if (ex == "5"):
                task5()
        if (ex == "6"):
                task6()

def task1():
    # Task 1
    words = t[0].split(" ")
    sorted_dict = fileMK.sortedDictFromWords(words)
    s = ""
    for i in range(50):
        s += str(sorted_dict[i]) + "\n"
    s = s.replace("(", "").replace(")", "").replace("'","").replace(",",":")
    path = raw_input(" Enter Path & File\n")
    fileMK.writeTextToFile(s, path)
    choose()

def task2():
    # Task 2

    choose()

def task3():
    # Task 3

    choose()

def task4():
    # Task 4

    choose()

def task5():
    # Task 5

    choose()

def task6():
    # Task 6

    choose()

choose()
