clear All;
close All;
clc;

theta = [1,2,3,4,5,6,7,8];
if mod(length(theta),2) ~= 0
    assert(false)
end
B = ones(length(theta), length(theta))/2 + eye(length(theta))/2;
t4 = sum(reshape(theta.^4, length(theta)/2, 2));
LTheta = t4(1) + theta*B*theta'