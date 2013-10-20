%% Main script file to run the algorith

clear all;
close all;
clc;

% Put your code here

circle = imread('Images/chrome/chrome.mask.png');
circle = circle(:,:,1);
[circleRow circleCol] = find(circle == 255);
center = [(max(circleCol) + min(circleCol))/2, (max(circleRow) + min(circleRow))/2]
radius = (max(circleRow) - min(circleRow))/2
