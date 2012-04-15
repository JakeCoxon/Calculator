package com.jakemadethis.prattparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.jakemadethis.calculator.ExpressionEvaluator;
import com.jakemadethis.calculator.Function;
import com.jakemadethis.calculator.Scope;
import com.jakemadethis.calculator.TokenType;
import com.jakemadethis.calculator.expressions.AssignExpression;
import com.jakemadethis.calculator.expressions.FunctionAssignExpression;
import com.jakemadethis.calculator.expressions.OperatorExpression;
import com.jakemadethis.calculator.expressions.ValueExpression;


public class ExpressionEvaluatorTest {
	
	private ExpressionEvaluator ev;
	private Scope scope;

	@Before
	public void before() {
		ev = null;//new ExpressionEvaluator();
		scope = new Scope();
	}
	
	@Test
	public void testValue() {
		ValueExpression val = new ValueExpression(32, null);
		double ret = ev.visit(val, scope);
		assertEquals(32, ret, 0.1);
	}
	
	@Test
	public void testOperator() {
		OperatorExpression op = new OperatorExpression(
				new ValueExpression(32), 
				TokenType.PLUS, 
				new ValueExpression(32));
		double ret = ev.visit(op, scope);
		assertEquals(64, ret, 0.1);
	}
	
	@Test
	public void testAssign() {
		AssignExpression assign = new AssignExpression("a", new ValueExpression(32), null);
		double ret = ev.visit(assign, scope);
		assertEquals(32, ret, 0.1);
		assertEquals(32, scope.getVariable("a"), 0.1);
	}
	
	@Test
	public void testAssignFunction() {
		FunctionAssignExpression assign = new FunctionAssignExpression(
				"a", new String[] {}, new ValueExpression(32), null);
		double ret = ev.visit(assign, scope);
		assertEquals(0, ret, 0.1);
		Function function = scope.getFunction("a");
		assertNotNull(function);
		assertEquals(32, function.invoke(new Double[]{}, ev), 0.1);
		
	}
}
