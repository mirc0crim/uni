function tsp()
    close All;
    clear All;
    clc;

    noOfCities = 411;
    
    coordinates = zeros(noOfCities,2);
    
    % Read coordinates from text file
    fid = fopen('TSP_411.txt', 'r');
    tline = fgets(fid);
    i = 1;
    while ischar(tline)
        m = regexp(tline, '\d+'); % returns [node_number, x, y]
        coordinates(i,:) = [str2double(tline(m(2):m(3)-2)), str2double(tline(m(3):end))];
        i = i + 1;
        tline = fgets(fid);
    end
    fclose(fid);
    
    % Initial locations
    subplot(2,2,1);
    scatter(coordinates(:,1),coordinates(:,2));
    title('Location of the Cities')
    axis([-3 125 -3 100])
    
    xdist = repmat(coordinates(:,1),1,noOfCities) - repmat(coordinates(:,1)',noOfCities,1);
    ydist = repmat(coordinates(:,2),1,noOfCities) - repmat(coordinates(:,2)',noOfCities,1);
    distances = sqrt(xdist.^2 + ydist.^2);
    distances(find(eye(noOfCities))) = Inf;
    [route, len, startCityNN] = useNearestNeighbor(distances);
    graphNN = createGraph(coordinates, route);
    subplot(2,2,3);
    plot(graphNN(:,1),graphNN(:,2));    
    title('Route with Nearest Neighbor');
    axis([-3 125 -3 100])
    hold on;
    plot(coordinates(startCityNN,1), coordinates(startCityNN,2), 'r*');
    hold off;
    text(130,25,['l=',num2str(round(len))]);
end

function graph = createGraph(coordinates, route)
    noOfCities = length(route);
    i = 1;
    j = 1;
    graph = ones(noOfCities+1,2);
    while i < noOfCities+1
        graph(i,:) = coordinates(j,:);
        j = find(route(j,:)); %next connected node
        i = i + 1;
    end
    graph(i,:) = coordinates(1,:); %close graph
end
