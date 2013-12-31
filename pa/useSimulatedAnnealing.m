function [route, dist] = useSimulatedAnnealing(distances, mode)
    % mode
    % 1 = Metropolis
    % 2 = Heat bath
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    r = randperm(noOfCities);
    lenBefore = calcLen(distances, r);
    T = 10000;
    while T > 0.00001
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
        end
        T = 0.95 * T;
    end
    dist = calcLen(distances, r);
    for i=1:noOfCities-1
        route(r(i), r(i+1)) = 1;
    end
    route(r(end), r(1)) = 1;
end
