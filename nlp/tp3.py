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
    dict = collections.defaultdict(int)
    for i in range(1):
        words = t[i].split(" ")
        for j in range(len(words)):
            w = words[j].replace(",", "").replace(".","")
            if (not re.findall("<(.*?)>", w) and len(w) > 0):
                if (w not in dict):
                    dict[w] = 0
                dict[words[j]] += 1
        print " Words in document " + str(i+1) + ":"
        sorted_dict = sorted(dict.iteritems(), key=operator.itemgetter(1))
        print sorted_dict
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
