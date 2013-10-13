clear all;
close all;
clc;
format long;

z1 = [1; 0];
z2 = [-0.5; sqrt(3)/2];
z3 = [-0.5; -sqrt(3)/2];

g = @(x,y) [x^3 - 3*x*y^2 - 1; 3*x^2*y - y^3];
j = @(x,y) [3*x^2 - 3*y^2, -6*x*y; 6*x*y, 3*x^2 - 3*y^2];

p = [2;1];
d = 1;
while d>0.01
    oldP = p;
    p = p - j(p(1),p(2))\g(p(1),p(2));
    d = sqrt(abs(p-oldP)' * abs(p-oldP));
end

d
p