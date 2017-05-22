/* 
    COMP3071 LA3 - Prolog introduction
*/

/* insert your clauses here */
game(overwatch).

character(mei).
character(widow).
character(road).
character(mercy).

like(mei).
like(mercy).
like(road).

hates(widow).

stupid(mei).
stupid(road).

wantsToPlay(X) :- like(X).
willNotPlay(X) :- hates(X).
janky(X) :- like(X),stupid(X).
shouldNotPlay(X) :- stupid(X);hates(X).


/* put your example queries in this comment under your clauses 

janky(mei).
true
shouldNotPlay(mei).
true
shouldNotPlay(road).
true
willNotPlay(widow).
true
wantsToPlay(mei).
true
janky(X).
X = mei
X = road
shouldNotPlay(X).
X = mei ;
X = road ;
X = widow.
*/