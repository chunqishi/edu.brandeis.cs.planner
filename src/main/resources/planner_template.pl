%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	
%%  Facts
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

splitter(a1).
splitter(b1).

tokenizer(a2).
tokenizer(b2).

tagger(a3).
tagger(b3).

chunking(a4).
chunking(b4).

ner(a5).
ner(b5).

parser(a6).
parser(b6).

dependencyparser(a7).
dependencyparser(b7).


coreference(a8).
coreference(b8).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	
%%  Rules
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

workflow(A,B,2,[A,B]):-
    splitter(A),
    tokenizer(B).

workflow(A,B,2,[A,B]):-
    tokenizer(A),
    tagger(B).

workflow(A,B,2,[A,B]):-
    tagger(A),
    parser(B).

workflow(A,B,2,[A,B]):-
    tagger(A),
    dependencyparser(B).

workflow(A,B,2,[A,B]):-
    tagger(A),
    coreference(B).

workflow(A,B,2,[A,B]):-
    tagger(A),
    ner(B).

workflow(A,C,N,[A,B|L]) :-
    #>(N,2),
    #=(M,N-1),
    workflow(A,B,2,[A,B]),
    workflow(B,C,M,[B|L]).
	
	


