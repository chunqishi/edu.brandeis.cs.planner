%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%  Facts
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

text(text).



%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%  Rules
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


pipeline(A,B):-
    text(A),
    splitter(B).

pipeline(A,B):-
    splitter(A),
    tokenizer(B).

pipeline(A,B):-
    tokenizer(A),
    tagger(B).

pipeline(A,B):-
    tagger(A),
    parser(B).

pipeline(A,B):-
    tagger(A),
    dependencyparser(B).

pipeline(A,B):-
    tagger(A),
    coreference(B).

pipeline(A,B):-
    tagger(A),
    ner(B).

workflow(A,B,[A,B]):-
    pipeline(A,B).

workflow(A,C,[A,B|L]):-
    pipeline(A,B),
    workflow(B,C,[B|L]).





