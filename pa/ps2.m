clear all;
close all;
clc;

z1 = [1,0];
z2 = [-0.5,sqrt(3)/2];
z3 = [-0.5,-sqrt(3)/2];

g = @(x,y) [x^3 - 3*x*y^2 - 1; 3*x^2*y - y^3];
j = @(x,y) [3*x^2 - 3*y^2, 6*x*y; 6*x*y, 3*x^2 - 3*y^2];

g(z1(1),z1(2))
j(z1(1),z1(2))
