q = [3, 5, 7]'
A = [2, 6, 6]'
B = [3, 4, 8]'

innerA = dot(q,A)
innerB = dot(q,B)
diceA = 2*dot(q,A)/(norm(A)+norm(q))
diceB = 2*dot(q,B)/(norm(B)+norm(q))
cosineA = dot(q,A)/sqrt(norm(q)*norm(A))
cosineB = dot(q,B)/sqrt(norm(q)*norm(B))