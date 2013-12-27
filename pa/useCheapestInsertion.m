function [route, dist, startSelected] = useCheapestInsertion(distances)
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    isUsed = zeros(noOfCities, 1);
    startSelected = ceil(rand(1)*noOfCities);
    c = 1;
    isUsed(startSelected) = 1;
    [~, secondSelected] = min(distances(startSelected,:));
    c = 2;
    isUsed(secondSelected) = 1;
    selected = [startSelected, secondSelected];
    while c < noOfCities
        minLenHat = Inf;
        minSelectedHat = [];
        unused = find(isUsed==0);
        used = Inf;
        for i=1:length(unused)
            minLen = Inf;
            minSelected = [];
            for j=1:c
                testSelected = [selected(1:j), unused(i), selected(j+1:end)];
                d = calcLen(distances, testSelected);
                if d < minLen
                    minLen = d;
                    minSelected = testSelected;
                end
            end
            if minLen < minLenHat
                minLenHat = minLen;
                minSelectedHat = minSelected;
                used = unused(i);
            end
        end
        isUsed(used) = 1;
        selected = minSelectedHat;
        c = c + 1;
        if mod(c, 20) == 0
            disp(c); % print out every 20th iteration
        end
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