function [route, dist, currL] = useLocalSearch(distances, mode)
    % mode
    % 1 = swap
    % 2 = translation
    % 3 = inversion
    % 4 = all
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    r = randperm(noOfCities);
    calcLen(distances, r);
    currL = zeros(noOfCities^2 *50, 1);
    c = 0;
    lenBefore = calcLen(distances, r);
    while c < noOfCities^2 *50
        if mode == 1
            newR = swap(r);
        end
        if mode == 2
            newR = translation(r);
        end
        if mode == 3
            newR = inversion(r);
        end
        if mode == 4
            selector = rand(1);
            if selector < 1/3
                newR = swap(r);
            elseif selector > 2/3
                newR = translation(r);
            else
                newR = inversion(r);
            end
        end
        lenAfter = calcLen(distances, newR);
        if lenAfter <= lenBefore
            r = newR;
            lenBefore = lenAfter;
        end
        currL(c+1) = lenBefore;
        c = c + 1;
    end
    dist = calcLen(distances, r);
    for i=1:noOfCities-1
        route(r(i), r(i+1)) = 1;
    end
    route(r(end), r(1)) = 1;
end
