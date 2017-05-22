% COMP3071 - LA4

/********************************************
** define your three predicates below here **
********************************************/
sibling(jeff, tina).
married(jeff, mark).
male(mark).

brotherInLaw(Person1,Person2) :-
	(married(SO,Person1),sibling(SO, Person2));
	(male(Person2),married(SO2,Person2),sibling(SO2,Person1)).
 
uniques(List,Result) :- duplicates(List,Dups), delete_list(Dups,List,Result).

duplicates([Check | Rest], Result) :- member(Check,Rest) -> (duplicates(Rest,Result1), append([Check],Result1,Result)) ; (duplicates(Rest,Result1), append([],Result1,Result)).
duplicates([],Result) :- Result = [].

delete_list([Element | Rest], List, Result) :- delete(List, Element, Result1), delete_list(Rest, Result1, Result).
delete_list([],List,Result) :- Result=List.

holes(List, Result) :- sort(List,SortedList), holes_(SortedList,Result).
holes_([ _ | []], Result) :- Result=[].
holes_([], Result) :- Result=[].
holes_(List,Result) :- 
	[First, Second | Tail] = List,
	Delta is Second-First,
	((Delta is 1) ->
		(
			holes_([Second | Tail], Result),!
		)
		;
		(
			NextNumber is First + 1,
			holes_([NextNumber , Second | Tail], Result1),
			append([NextNumber],Result1,Result),!
		)	
).

