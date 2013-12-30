function d = calcLen(distances, selected)
    noOfCities = length(distances);
    sel2 = [selected(2:end), selected(1)];
    d = sum(distances((sel2-1)*noOfCities + selected));
end
