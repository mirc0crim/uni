function [route, dist] = useLocalSearch(distances, mode)
    % mode
    % 1 = swap
    % 2 = translation
    % 3 = inversion
    % 4 = all
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    r = randperm(noOfCities);
    calcLen(distances, r);
    c = 0;
    while c < 5*noOfCities^2
        if mode == 1
            lenBefore = calcLen(distances, r);
            newR = swap(r);
            if calcLen(distances, newR) <= lenBefore
                r = newR;
            end
        end
        if mode == 2
            lenBefore = calcLen(distances, r);
            newR = translation(r);
            if calcLen(distances, newR) <= lenBefore
                r = newR;
            end
        end
        if mode == 3
            lenBefore = calcLen(distances, r);
            newR = inversion(r);
            if calcLen(distances, newR) <= lenBefore
                r = newR;
            end
        end
        if mode == 4
            lenBefore = calcLen(distances, r);
            selector = rand(1);
            if selector < 1/3
                newR = swap(r);
            elseif selector > 2/3
                newR = translation(r);
            else
                newR = inversion(r);
            end
            if calcLen(distances, newR) <= lenBefore
                r = newR;
            end
        end
        c = c + 1;
        if mod(c, 50000) == 0
            disp(c); % print out every 20th iteration
        end
    end
    dist = calcLen(distances, r);
    for i=1:noOfCities-1
        route(r(i), r(i+1)) = 1;
    end
    route(r(end), r(1)) = 1;
end

function out = swap(order)
    myRand = randperm(length(order));
    r1 = myRand(1);
    r2 = myRand(2);
    if r1 < r2
        out = [order(1:r1-1), order(r2), order(r1+1:r2-1), order(r1), order(r2+1:end)];
    else
        out = [order(1:r2-1), order(r1), order(r2+1:r1-1), order(r2), order(r1+1:end)];
    end
end

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

function out = inversion(order)
    myRand = randperm(length(order));
    r1 = myRand(1);
    r2 = myRand(2);
    if r1 < r2
        out = [order(1:r1), order(r2:-1:r1+1), order(r2+1:end)];
    else
        out = [order(1:r2), order(r1:-1:r2+1), order(r1+1:end)];
    end
end

function d = calcLen(distances, selected)
    noOfCities = length(selected);
    d = 0;
    for i=1:noOfCities-1
        d = d + distances(selected(i), selected(i+1));
    end
    d = d + distances(selected(end), selected(1));
end