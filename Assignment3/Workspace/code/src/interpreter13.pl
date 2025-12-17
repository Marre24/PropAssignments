%Author: Maximilian Ellnestam mael0424

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

evaluate_expr(Expr, Value, Vars) :-
    flatten_expr(Expr, Terms, Ops),
    map_eval_terms(Vars, Terms, TermValues),
    evaluate_values_left(TermValues, Ops, Value).

flatten_expr(expression(Term), [Term], []).
flatten_expr(expression(Term, add_op, Expr), [Term|Terms], [add_op|Ops]) :-
    flatten_expr(Expr, Terms, Ops).
flatten_expr(expression(Term, sub_op, Expr), [Term|Terms], [sub_op|Ops]) :-
    flatten_expr(Expr, Terms, Ops).

map_eval_terms(_, [], []).
map_eval_terms(Vars, [Term|Terms], [Val|Vals]) :-
    evaluate_term(Term, Val, Vars),
    map_eval_terms(Vars, Terms, Vals).

evaluate_term(Term, Value, Vars) :-
    flatten_term(Term, Factors, Ops),
    map_eval_factors(Vars, Factors, FactorValues),
    evaluate_values_left(FactorValues, Ops, Value).

flatten_term(term(Factor), [Factor], []).
flatten_term(term(Factor, mult_op, Term), [Factor|Factors], [mult_op|Ops]) :-
    flatten_term(Term, Factors, Ops).
flatten_term(term(Factor, div_op, Term), [Factor|Factors], [div_op|Ops]) :-
    flatten_term(Term, Factors, Ops).

map_eval_factors(_, [], []).
map_eval_factors(Vars, [Factor|Factors], [Val|Vals]) :-
    evaluate_factor(Factor, Val, Vars),
    map_eval_factors(Vars, Factors, Vals).

evaluate_values_left([V], [], V).
evaluate_values_left([A,B|Rest], [Op|Ops], Result) :-
    apply_arith_op(Op, A, B, R),
    evaluate_values_left([R|Rest], Ops, Result).

apply_arith_op(add_op, A, B, R) :- R is A + B.
apply_arith_op(sub_op, A, B, R) :- R is A - B.
apply_arith_op(mult_op, A, B, R) :- R is A * B.
apply_arith_op(div_op,  A, B, R) :- R is A / B.



evaluate_factor(factor(int(I)), I, _).
evaluate_factor(factor(ident(I)), V, VariablesIn) :-
	get_var_value(I, VariablesIn, V).
evaluate_factor(factor(left_paren, Expr, right_paren), Value, VariablesIn) :-
	evaluate_expr(Expr, Value, VariablesIn).

get_var_value(VarName, [VarName = Value | _], Value).
get_var_value(VarName, [_ | Rest], Result) :-
	get_var_value(VarName, Rest, Result).
get_var_value(_, [], 0).
