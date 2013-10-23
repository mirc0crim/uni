function [n, albedo] = fitReflectance(im, L)
  % [n, albedo] = fitReflectance(im, L)
  % 
  % Input:
  %   im - nPix x nDirChrome array of brightnesses,
  %   L  - 3 x nDirChrome array of light source directions.
  % Output:
  %   n - nPix x 3 array of surface normals, with n(k,1:3) = (nx, ny, nz)
  %       at the k-th pixel.
  %   albedo - nPix x 1 array of estimated albdedos
    

  % YOU NEED TO COMPLETE THIS
  
  % a(x)(n(x)*L) = I(x)
  [rows, cols, dirs] = size(im);
  n_hat = L'\reshape(im,rows*cols,dirs)';
  n_hat_sqr = n_hat.^2;
  albedoVect = sqrt(n_hat_sqr(1,:)+n_hat_sqr(2,:)+n_hat_sqr(3,:));
  nVect = n_hat./[albedoVect; albedoVect; albedoVect];
  albedo = reshape(albedoVect, rows, cols);
  albedo = albedo/max(max(albedo));
  n = reshape(nVect', rows, cols, 3);
  
return;