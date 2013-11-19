% Get the rotation and translation of the camera and do a 3D reconstruction

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
[U, D, V] = svd(EssentialMatrix);
W = [0,-1,0; 1,0,0; 0,0,1];
tx = V*W*D*V';
R = U*W'*V';
t = [tx(6),tx(7),tx(2)]'

x3 = zeros(1, length(left));
for i=1:length(left)
    r1 = R(1,:);
    y1r3 = right(i,1)*R(3,:);
    x3(i) = dot((r1-y1r3),t) / dot((r1-y1r3),[left(i,:),1]);
end
left3d = [left'; x3];
left3d(1,:) = left3d(1,:).*left3d(3,:);
left3d(2,:) = left3d(2,:).*left3d(3,:);
scatter3(left3d(1,:), left3d(2,:), left3d(3,:), 'x')
