mu = 73;
sd = 6.1;
ix = mu-3*sd:1e-3:mu+3*sd;
iy = pdf('normal', ix, mu, sd);
plot(ix,iy, 'r');

hold on;

mu = 70;
sd = 1.6;
ix = mu-5*sd:1e-3:mu+5*sd;
iy = pdf('normal', ix, mu, sd);
plot(ix,iy, 'b');

plot([66;66],[0;0.1], 'g');