function out = crossover(order)
    noOfCities = length(order);
    % use randperm to be sure that the same city isn't chosen twice
    % but it is a lot slower and only has a 1/400 chance to happen
    %myRand = randperm(length(order));
    %r1 = myRand(1);
    %r2 = myRand(2);
    r1 = ceil(rand(1)*noOfCities);
    r2 = ceil(rand(1)*noOfCities);
    if r1 < r2
        out = [order(r2:end), order(r1+1:r2-1), order(1:r1)];
    elseif r2 < r1
        out = [order(r1:end), order(r2+1:r1-1), order(1:r2)];
    else
        out = order;
    end
end
