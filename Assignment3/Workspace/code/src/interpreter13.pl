:- [tokenizer].
:- [filewriter].

run(InputFile,OutputFile):-
	tokenize(InputFile,Program),
	parse(ParseTree,Program,[]),
	evaluate(ParseTree,[],VariablesOut), 
	write_to_file(OutputFile,ParseTree, VariablesOut).

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


evaluate(block(left_curly, Stmts, right_curly), VariablesIn, VariablesOut) :-
	evaluate_stmts(Stmts, VariablesIn, VariablesOut).

evaluate_stmts(statements, VariablesIn, VariablesOut) :-
	VariablesOut = VariablesIn.
evaluate_stmts(statements(Assignment, Stmts), VariablesIn, VariablesOut) :-
	evaluate_assign(Assignment, VariablesIn, VariablesTmp),
	evaluate_stmts(Stmts, VariablesTmp, VariablesOut).

evaluate_assign(assignment(ident(V), assign_op, Expr, semicolon), VariablesIn, VariablesOut) :-
	evaluate_expr(Expr, Value, VariablesIn),
	VariablesOut = [V = Value | VariablesIn].

evaluate_expr(expression(Term), Value, VariablesIn) :-
	evaluate_term(Term, Value, VariablesIn).
evaluate_expr(expression(Term, add_op, Expr), Value, VariablesIn) :-
	evaluate_term(Term, T, VariablesIn),
	evaluate_expr(Expr, V, VariablesIn),
	Value is T + V.
evaluate_expr(expression(Term, sub_op, Expr), Value, VariablesIn) :-
	evaluate_term(Term, T, VariablesIn),
	evaluate_expr(Expr, V, VariablesIn),
	Value is T - V.


evaluate_term(term(Factor), Value, VariablesIn) :-
	evaluate_factor(Factor, Value, VariablesIn).
evaluate_term(term(Factor, mult_op, Term), Value, VariablesIn) :-
	evaluate_factor(Factor, FVal, VariablesIn),
	evaluate_term(Term, TVal, VariablesIn),
	Value is FVal * TVal.
evaluate_term(term(Factor, div_op, Term), Value, VariablesIn) :-
	evaluate_factor(Factor, Numer, VariablesIn),
	evaluate_term(Term, Denom, VariablesIn),
	Value is Numer / Denom.

evaluate_factor(factor(int(I)), I, _).
evaluate_factor(factor(ident(I)), V, VariablesIn) :-
	get_var_value(I, VariablesIn, V).
evaluate_factor(factor(left_paren, Expr, right_paren), Value, VariablesIn) :-
	evaluate_expr(Expr, Value, VariablesIn).

get_var_value(VarName, [VarName = Value | _], Value).
get_var_value(VarName, [_ | Rest], Result) :-
	get_var_value(VarName, Rest, Result).
get_var_value(_, [], 0).


/*
run('program2.txt','myparsetree2.txt').
*/
