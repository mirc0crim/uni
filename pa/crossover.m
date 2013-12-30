function out = crossover(order)
    myRand = randperm(length(order));
    r1 = myRand(1);
    r2 = myRand(2);
    if r1 < r2
        out = [order(r2:end), order(r1+1:r2-1), order(1:r1)];
    else
        out = [order(r1:end), order(r2+1:r1-1), order(1:r2)];
    end
end
