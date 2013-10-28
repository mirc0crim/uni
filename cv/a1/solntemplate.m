%% Main script file to run the algorith

clear all;
close all;
clc;
tic
% Put your code here
%%%%%%%%%%%%%%%
pic = 'cat';
%%%%%%%%%%%%%%%
nDir = 12;
L = getLightDir(1, 'Images/chrome/', nDir, false);
imgDim = size(imread(['Images/',pic,'/',pic,'.mask.png']));
imgGray = zeros(imgDim(1),imgDim(2),nDir);
% I contains all images
I = zeros(imgDim(1),imgDim(2),imgDim(3),nDir);
mask = imread(['Images/',pic,'/',pic,'.mask.png']);
mask = mask(:,:,1)/255;
for n=1:nDir
    img = double(imread(['Images/',pic,'/',pic,'.',num2str(n-1),'.png']));
    imgTemp = (img(:,:,1)+img(:,:,2)+img(:,:,3))/3;
    imgTemp(mask(:) == 0) = 0;
    imgGray(:,:,n) = imgTemp;
    I(:,:,:,n) = img(:,:,:);
end

[n, albedoGray] = fitReflectance(imgGray, L);

albedoColor = zeros(imgDim(1),imgDim(2),imgDim(3));
for i=1:imgDim(1)
    for j=1:imgDim(2)
        if mask(i,j) == 1
            col = reshape(I(i,j,:,:), 3, []);
            nor = reshape(n(i,j,:), [], 3);
            % I = a(n*L)
            % a = (n*L)^-1 I
            albedoColor(i,j,:) = (col/(nor*L))/255;
        end
    end
end

depthmap = getDepthFromNormals(n, mask);
toc % elapsed time for cat is 15 seconds as an example
imwrite(abs(n), ['output/',pic,'-normal.png']);
imwrite(albedoGray, ['output/',pic,'-albedoGray.png']);
imwrite(albedoColor, ['output/',pic,'-albedoColor.png']);
imwrite(depthmap, ['output/',pic,'-depth.png']);
