function ps5()
    close All;
    clear All;
    clc;

    coordinates = zeros(131,2);
    
    % Read coordinates from text file
    fid = fopen('data131.txt', 'r');
    tline = fgets(fid);
    i = 1;
    while ischar(tline)
        if tline(1) ~= 'N'
            m = regexp(tline, '\d+'); % returns [node_number, x, y]
            coordinates(i,:) = [str2double(tline(m(2):m(3)-2)), str2double(tline(m(3):end))];
            i = i + 1;
        end
        tline = fgets(fid);
    end
    fclose(fid);
    
    % Initial locations
    subplot(2,2,1);
    scatter(coordinates(:,1),coordinates(:,2));
    title('Location of the Cities')
    axis([-3 110 -3 50])
    
    xdist = repmat(coordinates(:,1),1,131) - repmat(coordinates(:,1)',131,1);
    ydist = repmat(coordinates(:,2),1,131) - repmat(coordinates(:,2)',131,1);

    % Euclidean Distance
    distancesEuclidean = sqrt(xdist.^2 + ydist.^2);
    distancesEuclidean(find(eye(131))) = Inf;
    routeEuclidean = useShortestEdge(distancesEuclidean);
    euclideanGraph = createGraph(coordinates, routeEuclidean);
    subplot(2,2,3);
    plot(euclideanGraph(:,1),euclideanGraph(:,2));
    title('Route with Euclidean Distance')
    axis([-3 110 -3 50])
    
    % Manhattan Distance
    distancesManhattan = abs(xdist) + abs(ydist);
    distancesManhattan(find(eye(131))) = Inf;
    routeManhattan = useShortestEdge(distancesManhattan);
    manhattanGraph = createGraph(coordinates, routeManhattan);
    subplot(2,2,4);
    plot(manhattanGraph(:,1),manhattanGraph(:,2));
    title('Route with Manhattan Distance')
    axis([-3 110 -3 50])
end

function graph = createGraph(coordinates, route)
    i = 1;
    j = 1;
    graph = ones(132,2);
    while i < 132
        graph(i,:) = coordinates(j,:);
        j = find(route(j,:)); %next connected node
        i = i + 1;
    end
    graph(i,:) = coordinates(1,:); %close graph
end

function route = useShortestEdge(distances)
    route = zeros(131,131);
    c = 1;
    while c < 132
        index = find(distances == min(min(distances)), 1);
        [y,x] = ind2sub([131,131], index);
        distances(y,x) = Inf;
        distances(x,y) = Inf;
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
        if valid || c == 131
            % shortest distance from x to y
            route(y,x) = 1;
            distances(:,x) = Inf; % no more incoming edges allowed
            distances(y,:) = Inf; % no more outgoing edges allowed
            c = c + 1;
            disp(c) % for visual progress
        end
    end
end
