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
  v = [];
  for x = 1:rows
      for y = 1:cols
          if mask(x,y)==1
              % do stuff
          end
      end
  end
  A = sparse();
  zVec = A\v;
  depth = reshape(zVec, rows, cols);
return
