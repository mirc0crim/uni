%% Main script file to run the algorith

clear all;
close all;
clc;

% Put your code here
nDir = 12;
circle = imread('Images/chrome/chrome.mask.png');
L = getLightDir(1, 'Images/chrome/', nDir, false);
imData = zeros(340,512,nDir);
for n=1:nDir
    img = double(imread(['Images/horse/','horse.',num2str(n-1),'.png']));
    imData(:,:,n) = img(:,:,1);
end

[n, albedo] = fitReflectance(imData, L);
subplot(1,2,1);
imshow(n);
subplot(1,2,2);
imshow(albedo);