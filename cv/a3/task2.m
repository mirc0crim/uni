% Task 2 - Visualizing the vocabulary

run('D:\uni\cv\vlfeat-0.9.17\toolbox\vl_setup')
imgDir = 'D:\uni\cv\images\';
siftDir = 'D:\uni\cv\sift\';
names = dir([siftDir '/*.mat']);
myDescriptors = [];
myCenters = [];
myScales = [];
myOrientations = [];
myImgID = [];
for i = 1:length(names)
   load([siftDir names(i).name], 'imgName', 'D', 'center', 'scale', 'orientation'); 
   myDescriptors = [myDescriptors, D];
   myCenters = [myCenters, center];
   myScales = [myScales, scale];
   myOrientations = [myOrientations, orientation];
   myImgID = [myImgID, repmat(i, [1, length(D)])];
   myImages(:,:,i) = single(rgb2gray(imread([imgDir, imgName])));
end

k = 1000;
% this takes over 13 minutes :/
%[centers, assignments] = vl_kmeans(single(myDescriptors), k);

for i=randsample(1:k, 2)
    figure('name', num2str(i));
    tileNum = 1;
    for j=find(assignments == i)
        if tileNum < 26
            patch = getPatchFromSIFTParameters(myCenters(:,j), myScales(:,j), ...
                myOrientations(:,j), myImages(:,:,myImgID(j)));
            subplot(5,5,tileNum);
            imshow(patch, [min(min(patch)) max(max(patch))]);
        else
            break
        end
        tileNum = tileNum + 1;
    end
end