clear All;
close All;
clc;

file = fopen('data/Matched point set.txt','r');
%matched = fscanf(file, '%d %d %d %d', [4, inf]);
C = textscan(file, '%d %d %d %d');
left = [C{1},C{2}];
right = [C{3},C{4}];
fclose(file);
