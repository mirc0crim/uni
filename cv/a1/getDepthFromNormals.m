function [depth] = getDepthFromNormals(n, mask)
  % [depth] = getDepthFromNormals(n, mask)
  %
  % Input:
  %    n is an [N, M, 3] matrix of surface normals (or zeros
  %      for no available normal).
  %    mask logical [N,M] matrix which is true for pixels
  %      at which the object is present.
  % Output
  %    depth an [N,M] matrix providing depths which are
  %          orthogonal to the normals n (in the least
  %          squares sense).
  %
  [rows, cols] = size(mask);
  
  % YOU NEED TO COMPLETE THIS.
  depth = [];
  A = sparse(2*rows*cols, rows*cols);
  v = zeros(2*rows*cols, 1);
  myIndex = 1;
  for i = 1:rows
      for j = 1:cols
          if mask(i,j)==1
              nx = n(i,j,1);
              ny = n(i,j,2);
              nz = n(i,j,3);
              A(myIndex, (i-1)*rows+j+1) = 1;
              A(myIndex, (i-1)*rows+j) = 0;
              v(myIndex) = nx/nz;
              myIndex = myIndex + 1;
              A(myIndex, (i-1)*rows+j) = 0;
              A(myIndex, i*rows+j) = 1;
              v(myIndex) = ny/nz;
              myIndex = myIndex + 1;
          end
      end
  end
  zVec = A\v;
  depth = reshape(zVec, rows, cols);
return
