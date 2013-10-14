clear all;
close all;
clc;

r = -2:0.05:2;
[X,Y] = meshgrid(r, r);
Z = ones(size(r'*r));
ii = 0;
for i = r
    ii = ii + 1;
    jj = 0;
    for j = r
        jj = jj + 1;
        Z(jj,ii) = ps2_conv(i,j);
    end
end
C = [0,0,1;1,0,0;0,1,0;0,0,0];
imshow(Z,C)
