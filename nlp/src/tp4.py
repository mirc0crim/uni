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
    elif (ex == "4"):
        task4()
        choose()
    elif (ex == "5"):
        task5()
        choose()

def printInfo():
    print("")
    print(" ------------------------------------------------------------------")
    print(" 1 = 50 Terms with highest Frequencies.")
    print(" 2 = Number of Terms.")
    print(" 3 = Terms appearing once, twice & three times.")
    print(" 4 = Context for \"can\" and \"general\" with two terms.")
    print(" 5 = 10 most frequent Words around specific term.")
    print(" Else Exit")
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
        words += t[i].split(" ")
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
    s = ""
    fcan = fileMK.get2FollowingTerms(t, "can")
    for i in range(len(fcan)):
        s+= "can " + fcan[i] + "\n"
    path = outputPath + "task4-can.txt"
    fileMK.writeTextToFile(s, path)
    s = ""
    fgeneral = fileMK.get2FollowingTerms(t, "general")
    for i in range(len(fgeneral)):
        s += "general " + fgeneral[i] + "\n"
    path = outputPath + "task4-general.txt"
    fileMK.writeTextToFile(s, path)

def task5():
    # Task 5
    tcan = fileMK.readFileToArray(outputPath + "task4-can.txt")
    dictcan = fileMK.sortedDictFromWords(tcan)
    s = ""
    for i in range(10):
        s += str(dictcan[i]).replace("can ","") + "\n"
    path = outputPath + "task5-can.txt"
    fileMK.writeTextToFile(s, path)
    tgeneral = fileMK.readFileToArray(outputPath + "task4-general.txt")
    dictgeneral = fileMK.sortedDictFromWords(tgeneral)
    s = ""
    for i in range(10):
        s += str(dictgeneral[i]).replace("general ","") + "\n"
    path = outputPath + "task5-general.txt"
    fileMK.writeTextToFile(s, path)


print("Path to Federalist Hamilton " + fedHamPath)
print("Path to Output " + outputPath)
choose()
