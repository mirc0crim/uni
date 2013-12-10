% Task 0 - Compute SIFT features for each image in a .mat file

run('D:\uni\cv\vlfeat-0.9.17\toolbox\vl_setup')

imgDir = 'D:\uni\cv\images\';
siftDir = 'D:\uni\cv\sift\';

images = dir([imgDir, '*.png']);
tic
for i=1:10
    imgName = images(i).name;
    I = single(rgb2gray(imread([imgDir, imgName])));
    [F,D] = vl_sift(I);
    numFeats = length(D);
	center = F(1:2,:);
    scale = F(3,:);
    orientation = F(4,:);
    save([siftDir, imgName(1:(end-4)), '.mat'], 'imgName', 'D', 'numFeats', 'center', 'scale', 'orientation');
end
toc % each pic takes about 1s

% plot last sift over image
image(imread([imgDir, imgName]));
perm = randperm(size(F,2));
sel = perm(1:50);
h1 = vl_plotframe(F(:,sel));
h2 = vl_plotframe(F(:,sel));
set(h1,'color','k','linewidth',3);
set(h2,'color','y','linewidth',2);
h3 = vl_plotsiftdescriptor(D(:,sel),F(:,sel));
set(h3,'color','g');
