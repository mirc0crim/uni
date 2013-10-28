function [L] = fitChromeSphere(chromeDir, nDir, chatty)
  % [L] = fitChromeSphere(chromeDir, nDir, chatty)
  % Input:
  %  chromeDir (string) -- directory containing chrome images.
  %  nDir -- number of different light source images.
  %  chatty -- true to show results images. 
  % Return:
  %  L is a 3 x nDir image of light source directions.

  % Since we are looking down the z-axis, the direction
  % of the light source from the surface should have
  % a negative z-component, i.e., the light sources
  % are behind the camera.
    
  if ~exist('chatty', 'var')
    chatty = false;
  end
    
  mask = imread([chromeDir, 'chrome.mask.png']);
  mask = mask(:,:,1) / 255.0;

  for n=1:nDir
    fname = [chromeDir,'chrome.',num2str(n-1),'.png'];
    img = imread(fname);
    imData(:,:,n) = img(:,:,1);           % red channel
  end

  % YOU NEED TO COMPLETE THIS FUNCTION
  
  [circleRow circleCol] = find(mask == 1);
  center = [(max(circleCol) + min(circleCol))/2, (max(circleRow) + min(circleRow))/2];
  radius = (max(circleRow) - min(circleRow))/2;
  
  L = zeros(3, nDir);
  r = [0,0,-1.0];
  for i = 1:nDir
      img = imData(:,:,i);
      [pntsRow, pntsCol] = find(img == 255);
      xPoint = sum(pntsCol)/double(size(pntsCol, 1));
      yPoint = sum(pntsRow)/double(size(pntsRow, 1));
      xNormal = xPoint - center(1);
      yNormal = yPoint - center(2); 
      zNormal = -sqrt(radius^2 - xNormal^2 - yNormal^2);
      n = [xNormal, yNormal, zNormal]/radius;
      nr = n * r';
      L(:,i) = 2*nr*n - r;
  end
  return;

