function tspOne()
    close All;
    clear All;
    clc;
    format short g
    tic;
    
    %----------------
    heuristic = 1;
    % 1-4   Construction NN, BI, CI, S
    % 5-8   Local Search S, T, I, STI
    % 9,10  Simulated Annealing M, HB
    % 11-14 Genetic Algorithm C, S, T, I
    %----------------
    
    noOfCities = 411;
    runs = 40;
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
    
    lenFound = zeros(runs,1);
    routeFound = zeros(runs,noOfCities+1,2);
    startChosen = zeros(runs,3);
    if heuristic > 10
        k = 1000;
    elseif heuristic > 8
        k = 405;
    elseif heuristic > 4
        k = noOfCities^2*5;
    else
        k = 1;
    end
    currLs = zeros(40,k);
    bestLs = zeros(40,k);
    worstLs = zeros(40,k);
    meanLs = zeros(40,k);
    
    for i=1:runs
        if heuristic == 1
            [route, len, start] = useNearestNeighbor(distances); % <0.5sec
        elseif heuristic == 2
            [route, len, start] = useBestInsertion(distances); % ~1min
        elseif heuristic == 3
            [route, len, start] = useCheapestInsertion(distances); % ~15min
        elseif heuristic == 4
            [route, len, start] = useSaving(distances); % ~3.5min
        elseif heuristic == 5
            [route, len, currL] = useLocalSearch(distances, 1);
        elseif heuristic == 6
            [route, len, currL] = useLocalSearch(distances, 2);
        elseif heuristic == 7
            [route, len, currL] = useLocalSearch(distances, 3);
        elseif heuristic == 8
            [route, len, currL] = useLocalSearch(distances, 4);
        elseif heuristic == 9
            [route, len, bestL, worstL, meanL] = useSimulatedAnnealing(distances, 1); % ~8min
        elseif heuristic == 10
            [route, len, bestL, worstL, meanL] = useSimulatedAnnealing(distances, 2); % ~9min
        elseif heuristic == 11
            [route, len, bestL, worstL, meanL] = useGeneticAlgorithm(distances, 1); % ~2min
        elseif heuristic == 12
            [route, len, bestL, worstL, meanL] = useGeneticAlgorithm(distances, 2); % ~2min
        elseif heuristic == 13
            [route, len, bestL, worstL, meanL] = useGeneticAlgorithm(distances, 3); % ~2min
        elseif heuristic == 14
            [route, len, bestL, worstL, meanL] = useGeneticAlgorithm(distances, 4); % ~2min
        end
        
        lenFound(i) = len;
        routeFound(i,:,:) = createGraph(coordinates, route);
        if heuristic < 5
            startChosen(i,:) = start;
        end
        if heuristic > 8
            bestLs(i,:) = bestL;
            worstLs(i,:) = worstL;
            meanLs(i,:) = meanL;
        end
        if heuristic > 4 && heuristic < 9
            currLs(i,:) = currL;
        end
        disp(i);
    end
    
    figure;
    [~, I] = sort(lenFound);
    scatter(coordinates(:,1),coordinates(:,2), 'g.');
    hold on;
    plot(routeFound(I(1),:,1),routeFound(I(1),:,2));
    if heuristic < 5
        plot(coordinates(startChosen(I(1),1),1), coordinates(startChosen(I(1),1),2), 'r*');
        plot(coordinates(startChosen(I(1),2),1), coordinates(startChosen(I(1),2),2), 'r*');
        plot(coordinates(startChosen(I(1),3),1), coordinates(startChosen(I(1),3),2), 'r*');
    end
    hold off;
    axis([-3 125 -3 100]);
    text(130,25,['l=', num2str(round(lenFound(I(1))))]);
    
    if heuristic > 4
        figure;
        hold on;
        if heuristic > 10
            k = 1:1000;
        elseif heuristic > 8
            set(gca,'XDir','Reverse')
            T = 10000;
            k = zeros(405,1);
            c = 1;
            while T > 0.00001
                k(c) = log10(T);
                c = c + 1;
                T = T * 0.95;
            end
        else
            k = log10(1:noOfCities^2*5);
        end
        if heuristic > 8
            for i=1:runs
                plot(k, bestLs(i,:), 'g');
                plot(k, worstLs(i,:), 'r');
                plot(k, meanLs(i,:), 'b');
            end
        else
            for i=1:runs
                plot(k, log10(min(currLs)), 'g');
                plot(k, log10(max(currLs)), 'r');
                plot(k, log10(mean(currLs)), 'b');
            end
        end
        hold off;
    end
    
    figure;
    disp(['best ', num2str(min(lenFound))]);
    disp(['worst ', num2str(max(lenFound))]);
    disp(['average ', num2str(mean(lenFound))]);
    averageLength = mean(lenFound);
    stdDev = std(lenFound);
    upper = averageLength+1.96*stdDev;
    lower = averageLength-1.96*stdDev;
    disp(['upper ', num2str(upper)]);
    disp(['lower ', num2str(lower)]);
    plot(1:runs,upper,'r.',1:runs,lower,'r.',1:runs,lenFound,'bo');
    
    toc;
end
