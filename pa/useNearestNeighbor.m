function [route, dist, startCity] = useNearestNeighbor(distances)
    origDistances = distances;
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    startCity = ceil(rand(1)*noOfCities);
    cityA = startCity;
    distances(:,startCity) = inf;
    c = 1;
    dist = 0;
    while c < noOfCities
        [~, cityB] = min(distances(cityA,:));
        route(cityA, cityB) = 1;
        dist = dist + distances(cityA, cityB);
        distances(:,cityB) = inf;
        cityA = cityB;
        c = c + 1;
    end
    route(cityA, startCity) = 1;
    dist = dist + origDistances(cityA, startCity);
end
