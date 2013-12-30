function [route, dist] = useGeneticAlgorithm(distances, mode)
    % mode
    % 1 = Crossover
    % 2 = Swap Mutation
    % 3 = Translation Mutation
    % 4 = Inversion Mutation
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    genes = zeros(100, noOfCities);
    lenBefore = zeros(100, 1);
    for i=1:100 %initialize 100 genes
        genes(i,:) = randperm(noOfCities);
        lenBefore(i) = calcLen(distances, genes(i,:));
    end
    for j=1:2000 % generations
        for i=1:100
            if mode == 1
                newR = crossover(genes(i,:));
            end
            if mode == 2
                newR = swap(genes(i,:));
            end
            if mode == 3
                newR = translation(genes(i,:));
            end
            if mode == 4
                newR = inversion(genes(i,:));
            end
            lenAfter = calcLen(distances, newR);
            if lenAfter <= lenBefore(i)
                genes(i,:) = newR;
                lenBefore(i) = lenAfter;
            end
        end
        [~, I] = sort(lenBefore);
        for k=1:20
            genes((k-1)*5+1:k*5,:) = genes(I(1:5),:);
            lenBefore((k-1)*5+1:k*5) = lenBefore(I(1:5));
        end
    end
    [V, I] = min(lenBefore);
    dist = V;
    for i=1:noOfCities-1
        route(genes(I,i), genes(I,i+1)) = 1;
    end
    route(genes(I,end), genes(I,1)) = 1;
end
