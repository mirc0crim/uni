function [L] = getLightDir(method, chromeDir, nDir, chatty)
  % [L] = getLightDir(method)
  % Input:
  %  method = 0:  Use default directions
  %  method = 1:  Use chrome sphere images
  %  chromeDir (string) directory containing chrome images.
  %  nDir number of different light source images.
  %  chatty  (default false) show chrome images and estimated highlight center.
  % Return:
  %  L is a 3 x nDir image of light source directions.

  % Since we are looking down the z-axis, the direction
  % of the light source from the surface should have
  % a negative z-component, i.e., the light sources
  % are behind the camera.
    
  switch method
   case 0,
    % Default directions...
    % In case you have trouble getting the light source directions,
    % and you want to move on with the next part of the assignment, you
    % can use these.  They are NOT correct, but you can use them for now
    % until you come back and fix your estimation of the light source
    % directions later.
    L = [
        0.3441   -0.4300   -0.8347
        0.2130   -0.1223   -0.9694
        0.2708   -0.2654   -0.9253
        0.0563   -0.2280   -0.9720
       -0.2423   -0.4071   -0.8807
       -0.2731   -0.3663   -0.8895 
        0.3198   -0.3610   -0.8760
       -0.0094   -0.3012   -0.9535
        0.2074   -0.3342   -0.9194
        0.0891   -0.3298   -0.9398
        0.1281   -0.0443   -0.9908
       -0.1406   -0.3590   -0.9227]';
    nrm = sqrt(sum(L.^2,1));
    L = L .* repmat(1./nrm, 3, 1);
   case 1,
    L = fitChromeSphere(chromeDir, nDir, chatty);
   otherwise,
    L = 0;
    fprintf(2, 'Invalid fitting method.\n');
  end
  

