function opt = ps4()
    clear All;
    close All;
    clc;
    % Step 0 initialization
    theta = [1,2;3,2;0.25,0.5];
    results = zeros(length(theta),1);
    for i = 1:length(theta)
        results(i) = L(theta(i,:));
    end
    alpha = 1.0;
    beta = 0.5;
    gamma = 2.0;
    delta = 0.5;
    % Step 1 Reflection
    [thetaMax, tmaxindex] = max(results);
    if length(find(results==thetaMax)) > 1
        thetaMax2 = thetaMax;
        tmax2index = tmaxindex;
    else
        [thetaMax2, tmax2index] = max(results(results ~= thetaMax));
    end
    [thetaMin, tminindex] = min(results);
    thetaNoMax = [theta(1:tmaxindex-1,:); theta(tmaxindex+1:end,:)];
    thetaCent = sum(thetaNoMax)/length(thetaNoMax);
    thetaRefl = (1+alpha)*thetaCent - alpha*theta(tmaxindex);
    % Step 1a Accept Reflection
    if L(theta(tminindex,:)) <= L(thetaRefl) && L(thetaRefl) <= L(theta(tmax2index,:))
        disp('accepted')
    end
end

function r = L(theta)
    if mod(length(theta),2) ~= 0
        assert(false)
    end
    B = ones(length(theta), length(theta))/2 + eye(length(theta))/2;
    t4 = sum(reshape(theta.^4, length(theta)/2, 2));
    r = t4(1) + theta*B*theta';

end