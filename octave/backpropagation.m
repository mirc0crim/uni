
function back = backpropagation()
	numOfInputs = 2;
	numOfNeurons = 2;
	maxError = 0.00001;

	myInput = [0 0; 1 0; 0 1; 1 1];
	myOutput = [0; 1; 1; 0];
	
	w1 = rand(3,2);
	w2 = rand(2,1);

	do
		error = 0;
		for i=1:size(myInput,1)
			printf(" %f \n", sigmoid(i));
		endfor
	until (error< maxError)

	printf("w1 = %f \n", w1);
	printf("w2 = %f \n", w2);

endfunction

function sig = sigmoid(x)
	c = 1;
	sig = 1 / ( 1 + exp(-c*x));
endfunction