clear All;
close All;
clc;

file = fopen('data/Matched point set.txt','r');
C = textscan(file, '%d %d %d %d');
fclose(file);
left = [C{1},C{2}];
right = [C{3},C{4}];
%remove random entries to keep only a subset of 20%
deletions = rand(length(left),1);
deletions(deletions >= 0.8) = 1;
left = double(left(deletions==1,:));
right = double(right(deletions==1,:));

fundamentalMatrix = fundMat(left, right);
disp(['Funamental Matrix has rank ', int2str(rank(fundamentalMatrix))]);
disp(fundamentalMatrix);

%from the intrinsic parameter file
K = [-83.33333, 0.0, 250.0; 0.0, -83.33333, 250.0; 0.0, 0.0, 1.0];
EssentialMatrix = K'*fundamentalMatrix*K
