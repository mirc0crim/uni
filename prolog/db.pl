population(usa,203).
population(india,548).
population(china,800).
population(brazil,108).
area(usa,3).
area(india,3).
area(china,4).
area(brazil,3).
density(Country,Dens) :-
	population(Country,Pop),
	area(Country,A),
	Dens is Pop/A.

bestimme :-
	write('Hat das Tier einen Ruecken- und Brustschildpanzer? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> panzerja; panzernein).

panzerja :-
	write('Hat Panzer').

panzernein :-
	write('Hat keinen Panzer').