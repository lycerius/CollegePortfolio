% COMP3071 - PA2

when_(1000,10).
when_(1200,12).
when_(3400,11).
when_(3350,12).
when_(2350,11).
where(1000,dobbs102).
where(1200,dobbs118).
where(3400,wentw216).
where(3350,wentw118).
where(2350,wentw216).
enroll(mary,1200).
enroll(john,3400).
enroll(mary,3350).
enroll(john,1000).
enroll(jim,1000).


/************************************
*****                           *****
***** In your final submission, *****
***** leave the text above this *****
*****    comment untouched.     *****
*****                           *****
************************************/
 


schedule(Student, Classroom, Time) :-
enroll(Student, Course), where(Course, Classroom), when_(Course, Time).

usage(Classroom, Time) :-
where(Course, Classroom), when_(Course, Time).

conflict(Course1, Course2) :-
(when_(Course1, Time),when_(Course2, Time)),(where(Course1,Classroom),where(Course2, Classroom)).

meet(Student1, Student2) :-
(enroll(Student1, Student1Course), enroll(Student2, Student1Course));
(enroll(Student2, Student2Course), where(Student1Course, Classroom), where(Student2Course, Classroom),
when_(Student1Course,Time1), when_(Student2Course,Time2),
TimeDelta is Time2 - Time1, (TimeDelta is -1 ; TimeDelta is 1)).
 