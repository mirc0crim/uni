#!C:/Python33/python.exe -u
# -*- coding: ascii -*-
import re
import math
import fileMK

corpusPath = "D:\\nlp\\corpus\\"
outputPath = "D:\\nlp\\output\\"

doc = False

t = []
t = fileMK.readFilesInArray(corpusPath, doc)

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
    elif (ex == "4"):
        task4()
        choose()
    elif (ex == "5"):
        task5()
        choose()
    elif (ex == "6"):
        task6()
        choose()
    elif (ex == "7"):
        toggleGH()
        choose()

def printInfo():
    print("")
    print(" ------------------------------------------------------------------")
    print(" 1 = 50 Terms with highest Frequencies.")
    print(" 2 = Number of Terms.")
    print(" 3 = Terms appearing once, twice & three times.")
    print(" 4 = Pattern Matching.")
    print(" 5 = Verify Zipf's law. Plot and log-log-Plot.")
    print(" 6 = 15 most Frequent Word Types by Hamilton vs Madison.")
    print(" 7 = Toggle \"Glasgow Herald\" reading.")
    print(" Else Exit")
    print(" ------------------------------------------------------------------")
    if (doc):
        print(" Creating the Dictionary from \"Glasgow Herald\" in Task 1, 2 &")
        print(" 3 will take about a Minute but Task 4c won't find anything.")
    else:
        print(" \"Glasgow Herald\" will be omitted. Task 1, 2 & 3 will be fast")
        print(" but Task 4c wont' find anything")
    print(" ------------------------------------------------------------------")

def task1():
    # Task 1
    words = []
    for i in range(len(t)):
        words += (t[i].split(" "))
    sorted_dict = fileMK.sortedDictFromWords(words)
    s = ""
    for i in range(50):
        s += str(sorted_dict[i]) + "\n"
    s = s.replace("(", "").replace(")", "").replace("'","").replace(",",":")
    path = input(" Enter Path & Filename (like \"D:\\terms.txt\")\n")
    if path == "":
        path = outputPath + "task1.txt"
    fileMK.writeTextToFile(s, path)

def task2():
    # Task 2
    w = []
    for i in range(len(t)):
        w += t[i].split(" ")
    unsorted_dict = fileMK.unsortedDictFromWords(w)
    s = "There are " + str(len(unsorted_dict)) + " different Terms"
    print(s)
    path = outputPath + "task2.txt"
    fileMK.writeTextToFile(s, path)

def task3():
    # Task 3
    words = []
    for i in range(len(t)):
        words += t[i].lower().split(" ")
    unsorted_dict = fileMK.unsortedDictFromWords(words)
    try:
        i = int(input(" How many times should the term appear?\n"))
    except ValueError:
        print(" Invalid Value, assuming 51\n")
        i = 51
    times = [key for key, value in unsorted_dict.items() if value == i]
    s = ""
    for j in range(len(times)):
        s += str(times[j]) + "; "
    n = str(len(s.split("; "))-1)
    print(" " + n + " Words found occuring " + str(i) + " time(s)")
    path = outputPath + "task3-" + str(i) + ".txt"
    fileMK.writeTextToFile(s, path)

def task4():
    # Task 4
    words = ""
    for i in range(len(t)):
        words += t[i] + "\n"
    # Task a
    print(" More than 3 Non-Space Characters in Brackets.")
    m = re.findall("[(]\S{3,}[)]", words.lower())
    print(str(len(m)) + " Brackets found")
    path = outputPath + "task4a.txt"
    fileMK.writeTextToFile(str(m), path)
    # Task b
    print(" More than 1 consecutive Words in Uppercase.")
    m = re.findall("[A-Z]{2,}\s[A-Z]{2,}[\s[A-Z]{2,}]*", words.lower())
    print(str(len(m)) + " Words found")
    path = outputPath + "task4b.txt"
    fileMK.writeTextToFile(str(m), path)
    # Task c
    print(" Numbers starting with \"#\".")
    m = re.findall("#[0-9,]+", words.lower())
    print(str(len(m)) + " Numbers found")
    path = outputPath + "task4c.txt"
    fileMK.writeTextToFile(str(m), path)
    # Task d
    print(" Terms containing a Hyphen.")
    m = re.findall("[a-zA-Z]{3,}[-][a-zA-Z]{3,}", words.lower())
    print(str(len(m)) + " Terms found")
    path = outputPath + "task4d.txt"
    fileMK.writeTextToFile(str(m), path)

def task5():
    # Task 5
    hamilton = fileMK.readFile(corpusPath + "Federalist Hamilton.txt")
    sorted_hamilton = fileMK.sortedDictFromWords(hamilton.lower().split(" "))
    x = []
    y = []
    for i in range(len(sorted_hamilton)):
        x.append(i)
        y.append(sorted_hamilton[i][1])
    fileMK.plotZipf(x, y, "Zipf's Law - Plot")
    x = []
    y = []
    for i in range(len(sorted_hamilton)):
        x.append(math.log(i+1))
        y.append(math.log(sorted_hamilton[i][1]))
    fileMK.plotZipf(x, y, "Zipf's Law - Log-Log-Plot")

def task6():
    # Task 6
    hamilton = fileMK.readFile(corpusPath + "Federalist Hamilton.txt")
    madison = fileMK.readFile(corpusPath + "Federalist Madison.txt")
    sorted_hamilton = fileMK.sortedDictFromWords(hamilton.lower().split(" "))
    sorted_madison = fileMK.sortedDictFromWords(madison.lower().split(" "))
    sHamilton = ""
    sMadison = ""
    for i in range(15):
        sHamilton += str(sorted_hamilton[i]) + "\n"
        sMadison += str(sorted_madison[i]) + "\n"
    sHamilton = sHamilton.replace("(", "").replace(")", "").replace("'","")
    sMadison = sMadison.replace("(", "").replace(")", "").replace("'","")
    h = "Hamilton:\n" + str(sHamilton)
    m = "Madison:\n" + str(sMadison)
    print(h)
    print(m)
    path = outputPath + "task6.txt"
    fileMK.writeTextToFile(h + "\n" + m, path)

def toggleGH():
    global doc
    global t
    if (doc):
        doc = False
        t = fileMK.readFilesInArray(corpusPath, doc)
    else:
        doc = True
        t = fileMK.readFilesInArray(corpusPath, doc)

print("Path to Corpus " + corpusPath)
print("Path to Output " + outputPath)
choose()
