%  Some facts about categories




parent(sam,mark).
parent(mark,jim).

%  Rules
WorkFlow(A,C,N) :-
    N > 1,
    M is N-1
    WorkFlow(A,B,1),
    WorkFlow(B,C,M).







