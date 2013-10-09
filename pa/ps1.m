clear all;
close all;
clc;
format long;
N = 1.25*10^6; % better choose < 1.7*10^6
t = 100;
A = rand(N,2,t); % N points of (x,y) and t times
C = A(:,1,:).^2 + A(:,2,:).^2; % x^2 + y^2
clear A;
myPi = reshape(sum((C <=1)), 1, t).*4/N;
clear C;
min(myPi)
max(myPi)
mean(myPi)
std(myPi)