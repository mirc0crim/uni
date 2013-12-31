function [route, dist, bestL, worstL, meanL] = useGeneticAlgorithm(distances, mode)
    % mode
    % 1 = Crossover
    % 2 = Swap Mutation
    % 3 = Translation Mutation
    % 4 = Inversion Mutation
    noOfCities = length(distances);
    route = zeros(noOfCities,noOfCities);
    generations = 1000;
    bestL = zeros(generations, 1);
    worstL = zeros(generations, 1);
    meanL = zeros(generations, 1);
    genes = zeros(100, noOfCities);
    lenBefore = zeros(100, 1);
    for i=1:100 %initialize 100 genes
        genes(i,:) = randperm(noOfCities);
        lenBefore(i) = calcLen(distances, genes(i,:));
    end
    for j=1:generations
        for i=1:100 % mutate each gene
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
            genes(i,:) = newR;
            lenBefore(i) = calcLen(distances, newR);
        end
        [~, I] = sort(lenBefore);
        for k=1:4 % select best 25% genes
            genes((k-1)*25+1:k*25,:) = genes(I(1:25),:);
            lenBefore((k-1)*25+1:k*25) = lenBefore(I(1:25));
        end
        bestL(j) = min(lenBefore);
        worstL(j) = max(lenBefore);
        meanL(j) = mean(lenBefore);
    end
    [V, I] = min(lenBefore);
    dist = V;
    for i=1:noOfCities-1
        route(genes(I,i), genes(I,i+1)) = 1;
    end
    route(genes(I,end), genes(I,1)) = 1;
end
