function [route, dist] = useShortestEdge(distances)
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    c = 1;
    dist = 0;
    while c < noOfCities+1
        index = find(distances == min(min(distances)), 1);
        [y,x] = ind2sub([noOfCities,noOfCities], index);
        valid = true;
        testRoute = route;
        testRoute(y,x) = 1;
        if max(sum(testRoute,1) + sum(testRoute,2)') > 2
            % degree exeeded
            valid = false;
        else
            for n = 1:c
                if trace(testRoute^n) ~= 0
                    % cycle detected
                    valid = false;
                    break;
                end
            end
        end
        if valid || c == noOfCities
            % shortest distance from x to y
            route(y,x) = 1;
            dist = dist + distances(y,x);
            distances(:,x) = Inf; % no more incoming edges allowed
            distances(y,:) = Inf; % no more outgoing edges allowed
            c = c + 1;
            disp(c) % for visual progress
        end
        distances(y,x) = Inf;
        distances(x,y) = Inf;
    end
end
