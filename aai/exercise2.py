import wave
from pylab import *

def choose():
    line = raw_input("No?\n")
    try:
        i = int(line)
    except ValueError:
        return
    if (i > 9):
        return
    startplot(i)
    choose()

def startplot(num):
    wr = wave.open("D:\\aai\\train\\" + str(num) + ".wav")
    a = map(ord, wr.readframes(wr.getnframes()))
    x = range(len(a))
    plot(x, a)
    ylabel("Bytes")
    xlabel("Frame")
    title("Visualized " + str(num) + ".wav")
    show()

choose()
