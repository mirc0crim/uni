function ps5()

    close All;
    clear All;
    clc;

    coordinates = zeros(131,2);

    fid = fopen('data131.txt', 'r');
    tline = fgets(fid);
    i = 1;
    while ischar(tline)
        if tline(1) ~= 'N'
            m = regexp(tline, '\d+');
            coordinates(i,:) = [str2double(tline(m(2):m(3)-2)), str2double(tline(m(3):end))];
            i = i + 1;
        end
        tline = fgets(fid);
    end
    fclose(fid);
    
    subplot(2,2,1);
    scatter(coordinates(:,1),coordinates(:,2));
    title('Location of the Cities')
    ylim
    axis([-3 110 -3 50])
    
    xdist = repmat(coordinates(:,1),1,131) - repmat(coordinates(:,1)',131,1);
    ydist = repmat(coordinates(:,2),1,131) - repmat(coordinates(:,2)',131,1);
    
    % Euclidean Distance
    distancesEuclidean = sqrt(xdist.^2 + ydist.^2);
    distancesEuclidean(find(eye(131))) = Inf;
    routeEuclidean = useShortestEdge(distancesEuclidean);
    subplot(2,2,3);
    i = 1;
    j = 1;
    euclideanGraph = ones(132,2);
    while i < 132
        euclideanGraph(i,:) = coordinates(j,:);
        j = find(routeEuclidean(j,:));
        i = i + 1;
    end
    euclideanGraph(i,:) = coordinates(1,:);
    plot(euclideanGraph(:,1),euclideanGraph(:,2));
    title('Route with Euclidean Distance')
    axis([-3 110 -3 50])
    
    % Manhattan Distance
    distancesManhattan = abs(xdist) + abs(ydist);
    distancesManhattan(find(eye(131))) = Inf;
    routeManhattan = useShortestEdge(distancesManhattan);
    subplot(2,2,4);
    i = 1;
    j = 1;
    manhattanGraph = ones(132,2);
    while i < 132
        manhattanGraph(i,:) = coordinates(j,:);
        j = find(routeManhattan(j,:));
        i = i + 1;
    end
    manhattanGraph(i,:) = coordinates(1,:);
    plot(manhattanGraph(:,1),manhattanGraph(:,2));
    title('Route with Manhattan Distance')
    axis([-3 110 -3 50])
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
        incomming = sum(testRoute,1);
        outgoing = sum(testRoute,2);
        if max(incomming + outgoing') > 2
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
            distances(:,x) = Inf;
            distances(y,:) = Inf;
            c = c + 1;
            disp(c)
        end
    end
end
