bestimme :-
	write('Hat das Tier einen Ruecken- und Brustschildpanzer? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> panzerja; panzernein).

panzerja :-
	write('Hat Panzer').

panzernein :-
	write('Hat keinen Panzer').