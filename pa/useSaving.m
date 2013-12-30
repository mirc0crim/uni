function [route, dist, startSelected] = useSaving(distances)
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    r = randperm(noOfCities);
    startSelected = r(1);
    tours = zeros(noOfCities, noOfCities);
    tours(1:end-1,1) = r(1);
    tours(1:end-1,2) = r(2:end);
    methodsMat = zeros(noOfCities, noOfCities);
    savingsMat = zeros(noOfCities, noOfCities);
    for i=1:noOfCities
        for j=1:noOfCities
            [V,I] = getSaving(i, j, tours, distances);
            methodsMat(i,j) = I;
            methodsMat(j,i) = I;
            savingsMat(i,j) = V;
            savingsMat(j,i) = V;
        end
    end
    while length(find(max(tours)==0)) > 0
        [V,I] = max(savingsMat);
        [~, b] = max(V);
        a = I(b);
        method = methodsMat(a,b);
        indNa = length(find(tours(a,:) ~= 0));
        indNb = length(find(tours(b,:) ~= 0));
        if method == 1
           tourB = tours(b, 2:indNb);
           tours(a,indNa+1:indNa+length(tourB)) = tourB;
           tours(b,:) = 0;
        elseif method == 2
           tourB = tours(b, 2:indNb);
           tours(a,2:indNa) = tours(a, indNa:-1:2);
           tours(a,indNa+1:indNa+length(tourB)) = tourB;
           tours(b,:) = 0;
        elseif method == 3
           tourA = tours(a, 2:indNa);
           tours(b,indNb+1:indNb+length(tourA)) = tourA;
           tours(a,:) = 0;
        elseif method == 4
           tourA = tours(a, 2:indNa);
           tours(b,2:indNb) = tours(b, indNb:-1:2);
           tours(b,indNb+1:indNb+length(tourA)) = tourA;
           tours(a,:) = 0;
        end
        
        if method < 3
           for i=1:noOfCities
               [V,I] = getSaving(a, i, tours, distances);
               methodsMat(a,i) = I;
               methodsMat(i,a) = I;
               savingsMat(a,i) = V;
               savingsMat(i,a) = V;
           end
           methodsMat(b,:) = 0;
           methodsMat(:,b) = 0;
           savingsMat(b,:) = 0;
           savingsMat(:,b) = 0;
        elseif method > 2
           for i=1:noOfCities
               [V,I] = getSaving(b, i, tours, distances);
               methodsMat(b,i) = I;
               methodsMat(i,b) = I;
               savingsMat(b,i) = V;
               savingsMat(i,b) = V;
           end
           methodsMat(a,:) = 0;
           methodsMat(:,a) = 0;
           savingsMat(a,:) = 0;
           savingsMat(:,a) = 0;
        end
    end
    row = find(tours(:,1) ~= 0);
    dist = calcLen(distances, tours(row,:));
    for i=1:noOfCities-1
       route(tours(row,i), tours(row,i+1)) = 1;
    end
    route(tours(row,end), tours(row,1)) = 1;
end

function [V, I] = getSaving(a, b, tours, distances)
    if a == 0 || b == 0 || a == b
        V = 0;
        I = 0;
        return;
    end
    indNa = length(find(tours(a,:) ~= 0));
    indNb = length(find(tours(b,:) ~= 0));
    if indNa == 0 || indNb == 0
        V = 0;
        I = 0;
        return;
    end
    Na = tours(a, indNa);
    Nb = tours(b, indNb);
    startSelected = max(tours(:,1));
    s1 = distances(Na, startSelected) + distances(startSelected, tours(b, 2)) ...
        - distances(Na, tours(b, 2));
    s2 = distances(tours(a, 2), startSelected) + distances(startSelected, tours(b, 2)) ...
        - distances(tours(a, 2), tours(b, 2));
    s3 = distances(Nb, startSelected) + distances(startSelected, tours(a, 2)) ...
        - distances(Nb, tours(a, 2));
    s4 = distances(Na, startSelected) + distances(startSelected, Nb) ...
        - distances(Na, Nb);
    [V, I] = max([s1,s2,s3,s4]);
end
