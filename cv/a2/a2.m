function a2()
    clear All;
    close All;
    clc;
    
    name = 'tsukuba';
    left = imread(['Images/', name, '-l.tiff']);
    right = imread(['Images/', name, '-r.tiff']);
    imgDim = size(left);
    subplot(1,2,1);
    imshow(left);
    subplot(1,2,2);
    imshow(right);
    [x, y] = ginput(2);
    x = round(x);
    y = round(y);
    nonvalid = find(x > imgDim(2) | y > imgDim(1) | x < 0 | y < 0);
    x(nonvalid) = NaN;
    y(nonvalid) = NaN;
    
end