function [route, dist, startSelected] = useSaving(distances)
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    r = randperm(noOfCities);
    startSelected = r(1);
    tours = zeros(noOfCities-1, noOfCities);
    tours(:,1) = r(1);
    tours(:,2) = r(2:end);
    while length(find(max(tours)==0)) > 0
        maxSave = 0;
        bestA = 0;
        bestB = 0;
        method = 0;
        possible = find(tours(:,1) ~= 0);
        for i=1:length(possible)
            for j=i+1:length(possible)
                a = possible(i);
                indNa = length(find(tours(a,:) ~= 0));
                Na = tours(a, indNa);
                b = possible(j);
                indNb = length(find(tours(b,:) ~= 0));
                Nb = tours(b, indNb);
                s1 = distances(Na, startSelected) + distances(startSelected, tours(b, 2)) ...
                    - distances(Na, tours(b, 2));
                s2 = distances(tours(a, 2), startSelected) + distances(startSelected, tours(b, 2)) ...
                    - distances(tours(a, 2), tours(b, 2));
                s3 = distances(Nb, startSelected) + distances(startSelected, tours(a, 2)) ...
                    - distances(Nb, tours(a, 2));
                s4 = distances(Na, startSelected) + distances(startSelected, Nb) ...
                    - distances(Na, Nb);
                [V, I] = max([s1,s2,s3,s4]);
                if V > maxSave
                    method = I;
                    bestA = a;
                    bestB = b;
                    maxSave = V;
                end
            end
        end
        a = bestA;
        b = bestB;
        indNa = length(find(tours(a,:) ~= 0));
        indNb = length(find(tours(b,:) ~= 0));
        if method == 1
           tourB = tours(b, 2:indNb);
           tours(a,indNa+1:indNa+length(tourB)) = tourB;
           tours(b,:) = 0;
        end
        if method == 2
           tourB = tours(b, 2:indNb);
           tours(a,2:indNa) = tours(a, indNa:-1:2);
           tours(a,indNa+1:indNa+length(tourB)) = tourB;
           tours(b,:) = 0;
        end
        if method == 3
           tourA = tours(a, 2:indNa);
           tours(b,indNb+1:indNb+length(tourA)) = tourA;
           tours(a,:) = 0;
        end
        if method == 4
           tourA = tours(a, 2:indNa);
           tours(b,2:indNb) = tours(b, indNb:-1:2);
           tours(b,indNb+1:indNb+length(tourA)) = tourA;
           tours(a,:) = 0;
        end
        if mod(length(find(max(tours)==0)),20) == 0
            disp(length(find(max(tours)==0)));
        end
    end
    row = find(tours(:,1) ~= 0);
    dist = calcLen(distances, tours(row,:));
    for i=1:noOfCities-1
       route(tours(row,i), tours(row,i+1)) = 1;
    end
    route(tours(row,end), tours(row,1)) = 1;
end

function d = calcLen(distances, selected)
    noOfCities = length(selected);
    d = 0;
    for i=1:noOfCities-1
        d = d + distances(selected(i), selected(i+1));
    end
    d = d + distances(selected(end), selected(1));
end