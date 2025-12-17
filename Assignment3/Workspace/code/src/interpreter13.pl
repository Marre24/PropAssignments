/*** 
Load the tokenizer (tokenize/2) and the file_writer (write_to_file/3).
***/
:- [tokenizer].
:- [filewriter].


/***
The top level predicate run/2 of the solution.
To be called like this:
?- run('program1.txt','myparsetree1.txt').
run('program2.txt','myparsetree2.txt').
***/
run(InputFile,OutputFile):-
	tokenize(InputFile,Program),
	parse(ParseTree,Program,[]),
	%evaluate(ParseTree,[],VariablesOut), 
	write_to_file(OutputFile,ParseTree,[]).
	
/***
parse(-ParseTree)-->
	A grammar defining your programming language,
	and returning a parse tree.
***/

/* WRITE YOUR CODE FOR THE PARSER HERE */

block(BlockTree) --> ['{'] , stmts(StmtTree) , ['}'], {BlockTree = block(left_curly, StmtTree, right_curly)}.
stmts(Value) --> {Value = statements}.
stmts(StatementTree) --> assign(Assignment), stmts(Rest), {StatementTree = statements(Assignment, Rest)}.

assign(Assignment) --> id(Var) , ['='] , expr(Expr) , [';'], {Assignment = assignment(Var, assign_op, Expr, semicolon)}.

expr(Value) --> term(TermValue), {Value = expression(TermValue)}.
expr(Value) --> term(TermValue) , ['+'] , expr(ExprValue), { Value = expression(TermValue, add_op, ExprValue)}.
expr(Value) --> term(TermValue) , ['-'] , expr(ExprValue), { Value = expression(TermValue, sub_op, ExprValue)}.

term(Value) --> factor(FactorValue), {Value = term(FactorValue)}.
term(Value) --> factor(FactorValue) , ['*'] , term(TermValue), {Value = term(FactorValue, mult_op, TermValue)}.
term(Value) --> factor(FactorValue) , ['/'] , term(TermValue), {Value = term(FactorValue, div_op, TermValue)}.

factor(Value) --> int(I), {Value = factor(I)}.
factor(Value) --> id(Var), {Value = factor(Var)}.
factor(Value) --> ['('] , expr(ExprValue) , [')'], {Value = factor(left_paren, ExprValue, right_paren)}.

int(Value) --> [N], {integer(N), Value = int(N)}.
id(Var) --> [N], {atom(N), Var = ident(N)}.

parse(ParseTree, Program, []) :-
    once(phrase(block(ParseTree), Program, [])).



	
/***
evaluate(+ParseTree,+VariablesIn,-VariablesOut):-
	Evaluates a parse-tree and returns the state of the program
	after evaluation as a list of variables and their values in 
	the form [var = value, ...].
***/

/* WRITE YOUR CODE FOR THE EVALUATOR HERE */

/* Default implementation for grade E. Replace it for a higher grade. */
evaluate(_ParseTree,[],[]).
