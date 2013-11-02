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
        tmax2index = tmaxindex;
    else
        [~, tmax2index] = max(results(results ~= thetaMax));
    end
    [~, tminindex] = min(results);
    thetaNoMax = [theta(1:tmaxindex-1,:); theta(tmaxindex+1:end,:)];
    thetaCent = sum(thetaNoMax)/length(thetaNoMax);
    thetaRefl = (1+alpha)*thetaCent - alpha*theta(tmaxindex,:);
    % Step 1a Accept Reflection
    if L(theta(tminindex,:)) <= L(thetaRefl) && L(thetaRefl) <= L(theta(tmax2index,:))
        theta = [thetaNoMax; thetaRefl];
        %goto step 5
    end
    % Step 2 Expansion
    if L(thetaRefl) < L(theta(tminindex,:))
        thetaExp = gamma*thetaRefl + (1-gamma)*thetaCent;
        % Step 2a Check expansion
        if L(thetaExp) < L(thetaRefl)
            theta = [thetaNoMax; thetaExp];
            % goto step 5
        else
            theta = [thetaNoMax; thetaRefl];
        end
    end
    % Step 3 Contraction
    if L(thetaRefl) < L(theta(tmaxindex,:))
        % outside
        thetaCont = beta*thetaRefl + (1-beta)*thetaCent;
        if L(thetaCont) < L(thetaRefl)
            theta = [thetaNoMax; thetaCont];
            % goto step 5
        end
    end
    if L(thetaRefl) >= L(theta(tmaxindex,:))
        % inside
        thetaCont = beta*theta(tmaxindex,:) + (1-beta)*thetaCent;
        if L(thetaCont) < L(theta(tmaxindex,:))
            theta = [thetaNoMax; thetaCont];
            % goto step 5
        end
    end
    % Step 4 Shrink
    for i = 1:length(theta)
        if i ~= tminindex
            theta(i,:) = delta*theta(i,:) + (1-delta)*theta(tminindex,:);
        end
    end
    % Step 5
    % stop if conv criterion met or max func call met
    % else go to step 1
end

function r = L(theta)
    if mod(length(theta),2) ~= 0
        assert(false)
    end
    B = ones(length(theta), length(theta))/2 + eye(length(theta))/2;
    t4 = sum(reshape(theta.^4, length(theta)/2, 2));
    r = t4(1) + theta*B*theta';

end