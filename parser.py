# -*- coding: utf-8 -*-
"""
Created on Tue Sep 17 22:49:39 2019

@author: Distanta
"""
from parse_table import compute_PARSE_TABLE
from dfa_utils import dfa_scan
from dfa_utils import re_to_dfa

def load_grammar( filename ):
    file= open(filename,"r")
    rules= {}
    nTerminal = set()
    terminal = set()
    isFirst= True
    for line in file:
        line = line.strip()
        if(len(line)>0):
            line = line.replace("\n", "")
            fEqIndex = line.find("=")
            left = line[0 : fEqIndex].strip()
            right = line[fEqIndex+1: len(line)].strip().split(" ")
            tup= ()
            nTerminal.add(left)
            if(isFirst):
                S= left
                isFirst= False;
            for ch in right:
                if(ch != ""):
                    tup= tup + (ch,)
                    if ch.isupper():
                        nTerminal.add(ch)
                    else:
                        terminal.add(ch)

            if(left not in rules):
                rules[left]= {tup}
            else:
                s= rules[left]
                s.add(tup)
                rules[left]= s
    file.close()
    return S, nTerminal, terminal, rules


def parse(program, S, NT, T, R):
    useRule = compute_PARSE_TABLE(S, NT, T, R)
    reg= "|".join(T)
    Q, E, delta, q0, F = re_to_dfa(reg)
    get_token = dfa_scan(program, Q, E, delta, q0, F )
    stack= list()
    stack.append(S)
    index=0
    lookahead = next(get_token)
    while(len(stack)!=0 and lookahead!=()):
        X = stack.pop()
        (token, start, end) = lookahead

        if X in T:
            if X== token:
                index= end+1
                lookahead= next(get_token)
            else:
                print("parse failed: unexpected token" + program[index:end+1] +" " + str(index) + " " + str(end))
                return
        elif (X, token) not in useRule:
            print("parse failed: cannot expand")
            return
        else:
            expansion= useRule[X, token]
            for element in reversed(expansion):
                stack.append(element)

    if(len(stack)==0 and lookahead==()):
        print("parse successful")
    else:
        print("parse failed: expected end token")
