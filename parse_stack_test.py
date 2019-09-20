# -*- coding: utf-8 -*-
"""
Created on Wed Sep 18 23:33:28 2019

@author: Distanta
"""
import sys
from parser import parse
from parser import load_grammar
program = sys.stdin.read().strip()
#print(program)
filename = "/Accounts/turing/students/s22/ojhadi01/cs341/proj3/logo_grammar.txt"
S, N, T, R = load_grammar(filename)
parse(program, S, N, T, R )
