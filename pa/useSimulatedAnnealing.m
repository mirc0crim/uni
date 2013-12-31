function [route, dist, bestL, worstL, meanL] = useSimulatedAnnealing(distances, mode)
    % mode
    % 1 = Metropolis
    % 2 = Heat bath
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    r = randperm(noOfCities);
    % 10000 * 0.95^x < 0.00001 -> x >= 405
    bestL = zeros(405, 1);
    worstL = zeros(405, 1);
    meanL = zeros(405, 1);
    lenBefore = calcLen(distances, r);
    T = 10000;
    c = 1;
    while T > 0.00001
        allLengths = zeros(1000,1);
        for i=1:1000
            selector = rand(1);
            if selector < 1/3
                newR = swap(r);
            elseif selector > 2/3
                newR = translation(r);
            else
                newR = inversion(r);
            end
            lenAfter = calcLen(distances, newR);
            deltaH = lenAfter - lenBefore;
            if mode == 1
                if deltaH <= 0
                    r = newR;
                    lenBefore = lenAfter;
                else
                    prob = exp(-deltaH/T);
                    if rand(1) < prob
                        r = newR;
                        lenBefore = lenAfter;
                    end
                end
            end
            if mode == 2
                prob = 1/(1+exp(deltaH/T));
                if rand(1) < prob
                    r = newR;
                    lenBefore = lenAfter;
                end
            end
            allLengths(i) = lenBefore;
        end
        bestL(c) = min(allLengths);
        meanL(c) = mean(allLengths);
        worstL(c) = max(allLengths);
        T = 0.95 * T;
        c = c + 1;
    end
    dist = calcLen(distances, r);
    for i=1:noOfCities-1
        route(r(i), r(i+1)) = 1;
    end
    route(r(end), r(1)) = 1;
end
