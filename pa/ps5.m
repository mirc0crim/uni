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
distances = sqrt(xdist.^2 + ydist.^2);
distances(find(triu(ones(131,131)))) = Inf;
route = zeros(131,131);
myroute = ones(262,2);
c = 1;
while c < 132
    index = find(distances == min(min(distances)), 1);
    [y,x] = ind2sub([131,131], index);
    distances(y,x) = Inf;
    valid = true;
    testRoute = route;
    testRoute(y,x) = 1;
    incomming = sum(testRoute,1);
    outgoing = sum(testRoute,2);
    if max(incomming + outgoing') > 2
        %disp('degree exeeded');
        valid = false;
    else
        for n = 1:c
            if trace(testRoute^n) ~= 0
                %disp('cycle detected');
                valid = false;
                break;
            end
        end
    end
    if valid
        %disp(['shortest distance from ', num2str(x), ' to ', num2str(y)]);
        myroute(2*x-1,:) = coordinates(x,:);
        myroute(2*x,:) = coordinates(y,:);
        route(y,x) = 1;
        c = c + 1;
        disp(c)
    end
end

subplot(1,2,1);
scatter(coordinates(:,1),coordinates(:,2));
subplot(1,2,2);

plot(myroute(:,1),myroute(:,2));
