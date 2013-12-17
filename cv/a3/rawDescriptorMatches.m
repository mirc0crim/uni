% Task 1 - Raw descriptor matching

load('twoFrameData.mat');
region = selectRegion(im1, positions1);

matches = [];
for d=descriptors1(region,:)'
    dist = dist2(d', descriptors2);
    [Y, I] = min(dist);
    matches = [matches; Y, I];
end
matches = sortrows(matches, 1);
topmatches = matches(1:75, :);

figure;
imshow(im2);
displaySIFTPatches(positions2(topmatches(:,2),:), scales2(topmatches(:,2),:), ...
    orients2(topmatches(:,2),:), im2);