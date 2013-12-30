function out = translation(order)
    myRand = randperm(length(order));
    r1 = myRand(1);
    r2 = myRand(2);
    if r1 < r2
        out = [order(1:r1), order(r2), order(r1+1:r2-1), order(r2+1:end)];
    else
        out = [order(1:r2), order(r1), order(r2+1:r1-1), order(r1+1:end)];
    end
end
