import wave
from pylab import *

#wav files have to be in folder D:\aai\

def choose():
    line = raw_input("No?\n")
    startplot(line)

def startplot(num):
    try:
        i = int(num)
    except ValueError:
        print "Number must be [0,9]"
        choose()
        return
    if (i > 9):
        print "Number must be [0,9]"
        choose()
        return
    wr = wave.open("D:\\aai\\" + num + ".wav")
    a = map(ord, wr.readframes(wr.getnframes()))
    x = range(len(a))
    plot(x, a)
    ylabel("Bytes")
    xlabel("Frame")
    title("Visualized " + num + ".wav")
    show()
    choose()

choose()
