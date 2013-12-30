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
        minCostHat = Inf;
        minSelectedHat = [];
        unused = find(isUsed==0);
        used = Inf;
        for i=1:length(unused)
            minCost = Inf;
            minSelected = [];
            for j=1:c-1
                insertCost = distances(selected(j), unused(i)) ...
                    + distances(unused(i), selected(j+1))...
                    - distances(selected(j), selected(j+1));
                if insertCost < minCost
                    minCost = insertCost;
                    minSelected = [selected(1:j), unused(i), selected(j+1:end)];
                end
            end
            insertCost = distances(selected(c), unused(i)) ...
                + distances(unused(i), selected(1))...
                - distances(selected(c), selected(1));
            if insertCost < minCost
                minCostHat = insertCost;
                minSelectedHat = [selected(1:c), unused(i), selected(c+1:end)];
                used = unused(i);
            elseif minCost < minCostHat
                minCostHat = minCost;
                minSelectedHat = minSelected;
                used = unused(i);
            end
        end
        isUsed(used) = 1;
        selected = minSelectedHat;
        c = c + 1;
    end
    dist = calcLen(distances, selected);
    for i=1:noOfCities-1
       route(selected(i), selected(i+1)) = 1;
    end
    route(selected(end), selected(1)) = 1;
end
