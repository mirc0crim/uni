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

%scatter(coordinates(:,1),coordinates(:,2));

xdist = repmat(coordinates(:,1),1,131) - repmat(coordinates(:,1)',131,1);
ydist = repmat(coordinates(:,2),1,131) - repmat(coordinates(:,2)',131,1);
distances = sqrt(xdist.^2 + ydist.^2);
distances(find(triu(ones(131,131)))) = Inf;

index = min(find(distances == min(min(distances))));
x = mod(index,131)-1;
y = index - (x-1)*131;
disp(['shortest distance from ', num2str(x), ' to ', num2str(y)]);
distances(y,x) = Inf;
