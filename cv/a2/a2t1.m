function a2t1()
    clear All;
    close All;
    clc;

    name = 'tsukuba';
    imLeft = imread(['Images/', name, '-l.tiff']);
    imRight = imread(['Images/', name, '-r.tiff']);
    %[left, right] = cpselect(im_left, im_right, 'Wait', true)
    left = [174,172; 178,156; 194,142; 194,132; 80,256; 47,81; 244,60; 311,222];
    right = [163,171; 168,155; 187,143; 186,132; 68,256; 41,81; 240,60; 303,221];
    fundamentalMatrix = fundMat(left, right)

    epipoleEpipolar(imLeft, imRight, fundamentalMatrix, 'left', 'right')
    xlabel('(click to continue)')
    waitforbuttonpress;
    epipoleEpipolar(imRight, imLeft, fundamentalMatrix', 'right', 'left')
end

function epipoleEpipolar(im1, im2, f, dir1, dir2)
    imDim = size(im1);
    figure(1);
    imshow(im1);
    title(['Choose a point in this ', dir1, ' image']);
    pnt = ginput(1);
    hold on;
    plot(pnt(1), pnt(2), 'rx');
    hold off;
    figure(2);
    imshow(im2);
    title(['Here is the epipole and epipolar line in the ', dir2,' image']);
    hold on;
    fp = f*[pnt, 1]';
    x1 = -fp(3)/fp(1);
    y2 = -fp(3)/fp(2);
    plot([x1,0,x1-imDim(2)*fp(2)/fp(1),imDim(1)], [0,y2,imDim(2),y2-imDim(1)*fp(1)/fp(2)], 'r');
    epipol = null(f);
    epipol = epipol/epipol(3);
    disp([dir2, ' epipol']);
    disp(epipol(1:2));
    plot(epipol(1), epipol(2), 'og');
    hold off
end
