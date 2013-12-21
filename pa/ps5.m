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
    [routeEuclidean, distEuclidean] = useShortestEdge(distancesEuclidean);
    euclideanGraph = createGraph(coordinates, routeEuclidean);
    subplot(2,2,3);
    plot(euclideanGraph(:,1),euclideanGraph(:,2));
    title('Route with Euclidean Distance')
    axis([-3 110 -3 50])
    text(115,25,['l=',num2str(round(distEuclidean))])
    
    % Manhattan Distance
    distancesManhattan = abs(xdist) + abs(ydist);
    distancesManhattan(find(eye(131))) = Inf;
    [routeManhattan, distManhattan] = useShortestEdge(distancesManhattan);
    manhattanGraph = createGraph(coordinates, routeManhattan);
    subplot(2,2,4);
    plot(manhattanGraph(:,1),manhattanGraph(:,2));
    title('Route with Manhattan Distance')
    axis([-3 110 -3 50])
    text(115,25,['l=',num2str(distManhattan)])
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
