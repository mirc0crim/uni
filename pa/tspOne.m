function tspOne()
    close All;
    clear All;
    clc;
    format short g
    tic;
    
    noOfCities = 411;
    coordinates = zeros(noOfCities,2);
    
    % Read coordinates from text file
    fid = fopen('TSP_411.txt', 'r');
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
    
    xdist = repmat(coordinates(:,1),1,noOfCities) - repmat(coordinates(:,1)',noOfCities,1);
    ydist = repmat(coordinates(:,2),1,noOfCities) - repmat(coordinates(:,2)',noOfCities,1);
    distances = sqrt(xdist.^2 + ydist.^2);
    distances(find(eye(noOfCities))) = Inf;
    
    lenFound = zeros(40,1);
    routeFound = zeros(40,noOfCities+1,2);
    startChosen = zeros(40,3);
    
    for i=1:40
        [route, len, start] = useNearestNeighbor(distances);
        %[route, len, start] = useBestInsertion(distances);
        %[route, len, start] = useCheapestInsertion(distances);
        %[route, len, start] = useSaving(distances);
        %[route, len] = useLocalSearch(distances, 1);
        %[route, len] = useLocalSearch(distances, 2);
        %[route, len] = useLocalSearch(distances, 3);
        %[route, len] = useLocalSearch(distances, 4);
        %[route, len] = useSimulatedAnnealing(distances, 1);
        %[route, len] = useSimulatedAnnealing(distances, 2);
        %[route, len] = useGeneticAlgorithm(distances, 1);
        %[route, len] = useGeneticAlgorithm(distances, 2);
        %[route, len] = useGeneticAlgorithm(distances, 3);
        %[route, len] = useGeneticAlgorithm(distances, 4);
        
        lenFound(i) = len;
        routeFound(i,:,:) = createGraph(coordinates, route);
        startChosen(i,:) = start;
        disp(i);
    end
    
    figure;
    [~, I] = sort(lenFound);
    scatter(coordinates(:,1),coordinates(:,2), 'g.');
    hold on;
    plot(routeFound(I(1),:,1),routeFound(I(1),:,2));
    plot(coordinates(startChosen(I(1),1),1), coordinates(startChosen(I(1),1),2), 'r*');
    plot(coordinates(startChosen(I(1),2),1), coordinates(startChosen(I(1),2),2), 'r*');
    plot(coordinates(startChosen(I(1),3),1), coordinates(startChosen(I(1),3),2), 'r*');
    hold off;
    axis([-3 125 -3 100]);
    text(130,25,['l=', num2str(round(lenFound(I(1))))]);
    
    averageLength = sum(lenFound)/40
    
    toc;
end
