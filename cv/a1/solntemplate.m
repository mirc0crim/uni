%% Main script file to run the algorith

clear all;
close all;
clc;

% Put your code here

circle = imread('Images/chrome/chrome.mask.png');
L = getLightDir(1, 'Images/chrome/', 12, false);