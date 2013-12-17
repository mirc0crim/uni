% Task 3 - Full frame queries
% requires "Task 2 - Visualizing the vocabulary" to run first
siftDir = 'D:\uni\cv\sift\';
names = dir([siftDir '/*.mat']);

numOfImages = length(names)-1;
searchImage = 77;
% make the histograms
hist = [];
for i=1:numOfImages
    hist(:,i) = histc(assignments(myImgID == i), 1:length(myCenters));
end
% get closest match
similarities = [];
for img=1:numOfImages
    s = sum(hist(:,img).*hist(:, searchImage));
    s = s / (norm(hist(:,img) * norm(hist(:, searchImage))));
    similarities = [similarities; s, img];
end
similarities = sortrows(similarities);
similarities = flipdim(similarities, 1);
% results
figure
subplot(3,3,2);
imshow(myImages(:,:,searchImage), [0 255]);
for similarImg=1:6
    subplot(3, 3, 3 + similarImg);
    imshow(myImages(:,:,similarities(similarImg, 2)), [0 255]);
end
