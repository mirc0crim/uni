# -*- coding: ascii -*-
import sys
import re

print "3. Numbers from 1 to 100."
print ""

for i in range(1,101):
	if (i<10):
		sys.stdout.write(" " + str(i) + " ")
	else:
		sys.stdout.write(str(i) + " ")
	if (i%10 == 0):
		sys.stdout.write("\n")

raw_input("\nPress Enter to continue...")
print "4. Regex for a string containing both \"blue\" & \"green\" somewhere."
print ""

line = raw_input("Enter String\n")
m = re.search("(blue|green)(\W*\w*)*(blue|green)" , line)
if m:
        print "String found: " + m.group()
else:
        print "No match found"

raw_input("\nPress Enter to continue...")
print "5. Regex for a sentence starting with \"A\" and ending with \"end.\"."
print ""

line = raw_input("Enter Sentence\n")
m = re.search("^A(\W*\w*)*end\.$" , line)
if m:
        print "Sentence found: " + m.group()
else:
        print "No match found"

raw_input("\nPress Enter to continue...")
print "6. Regex for a string containing a number that is a multiple of 5."
print ""

line = raw_input("Enter String\n")
m = re.search("[0-9]*[0,5]" , line)
if m:
        print "String found: " + m.group()
else:
        print "No match found"
