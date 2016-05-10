%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%  Facts
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% splitter(a1).
% splitter(b1).
%
% tokenizer(a2).
% tokenizer(b2).
%
% tagger(a3).
% tagger(b3).
%
% chunking(a4).
% chunking(b4).
%
% ner(a5).
% ner(b5).
%
% parser(a6).
% parser(b6).
%
% dependencyparser(a7).
% dependencyparser(b7).
%
%
% coreference(a8).
% coreference(b8).
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%  Rules
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

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





