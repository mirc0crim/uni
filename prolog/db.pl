bestimme :-
	write('Hat das Tier einen Ruecken- und Brustschildpanzer? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> flossen; glieder).

flossen :-
	write('Hat das Tier Flossenaehnliche Glieder? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> karett; riesen).

karett :-
	write('Gattungsname des Tieres: Karettschildkroete'),
	nl.

riesen :-
	write('Gattungsname des Tieres: Riesenschildkroete'),
	nl.

glieder :-
	write('Hat das Tier erkennbare Glieder? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> schwanz; augen).

augen :-
	write('Hat das Tier sichtbare Augenlieder? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> blind; schwimmen).

blind :-
	write('Gattungsname des Tieres: Blindschleiche'),
	nl.

schwimmen :-
	write('Kann das Tier schwimmen? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> ringel; asp).

ringel :-
	write('Gattungsname des Tieres: Ringelnatter'),
	nl.

asp :-
	write('Gattungsname des Tieres: Aspisviper'),
	nl.

schwanz :-
	write('Hat das Tier einen zylindrischen Schwanz? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> krallen; schnauze).

krallen :-
	write('Hat das Tier Krallen an den Fuessen? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> echse; polster).

echse :-
	write('Gattungsname des Tieres: Eidechse'),
	nl.

polster :-
	write('Hat das Tier Haftpolster an den Fuessen? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> gecko; cham).

gecko :-
	write('Gattungsname des Tieres: Gecko'),
	nl.

cham :-
	write('Gattungsname des Tieres: Chamaeleon'),
	nl.

schnauze :-
	write('Hat das Tier eine schmale Schnauze? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> gavial; zahn).

gavial :-
	write('Gattungsname des Tieres: Gavial'),
	nl.

zahn :-
	write('Ist der vierte Unterkieverzahn sichtbar? (ja/nein)'),
	read(X),
	nl,
	(X = ja -> krokodil; aligator).

krokodil :-
	write('Gattungsname des Tieres: Krokodil'),
	nl.

aligator :-
	write('Gattungsname des Tieres: Aligator'),
	nl.