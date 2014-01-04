function [route, dist] = useLocalSearch2(distances, sequence)
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    r = sequence;
    calcLen(distances, r);
    c = 0;
    lenBefore = calcLen(distances, r);
    while c < noOfCities^2*50
        selector = rand(1);
        if selector < 1/3
            newR = swap(r);
        elseif selector > 2/3
            newR = translation(r);
        else
            newR = inversion(r);
        end
        lenAfter = calcLen(distances, newR);
        if lenAfter < lenBefore
            r = newR;
            lenBefore = lenAfter;
        end
        c = c + 1;
    end
    dist = calcLen(distances, r);
    for i=1:noOfCities-1
        route(r(i), r(i+1)) = 1;
    end
    route(r(end), r(1)) = 1;
end
