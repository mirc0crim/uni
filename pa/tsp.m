function tsp()
    close All;
    clear All;
    clc;

    noOfCities = 131;
    coordinates = zeros(noOfCities,2);
    
    % Read coordinates from text file
    % fid = fopen('TSP_411.txt', 'r');
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
    figure;
    scatter(coordinates(:,1),coordinates(:,2));
    title('Location of the Cities')
    axis([-3 125 -3 100])
    
    xdist = repmat(coordinates(:,1),1,noOfCities) - repmat(coordinates(:,1)',noOfCities,1);
    ydist = repmat(coordinates(:,2),1,noOfCities) - repmat(coordinates(:,2)',noOfCities,1);
    distances = sqrt(xdist.^2 + ydist.^2);
    distances(find(eye(noOfCities))) = Inf;
    
    % Nearest Neighbor Construction Heuristics
    tic;
    [routeNN, lenNN, startCityNN] = useNearestNeighbor(distances);
    graphNN = createGraph(coordinates, routeNN);
    figure;
    plot(graphNN(:,1),graphNN(:,2));    
    title('Route with Nearest Neighbor');
    axis([-3 125 -3 100]);
    hold on;
    plot(coordinates(startCityNN,1), coordinates(startCityNN,2), 'r*');
    hold off;
    text(130,25,['l=',num2str(round(lenNN))]);
    toc;
    
    % Best Insertion Construction Heuristics
    tic;
    [routeBI, lenBI, startSelecteBI] = useBestInsertion(distances);
    graphBI = createGraph(coordinates, routeBI);
    figure;
    plot(graphBI(:,1),graphBI(:,2));    
    title('Route with Best Insertion');
    axis([-3 125 -3 100]);
    hold on;
    plot(coordinates(startSelecteBI(1),1), coordinates(startSelecteBI(1),2), 'r*');
    plot(coordinates(startSelecteBI(2),1), coordinates(startSelecteBI(2),2), 'r*');
    plot(coordinates(startSelecteBI(3),1), coordinates(startSelecteBI(3),2), 'r*');
    hold off;
    text(130,25,['l=',num2str(round(lenBI))]);
    toc;
    
    % Cheapest Insertion Construction Heuristics
    tic;
    [routeCI, lenCI, startSelecteCI] = useCheapestInsertion(distances);
    graphCI = createGraph(coordinates, routeCI);
    figure;
    plot(graphCI(:,1),graphCI(:,2));    
    title('Route with Cheapest Insertion');
    axis([-3 125 -3 100]);
    hold on;
    plot(coordinates(startSelecteCI,1), coordinates(startSelecteCI,2), 'r*');
    hold off;
    text(130,25,['l=',num2str(round(lenCI))]);
    toc;
    
    % Local Search Improvement Heuristics using Swap only
    tic;
    [routeLSS, lenLSS] = useLocalSearch(distances, 1);
    graphLSS = createGraph(coordinates, routeLSS);
    figure;
    subplot(2,2,1);
    plot(graphLSS(:,1),graphLSS(:,2));    
    title('Route with Local Search (Swap)');
    axis([-3 125 -3 100]);
    text(130,25,['l=',num2str(round(lenLSS))]);
    toc;
    
    % Local Search Improvement Heuristics using Translation only
    tic;
    [routeLST, lenLST] = useLocalSearch(distances, 2);
    graphLST = createGraph(coordinates, routeLST);
    subplot(2,2,2);
    plot(graphLST(:,1),graphLST(:,2));    
    title('Route with Local Search (Translation)');
    axis([-3 125 -3 100]);
    text(130,25,['l=',num2str(round(lenLST))]);
    toc;
    
    % Local Search Improvement Heuristics using Inversion only
    tic;
    [routeLSI, lenLSI] = useLocalSearch(distances, 3);
    graphLSI = createGraph(coordinates, routeLSI);
    subplot(2,2,3);
    plot(graphLSI(:,1),graphLSI(:,2));
    title('Route with Local Search (Inversion)');
    axis([-3 125 -3 100]);
    text(130,25,['l=',num2str(round(lenLSI))]);
    toc;
    
    % Local Search Improvement Heuristics using Swap, Translation & Inversion
    tic;
    [routeLSSTI, lenLSSTI] = useLocalSearch(distances, 4);
    graphLSSTI = createGraph(coordinates, routeLSSTI);
    subplot(2,2,4);
    plot(graphLSSTI(:,1),graphLSSTI(:,2));
    title('Route with Local Search (Swap, Translation, Inversion)');
    axis([-3 125 -3 100]);
    text(130,25,['l=',num2str(round(lenLSSTI))]);
    toc;
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
