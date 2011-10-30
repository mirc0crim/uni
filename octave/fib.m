
% fib.m
% Compute fibonacci sequence 1, 1, 2, 3, 5, 8, 13, 21, ...
% fib(n+2) = fib(n) + fib(n+1)
function fn = fib( n )
	if n >= 2
		fn = fib(n-2) + fib(n-1);
	elseif n >= 0
		fn = 1;
	else
		fn = 0; % Error
	endif
endfunction