% Mirco Kocher
% 09-113-739

clear all;
close all;
clc;

im = imread('colorful.jpg');
imGray = rgb2gray(im);
figure;
subplot(2,4,1);
imshow(im);
title('Original')
subplot(2,4,2);
imshow(imGray);
title('Gray')
% Task 1
imInvGray = uint8(ones(size(imGray))*255) - imGray;
subplot(2,4,3);
imshow(imInvGray);
title('Negative Gray')
% Task 2
imMir = cat(3, im(:,end:-1:1,1), im(:,end:-1:1,2), im(:,end:-1:1,3));
subplot(2,4,4);
imshow(imMir);
title('Mirror')
% Task 3
imRG = cat(3, im(:,:,2), im(:,:,1), im(:,:,3));
subplot(2,4,5);
imshow(imRG);
title('Red Green Switch')
% Task 4
imAvg = (im + imMir)./2;
subplot(2,4,6);
imshow(imAvg);
title('Averaged with Mirror')
% Task 5
r = randi(510)-255;
imRandGray = double(imGray) + ones(size(imGray))*r;
imRandGray(find(imRandGray > 255)) = 255;
imRandGray(find(imRandGray < 0)) = 0;
subplot(2,4,7);
imshow(uint8(imRandGray));
title(sprintf('Grayscale %+i', r))
