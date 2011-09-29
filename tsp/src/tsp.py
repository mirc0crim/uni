import time

class PlaceList:
    def __init__(self, size=7):
        self.size = size
        self.init_list()
        self.load()

    def load(self):
        path = str(self.size) + ".tsp"
        if self.size < 10: path = "0" + path
        fp = open(path, "r")
        count = int(fp.readline())
        assert count == self.size
        fp.readline()

        y = 0
        for line in fp.readlines():
            x = y + 1
            for number in line.split(" "):
                if number == "":
                    continue
                self.set_distance(x,y,number)
                x += 1
            y += 1

    def init_list(self):
        self.distance = []
        for i in range(self.size):
            self.distance.append([])
            for j in range(self.size):
                self.distance[i].append(0)

    def set_distance(self, x, y, d):
        d = int(d)
        self.distance[x][y] = d
        self.distance[y][x] = d

    def dist(self, x, y):
        return self.distance[x][y]
class Nearest():
    def name(self):
        return "Nearest"

    def run(self, dist):
        km = 0
        left = range(1, dist.size)
        now = 0
        while len(left) > 0:
            best = 1000000
            besti = -1
            for i in left:
                if dist.dist(now, i) < best:
                    best = dist.dist(now, i)
                    besti = i
            assert besti > 0
            now = besti
            km += best
            left.remove(now)

        km += dist.dist(now, 0)

        return km

class Bruteforce():
    def name(self):
        return "Bruteforce"

    def run(self, dist):
        n = dist.size
        bestkm = 100000000
        current = []
        for i in range(n):
            current.append(0)

        while True:
            if not self.valid_next(current): break
            
            km = self.getkm(dist, current)
            if km < bestkm:
                bestkm = km
                
        return bestkm

    def getkm(self, dist, current):
        km = 0
        for i in range(len(current)):
            j = i + 1
            if j == len(current): j = 0
            km += dist.dist(current[i], current[j])
        return km

    def valid_next(self, current):
        while True:
            if not self.next(current):
                return False
            copy = current[::]
            copy.sort()
            valid = True
            for i in range(len(copy)):
                if i != copy[i]:
                    valid = False
                    break
            if not valid:
                continue
            return True

    def next(self, current):
        n = len(current)
        i = 0
        while i < n and current[i] == n-1:
            current[i] = 0
            i += 1
        if i == n: return False
        current[i] += 1
        return True

class BetterBruteforce():
    def name(self):
        return "BetterBruteforce"

    def run(self, dist):
        n = dist.size
        bestkm = 100000000
        current = range(n)

        while True:
            km = self.getkm(dist, current)
            if km < bestkm:
                bestkm = km

            current = self.valid_next(current)
            if not current: break
                
        return bestkm

    def getkm(self, dist, current):
        km = 0
        for i in range(len(current)):
            j = i + 1
            if j == len(current): j = 0
            km += dist.dist(current[i], current[j])
        return km

    def valid_next(self, current):
        n = len(current)
        k = n - 2
        while k >= 0 and current[k] > current[k+1]:
            k -= 1
        if k < 0:
            return False
        
        l = n - 1
        while current[k] > current[l]:
            l -= 1

        tmp = current[k]
        current[k] = current[l]
        current[l] = tmp

        splt1 = current[:k+1]
        splt2 = current[k+1:]
        splt2.reverse()

        return splt1 + splt2
        
def run(algo, dist):
    start = time.time()
    km = algo.run(dist)
    stop = time.time()
    return [km, stop-start]

def run_algorithms(al, mn, mx):
    print "Algorithm","km","ms"
    for n in range(mn, mx+1):
        print
        print "n =", n
        dist = PlaceList(n)
        for algo in al:
            a = algo()
            result = run(a, dist)
            print a.name(), result[0], result[1]*1000


run_algorithms([BetterBruteforce, Nearest], 2, 20)
