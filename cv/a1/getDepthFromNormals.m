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
  [M, N] = size(mask);
  
  % YOU NEED TO COMPLETE THIS.
  A = sparse(2*M*N, M*N);
  v = zeros(2*M*N, 1);
  for j = 1:N
      for i = 1:M
          if mask(i,j)==1
              nx = n(i,j,1);
              ny = n(i,j,2);
              nz = n(i,j,3);
              r = (j-1)*M+i;
              c = r;
              % horizontal
              % -Nx = Nz(Zx+1y -Zxy)
              A(r,c) = -nz;
              A(r,c+M) = nz;
              v(r) = -nx;
              % vertical
              % -Ny = Nz(Zxy+1 -Zxy)
              r = M*N + r;
              A(r,c) = -nz;
              A(r,c+1) = nz;
              v(r) = -ny;
          end
      end
  end
  % Az = v
  % z = A^-1 v
  warning('off','MATLAB:rankDeficientMatrix');
  zVec = A\v;
  depth = reshape(zVec, M, N);
  % no negative
  depth = depth + abs(min(min(depth)));
  % range [0,1]
  depth = depth/max(max(depth));
  depth = 1-depth;
  depth(mask==0) = 0;
return
