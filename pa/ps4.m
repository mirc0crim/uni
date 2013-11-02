function opt = ps4()
    clear All;
    close All;
    clc;
    % Step 0 initialization
    theta = [1,2;3,2;0.25,0.5]
    counter = 1;
    while counter < 20
        counter = counter + 1;
        results = zeros(length(theta),1);
        for i = 1:length(theta)
            results(i) = L(theta(i,:));
        end
        [theta, thetaNoMax, thetaCent, thetaRefl, tmaxindex, tminindex, improved] = step1(theta, results);
        if norm(theta(tmaxindex,:) - theta(tminindex,:)) < 1
            break;
        end
        if improved == false
            [theta, improved] = step2(theta, thetaNoMax, thetaRefl, thetaCent, tminindex);
        end
        if improved == false
            [theta, improved] = step3(theta, thetaNoMax, thetaRefl, thetaCent, tmaxindex);
        end
        if improved == false
            theta = step4(theta, tminindex);
        end
    end
	theta
end

function [theta, thetaNoMax, thetaCent, thetaRefl, tmaxindex, tminindex, step5] = step1(theta, results)
    % Step 1 Reflection
    alpha = 1.0;
	step5 = false;
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
        step5 = true;
    end
end

function [theta, step5] = step2(theta, thetaNoMax, thetaRefl, thetaCent, tminindex)
    % Step 2 Expansion
    gamma = 2.0;
    step5 = false;
    if L(thetaRefl) < L(theta(tminindex,:))
        thetaExp = gamma*thetaRefl + (1-gamma)*thetaCent;
        % Step 2a Check expansion
        if L(thetaExp) < L(thetaRefl)
            theta = [thetaNoMax; thetaExp];
            step5 = true;
        else
            theta = [thetaNoMax; thetaRefl];
        end
    end
end

function [theta, step5] = step3(theta, thetaNoMax, thetaRefl, thetaCent, tmaxindex)
    % Step 3 Contraction
    beta = 0.5;
    step5 = false;
    if L(thetaRefl) < L(theta(tmaxindex,:))
        % outside
        thetaCont = beta*thetaRefl + (1-beta)*thetaCent;
        if L(thetaCont) < L(thetaRefl)
            theta = [thetaNoMax; thetaCont];
            step5 = true;
        end
    end
    if L(thetaRefl) >= L(theta(tmaxindex,:))
        % inside
        thetaCont = beta*theta(tmaxindex,:) + (1-beta)*thetaCent;
        if L(thetaCont) < L(theta(tmaxindex,:))
            theta = [thetaNoMax; thetaCont];
            step5 = true;
        end
    end
end

function theta = step4(theta, tminindex)
    % Step 4 Shrink
    delta = 0.5;
    for i = 1:length(theta)
        if i ~= tminindex
            theta(i,:) = delta*theta(i,:) + (1-delta)*theta(tminindex,:);
        end
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