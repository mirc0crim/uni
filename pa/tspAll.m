function tspAll()
    close All;
    clear All;
    clc;

    profile on;
    
    bigFile = true;
    
    if bigFile
        noOfCities = 411;
        ax = [-3 125 -3 100];
        textX = 130;
        fid = fopen('TSP_411.txt', 'r');
    else
        noOfCities = 131;
        ax = [-3 110 -3 50];
        textX = 115;
        fid = fopen('data131.txt', 'r');
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
    subplot(4,4,1:2);
    scatter(coordinates(:,1),coordinates(:,2));
    title('Location of the Cities')
    axis(ax)
    
    xdist = repmat(coordinates(:,1),1,noOfCities) - repmat(coordinates(:,1)',noOfCities,1);
    ydist = repmat(coordinates(:,2),1,noOfCities) - repmat(coordinates(:,2)',noOfCities,1);
    distances = sqrt(xdist.^2 + ydist.^2);
    distances(find(eye(noOfCities))) = Inf;
    
    % Nearest Neighbor Construction Heuristics
    disp('NN');
    tic;
    [routeNN, lenNN, startCityNN] = useNearestNeighbor(distances);
    graphNN = createGraph(coordinates, routeNN);
    subplot(4,4,3);
    plot(graphNN(:,1),graphNN(:,2));    
    title('Nearest Neighbor');
    axis(ax)
    hold on;
    plot(coordinates(startCityNN,1), coordinates(startCityNN,2), 'r*');
    hold off;
    text(textX,25,['l=',num2str(round(lenNN))]);
    toc;
    
    % Best Insertion Construction Heuristics
    disp('BI');
    tic;
    [routeBI, lenBI, startSelecteBI] = useBestInsertion(distances);
    graphBI = createGraph(coordinates, routeBI);
    subplot(4,4,4);
    plot(graphBI(:,1),graphBI(:,2));    
    title('Best Insertion');
    axis(ax)
    hold on;
    plot(coordinates(startSelecteBI(1),1), coordinates(startSelecteBI(1),2), 'r*');
    plot(coordinates(startSelecteBI(2),1), coordinates(startSelecteBI(2),2), 'r*');
    plot(coordinates(startSelecteBI(3),1), coordinates(startSelecteBI(3),2), 'r*');
    hold off;
    text(textX,25,['l=',num2str(round(lenBI))]);
    toc;
    
    % Cheapest Insertion Construction Heuristics
    disp('CI');
    tic;
    [routeCI, lenCI, startSelecteCI] = useCheapestInsertion(distances);
    graphCI = createGraph(coordinates, routeCI);
    subplot(4,4,5);
    plot(graphCI(:,1),graphCI(:,2));    
    title('Cheapest Insertion');
    axis(ax)
    hold on;
    plot(coordinates(startSelecteCI,1), coordinates(startSelecteCI,2), 'r*');
    hold off;
    text(textX,25,['l=',num2str(round(lenCI))]);
    toc;
    
    % Saving Construction Heuristics
    disp('S');
    tic;
    [routeS, lenS, startSelecteS] = useSaving(distances);
    graphS = createGraph(coordinates, routeS);
    subplot(4,4,6);
    plot(graphS(:,1),graphS(:,2));    
    title('Saving');
    axis(ax)
    hold on;
    plot(coordinates(startSelecteS,1), coordinates(startSelecteS,2), 'r*');
    hold off;
    text(textX,25,['l=',num2str(round(lenS))]);
    toc;
    
    % Local Search Improvement Heuristics using Swap only
    disp('LS');
    tic;
    [routeLSS, lenLSS, ~] = useLocalSearch(distances, 1);
    graphLSS = createGraph(coordinates, routeLSS);
    subplot(4,4,7);
    plot(graphLSS(:,1),graphLSS(:,2));    
    title('Local Search (Swap)');
    axis(ax)
    text(textX,25,['l=',num2str(round(lenLSS))]);
    toc;
    
    % Local Search Improvement Heuristics using Translation only
    tic;
    [routeLST, lenLST, ~] = useLocalSearch(distances, 2);
    graphLST = createGraph(coordinates, routeLST);
    subplot(4,4,8);
    plot(graphLST(:,1),graphLST(:,2));    
    title('Local Search (Translation)');
    axis(ax)
    text(textX,25,['l=',num2str(round(lenLST))]);
    toc;
    
    % Local Search Improvement Heuristics using Inversion only
    tic;
    [routeLSI, lenLSI, ~] = useLocalSearch(distances, 3);
    graphLSI = createGraph(coordinates, routeLSI);
    subplot(4,4,9);
    plot(graphLSI(:,1),graphLSI(:,2));
    title('Local Search (Inversion)');
    axis(ax)
    text(textX,25,['l=',num2str(round(lenLSI))]);
    toc;
    
    % Local Search Improvement Heuristics using Swap, Translation & Inversion
    tic;
    [routeLSSTI, lenLSSTI, ~] = useLocalSearch(distances, 4);
    graphLSSTI = createGraph(coordinates, routeLSSTI);
    subplot(4,4,10);
    plot(graphLSSTI(:,1),graphLSSTI(:,2));
    title('Local Search (Swap, Translation, Inversion)');
    axis(ax)
    text(textX,25,['l=',num2str(round(lenLSSTI))]);
    toc;
    
    % Simulated Annealing Improvement Heuristics using Metropolis
    disp('SA');
    tic;
    [routeSAM, lenSAM, ~] = useSimulatedAnnealing(distances, 1);
    graphSAM = createGraph(coordinates, routeSAM);
    subplot(4,4,11);
    plot(graphSAM(:,1),graphSAM(:,2));
    title('Simulated Annealing (Metropolis)');
    axis(ax)
    text(textX,25,['l=',num2str(round(lenSAM))]);
    toc;
    
    % Simulated Annealing Improvement Heuristics using Heat bath
    tic;
    [routeSAHB, lenSAHB, ~] = useSimulatedAnnealing(distances, 2);
    graphSAHB = createGraph(coordinates, routeSAHB);
    subplot(4,4,12);
    plot(graphSAHB(:,1),graphSAHB(:,2));
    title('Simulated Annealing (Heat bath)');
    axis(ax)
    text(textX,25,['l=',num2str(round(lenSAHB))]);
    toc;
    
    % Genetic Algorithm Improvement Heuristics using Crossover
    disp('GA');
    tic;
    [routeGAC, lenGAC, ~] = useGeneticAlgorithm(distances, 1);
    graphGAC = createGraph(coordinates, routeGAC);
    subplot(4,4,13);
    plot(graphGAC(:,1),graphGAC(:,2));
    title('Genetic Algorithm (Crossover)');
    axis(ax)
    text(textX,25,['l=',num2str(round(lenGAC))]);
    toc;
    
    % Genetic Algorithm Improvement Heuristics using Swap Mutation
    tic;
    [routeGASM, lenGASM, ~] = useGeneticAlgorithm(distances, 2);
    graphGASM = createGraph(coordinates, routeGASM);
    subplot(4,4,14);
    plot(graphGASM(:,1),graphGASM(:,2));
    title('Genetic Algorithm (Swap Mutation)');
    axis(ax)
    text(textX,25,['l=',num2str(round(lenGASM))]);
    toc;
    
    % Genetic Algorithm Improvement Heuristics using Translation Mutation
    tic;
    [routeGATM, lenGATM, ~] = useGeneticAlgorithm(distances, 3);
    graphGATM = createGraph(coordinates, routeGATM);
    subplot(4,4,15);
    plot(graphGATM(:,1),graphGATM(:,2));
    title('Genetic Algorithm (Translation Mutation)');
    axis(ax)
    text(textX,25,['l=',num2str(round(lenGATM))]);
    toc;
    
    % Genetic Algorithm Improvement Heuristics using Inversion Mutation
    tic;
    [routeGAIM, lenGAIM, ~] = useGeneticAlgorithm(distances, 4);
    graphGAIM = createGraph(coordinates, routeGAIM);
    subplot(4,4,16);
    plot(graphGAIM(:,1),graphGAIM(:,2));
    title('Genetic Algorithm (Inversion Mutation)');
    axis(ax)
    text(textX,25,['l=',num2str(round(lenGAIM))]);
    toc;
    
    profile off;
    profile viewer;
end
