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

scatter(coordinates(:,1),coordinates(:,2));