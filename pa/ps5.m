close All;
clear All;
clc;

coordinates = zeros(131,2);

fid = fopen('data131.txt', 'r');
tline = fgets(fid);
i = 1;
while ischar(tline)
    if tline(1) ~= 'N'
        m = regexp(tline, '\d+');
        coordinates(i,:) = [str2double(tline(m(2):m(3)-2)), str2double(tline(m(3):end))];
        i = i + 1;
    end
    tline = fgets(fid);
end
fclose(fid);

xdist = repmat(coordinates(:,1),1,131) - repmat(coordinates(:,1)',131,1);
ydist = repmat(coordinates(:,2),1,131) - repmat(coordinates(:,2)',131,1);

% Euclidean Distance
distances = sqrt(xdist.^2 + ydist.^2);

% Manhattan Distance
%distances = abs(xdist) + abs(ydist);

distances(find(eye(131))) = Inf;

route = zeros(131,131);
c = 1;
while c < 132
    index = find(distances == min(min(distances)), 1);
    [y,x] = ind2sub([131,131], index);
    distances(y,x) = Inf;
    distances(x,y) = Inf;
    valid = true;
    testRoute = route;
    testRoute(y,x) = 1;
    incomming = sum(testRoute,1);
    outgoing = sum(testRoute,2);
    if max(incomming + outgoing') > 2
        % degree exeeded
        valid = false;
    else
        for n = 1:c
            if trace(testRoute^n) ~= 0
                % cycle detected
                valid = false;
                break;
            end
        end
    end
    if valid || c == 131
        % shortest distance from x to y
        route(y,x) = 1;
        distances(:,x) = Inf;
        distances(y,:) = Inf;
        c = c + 1;
        disp(c)
    end
end

subplot(1,2,1);
scatter(coordinates(:,1),coordinates(:,2));
subplot(1,2,2);
i = 1;
j = 1;
A = ones(132,2);
while i < 132
    A(i,:) = coordinates(j,:);
    j = find(route(j,:));
    i = i + 1;
end
A(i,:) = coordinates(1,:);
plot(A(:,1),A(:,2));
