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
  n = zeros(rows, cols, 3);
  albedo = zeros(rows, cols);
  ps = pinv(L)';
  for i = 1:rows
      for j = 1:cols
          n_snake = ps*reshape(im(i,j,:), dirs, 1);
          albedo(i,j,:) = norm(n_snake);
          n(i,j,:) = n_snake/albedo(i,j,:);
      end
  end
  albedo = albedo/max(max(albedo));
return;