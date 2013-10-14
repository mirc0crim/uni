function a = ps2_conv(p1,p2)
% calculates if the point [x,y] converges to z1, z2 or z3

p = [p1; p2];
z1 = [1; 0];
z2 = [-0.5; sqrt(3)/2];
z3 = [-0.5; -sqrt(3)/2];

g = @(x,y) [x^3 - 3*x*y^2 - 1; 3*x^2*y - y^3];
j = @(x,y) [3*x^2 - 3*y^2, -6*x*y; 6*x*y, 3*x^2 - 3*y^2];
dist = @(x,y) sqrt(abs(x-y)' * abs(x-y));

d = 1;
while d>0.01
    oldP = p;
    p = p - j(p(1),p(2))\g(p(1),p(2));
    d = dist(p,oldP);
end

array = [dist(p,z1), dist(p,z2), dist(p,z3)];
[d, a] = min(array);
if isnan(array(1))
    a = 4;
end
