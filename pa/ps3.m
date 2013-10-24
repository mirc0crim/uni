clear All;
close All;
clc;

loss = [-1,-2];
array = [1,0;1,1;-1,1];
bVect = [4,6,1];

ind = [];

[LNew,ANew,bNew,indNew] = simplex(loss,array,bVect,ind);
[LNew2,ANew2,bNew2,indNew2] = simplex(LNew,ANew,bNew,indNew);
