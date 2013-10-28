clear All;
close All;
clc;

loss = [-1,-2];
array = [1,0;1,1;-1,1];
bVect = [4,6,1];

[LNew,ANew,bNew] = simplex(loss,array,bVect);