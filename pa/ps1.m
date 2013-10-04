clear all;
close all;
clc;
format long;
N = 10^6;
t = 100;
A = rand(N,2,t); % N points of (x,y) and t times
C = A(:,1,:).*A(:,1,:) + A(:,2,:).*A(:,2,:); % x^2 + y^2
myPi = reshape(sum((C <=1)), 1, t).*4/N;
min(myPi)
max(myPi)
mean(myPi)
std(myPi)