package com.jakemadethis.calculator.expressions;

public interface ExpressionVisitor<R, C> {
	R visit(AssignExpression expr, C context);
	R visit(FunctionAssignExpression expr, C context);
	R visit(CallExpression expr, C context);
	R visit(NameExpression expr, C context);
	R visit(OperatorExpression expr, C context);
	R visit(UnaryExpression expr, C context);
	R visit(ValueExpression expr, C context);
}
