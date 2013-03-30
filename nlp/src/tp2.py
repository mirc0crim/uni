#!C:/Python33/python.exe -u
# -*- coding: ascii -*-
import fileMK

path = "D:\\nlp\\corpus"

t = []
t = fileMK.readFilesInArray(path, True)

def choose():
        print("")
        print(" --------------------------------------------------------------------")
        print(" 1 = Number of Tags in Document.")
        print(" 2 = Headlines/Titles in Document.")
        print(" 3 = Text of Docno.")
        print(" 4 = Number of Tokens in Corpus.")
        print(" Else Exit")
        print(" --------------------------------------------------------------------")
        ex = input()
        if (ex == "1"):
                task1()
        if (ex == "2"):
                task2()
        if (ex == "3"):
                task3()
        if (ex == "4"):
                task4()

def task1():
    # Task 1
    for i in range(len(t)):
        print(" Number of tags in document " + str(i+1) + ":")
        print(fileMK.countAllTags(t[i]))
    choose()

def task2():
    # Task 2
    for i in range(len(t)):
        h = fileMK.readAllHeadlines(t[i])
        print(" Headlines in document " + str(i+1) + ":")
        for j in range(len(h)):
            print(h[j])
        print("\n " + str(len(h)) + " Headlines extracted")
    choose()

def task3():
    # Task 3
    search = input(" Enter a Docno like \"GH950102-000004\" or \"51\"\n")
    for i in range(len(t)):
        d = []
        d = fileMK.splitToDocsArray(t[i])
        s = fileMK.searchTextWithDocno(d, search)
        if s != None:
            print(s)
        else:
            print(" No Document found")
    choose()

def task4():
    # Task 4
    n = 0
    for i in range(len(t)):
        n += fileMK.numOfTokens(t[i], " ")
    print(" " + str(n) + " tokens in corpus with " + str(len(t)) + " documents")
    choose()

choose()
