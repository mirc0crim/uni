function tspImproved()
    close All;
    clear All;
    clc;
    
    bigFile = true;
    
    if bigFile
        noOfCities = 411;
        ax = [-3 125 -3 100];
        textX = 130;
        fid = fopen('TSP_411.txt', 'r'); % takes 4 min
    else
        noOfCities = 131;
        ax = [-3 110 -3 50];
        textX = 115;
        fid = fopen('data131.txt', 'r'); % takes 1 min
    end

    coordinates = zeros(noOfCities,2);
    
    % Read coordinates from text file
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
    axis(ax)
    
    xdist = repmat(coordinates(:,1),1,noOfCities) - repmat(coordinates(:,1)',noOfCities,1);
    ydist = repmat(coordinates(:,2),1,noOfCities) - repmat(coordinates(:,2)',noOfCities,1);
    distances = sqrt(xdist.^2 + ydist.^2);
    distances(find(eye(noOfCities))) = Inf;
    
    % Best Insertion Construction Heuristics
    tic;
    minLen = Inf;
    bestRoute = [];
    for i=1:40
        [routeBI, lenBI, ~] = useBestInsertion(distances);
        if lenBI < minLen
            bestRoute = routeBI;
            minLen = lenBI;
        end
    end
    toc;
    
    % Local Search Improvement Heuristics using Swap, Translation & Inversion
    tic;
    figure;
    sequence = routeToSeq(bestRoute);
    [routeLSSTI, lenLSSTI] = useLocalSearch2(distances, sequence');
    graphLSSTI = createGraph(coordinates, routeLSSTI);
    plot(graphLSSTI(:,1),graphLSSTI(:,2));
    title('Local Search (Swap, Translation, Inversion)');
    axis(ax)
    hold on;
    scatter(coordinates(:,1),coordinates(:,2), 'g.');
    hold off;
    text(textX,25,['l=',num2str(round(lenLSSTI))]);
    toc;

end

function s = routeToSeq(r)
    noOfCities = length(r);
    s = zeros(noOfCities, 1);
    I = 1;
    for i=1:noOfCities
        s(i) = I;
        [~,I] = max(r(I,:));
    end
end
