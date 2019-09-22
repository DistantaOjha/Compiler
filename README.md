# Compiler
Compiler that compiles and executes Python like progamming language and targets JVMs. Designed to work through Bash Shell.
Work in process...

**MiniIDE:** It is the main driver. It is made using JavaFX. You can enter a regular expression (first line) and a bunch of strings (one line for each strings) on the left panel of the application. We currently have three buttons RegEx , RegEx+DFA and LL(1) Stack Parse. 

RegEX checks if the list of strings on the left panel is acceptable or not according to the given regular expression. It does this by     constructing a NFA and going through each string.

RegEx+DFA does the same thing as RegEX does but it converts RE to NFA and NFA to DFA before checking the string.

LL(1) Stack Parse: For now, we have a LOGO programming language like grammar in logo_grammar.txt. The Python program loads the file and constructs parse table. And then, we implement Top-Down parsing using stack and determine whether the strings are parsable when given that grammar.

**PLEASE NOTE THIS IS A WORK IN PROGRESS**  

