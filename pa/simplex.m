function [L, A, b] = simplex(L, A, b)
    arrayDim = size(A);
    ind = zeros(1,arrayDim(1)+arrayDim(2));
    for i = 1:arrayDim(1)+arrayDim(2)
        ind(i) = i;
    end
    counter = 0;
    while counter < 10
        simplexTable = A;
        simplexTable(:,arrayDim(2)+1) = b';
        if arrayDim(2)+1 ~= length(L)
            L = [L,0];
        end
        simplexTable(arrayDim(1)+1,:) = L;
        if min(simplexTable(arrayDim(1),:)) >= 0
            disp(simplexTable)
            for i = 1:arrayDim(1)+arrayDim(2)
                if ind(i) <= arrayDim(1)
                    if i <= arrayDim(2)
                        disp(['x',num2str(ind(i)),'=',num2str(simplexTable(arrayDim(1)+1,i))]);
                    else
                        disp(['x',num2str(ind(i)),'=',num2str(simplexTable(i-arrayDim(2),arrayDim(2)+1))]);
                    end
                end
            end
            return
        end
        [~, s] = min(simplexTable(arrayDim(1)+1,:));
        if max(simplexTable(1:arrayDim(1),s)) <= 0
            disp('no finite solution')
            return
        end
        pos = simplexTable(1:arrayDim(1),s);
        pos(pos < 0) = 0;
        [~, r] = min(simplexTable(1:arrayDim(1),arrayDim(2)+1)./pos);
        tempTable = simplexTable;
        % update A
        for i = 1:arrayDim(1)
            for j = 1:arrayDim(2)
                if i ~= r && j ~= s
                    tempTable(i,j) = simplexTable(i,j) - simplexTable(i,s)*simplexTable(r,j)/simplexTable(r,s);
                end
                if i == r && j == s
                    tempTable(i,j) = 1/simplexTable(r,s);
                end
                if i ~= r && j == s
                    tempTable(i,j) = -simplexTable(i,s)/simplexTable(r,s);
                end
                if i == r && j ~= s
                    tempTable(i,j) = simplexTable(r,j)/simplexTable(r,s);
                end
            end
        end
        % update b
        ais = simplexTable(:,s);
        ars = simplexTable(r,s);
        br = simplexTable(r,arrayDim(2)+1);
        bi = simplexTable(:,arrayDim(2)+1);
        tempTable(:,arrayDim(2)+1) = (bi - (br*ais)/ars)';
        tempTable(r,arrayDim(2)+1) = br/ars;
        % update d
        arj = simplexTable(r,:);
        ds = simplexTable(arrayDim(1)+1,s);
        dj = simplexTable(arrayDim(1)+1,:);
        tempTable(arrayDim(1)+1,:) = dj - (ds * arj)/ars;
        tempTable(arrayDim(1)+1,s) = -ds/ars;
        % update v
        tempTable(arrayDim(1)+1,arrayDim(2)+1) = simplexTable(arrayDim(1)+1,arrayDim(2)+1) + ds*br/ars;
        
        simplexTable = tempTable;
        L = simplexTable(arrayDim(1)+1,:);
        A = simplexTable(1:arrayDim(1),1:arrayDim(2));
        b = simplexTable(1:arrayDim(1),arrayDim(2)+1);
        tempIndex = ind(s);
        ind(s) = ind(arrayDim(2)+r);
        ind(arrayDim(2)+r) = tempIndex;
        counter = counter + 1;
    end
end
