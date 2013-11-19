function f = fundMat(left, right)
	% get the fundamental matrix from two sets of matching points (at least 8)
    l = [left, ones(length(left), 1)]';
    r = [right, ones(length(right), 1)]';
    left_normed = normMat(l)*l;
    right_normed = normMat(r)*r;
    u2u1u2v1u2 = repmat(right_normed(1,:)', 1, 3).*left_normed';
    v2u1v2v1v2 = repmat(right_normed(2,:)', 1, 3).*left_normed';
    eightPntMat = [u2u1u2v1u2, v2u1v2v1v2, left_normed'];
    [~, ~, V] = svd(eightPntMat);
    [fundU, fundS, fundV] = svd(reshape(V(:,9), 3, 3)');
    f = normMat(r)' * fundU*diag([fundS(1,1), fundS(2,2), 0])*fundV' * normMat(l);
end

function m = normMat(vect3N)
    % Normalize a vector of 3xN coordinates by subtracting centroid
    c = mean(vect3N, 2);
    means = repmat(c, 1, length(vect3N));
    dist = sqrt(sum((vect3N - means).^2));
    mean_dist= mean(dist);
    m = [2/mean_dist, 0, -2/mean_dist*c(1);...
        0, 2/mean_dist, -2/mean_dist*c(2);...
        0, 0, 1];
end
