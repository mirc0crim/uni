function [route, dist, startSelected] = useBestInsertion(distances)
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    r = randperm(noOfCities);
    selected = [r(1), r(2), r(3)];
    startSelected = [r(1), r(2), r(3)];
    c = 3;
    while c < noOfCities
        minLen = Inf;
        minSelected = [];
        for i=1:c
            testSelected = [selected(1:i), r(c+1), selected(i+1:end)];
            d = calcLen(distances, testSelected);
            if d < minLen
                minLen = d;
                minSelected = testSelected;
            end
        end
        selected = minSelected;
        c = c + 1;
    end
    dist = calcLen(distances, selected);
    for i=1:noOfCities-1
       route(selected(i), selected(i+1)) = 1;
    end
    route(selected(end), selected(1)) = 1;
end

function d = calcLen(distances, selected)
    noOfCities = length(selected);
    d = 0;
    for i=1:noOfCities-1
        d = d + distances(selected(i), selected(i+1));
    end
    d = d + distances(selected(end), selected(1));
end