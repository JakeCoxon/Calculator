package com.jakemadethis.calculatorgui;

import com.jakemadethis.calculator.CharPosition;
import com.jakemadethis.calculator.Token;
import com.jakemadethis.calculator.expressions.AssignExpression;
import com.jakemadethis.calculator.expressions.CallExpression;
import com.jakemadethis.calculator.expressions.Expression;
import com.jakemadethis.calculator.expressions.ExpressionVisitor;
import com.jakemadethis.calculator.expressions.FunctionAssignExpression;
import com.jakemadethis.calculator.expressions.NameExpression;
import com.jakemadethis.calculator.expressions.OperatorExpression;
import com.jakemadethis.calculator.expressions.UnaryExpression;
import com.jakemadethis.calculator.expressions.ValueExpression;
import com.jakemadethis.guiconsole.LinedBuffer;

public class SyntaxHighlighter implements ExpressionVisitor<Void, Integer> {

	private static final int COLOR_NAME = 0xffffff;
	private static final int COLOR_OPERATOR = 0xaaaaaa;
	private static final int COLOR_VALUE = 0xaaaaaa;
	private static final int COLOR_HASHOPERATOR = 0xffaa00;
	private final LinedBuffer buffer;

	public SyntaxHighlighter(LinedBuffer buffer) {
		this.buffer = buffer;
	}


	@Override
	public Void visit(AssignExpression expr, Integer line) {
		expr.getBody().accept(this, line);
		return null;
	}
	
	@Override
	public Void visit(FunctionAssignExpression expr, Integer line) {
		expr.getBody().accept(this, line);
		return null;
	}

	@Override
	public Void visit(CallExpression expr, Integer line) {
		Expression[] exprArgs = expr.getArgs();
		
		for (int i = 0; i < exprArgs.length; i ++) {
			exprArgs[i].accept(this, line);
		}
		
		return null;
	}

	@Override
	public Void visit(NameExpression expr, Integer line) {
		CharPosition p = expr.getPosition();
		buffer.setColor(COLOR_NAME, p.getStart(), line, p.getLength());
		return null;
	}

	@Override
	public Void visit(OperatorExpression expr, Integer line) {
		expr.getLeft().accept(this, line);
		expr.getRight().accept(this, line);
		
		Token token = expr.getOperatorToken();
		int start = token.getStart();
		if (start > -1) {
			buffer.setColor(COLOR_OPERATOR, start, line, token.getEnd() - start);
		}
		
		return null;
	}

	@Override
	public Void visit(UnaryExpression expr, Integer line) {
		expr.getBody().accept(this, line);
		
		if (expr.getOperatorType() == MyParserDefinitions.HASH) {
			CharPosition p = expr.getPosition();
			buffer.setColor(COLOR_HASHOPERATOR, p.getStart(), line, p.getLength());
		}
		
		
		return null;
	}

	@Override
	public Void visit(ValueExpression expr, Integer line) {
		CharPosition p = expr.getPosition();
		buffer.setColor(COLOR_VALUE, p.getStart(), line, p.getLength());
		return null;
	}

}
