package com.jakemadethis.prattparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jakemadethis.calculator.ExpressionEvaluator;
import com.jakemadethis.calculator.ExpressionFunction;
import com.jakemadethis.calculator.Function;
import com.jakemadethis.calculator.Scope;
import com.jakemadethis.calculator.TokenType;
import com.jakemadethis.calculator.expressions.NameExpression;
import com.jakemadethis.calculator.expressions.OperatorExpression;
import com.jakemadethis.calculator.expressions.ValueExpression;

public class ScopeTest {

	/**
	 * getParent should get the scopes parent
	 */
	@Test public void testScopeParent() {
		Scope topScope = new Scope();
		Scope scope = topScope.push();
		assertEquals(topScope, scope.getParent());
	}
	
	/**
	 * pushBefore should push a scope before this one
	 */
	@Test public void testPushBefore() {
		Scope topScope = new Scope();
		Scope bottomScope = topScope.push();
		Scope middleScope = bottomScope.pushBefore();
		assertEquals(middleScope, bottomScope.getParent());
		assertEquals(topScope, middleScope.getParent());
	}
	
	/**
	 * Defining a variable should work
	 * Defining a variable twice should do nothing
	 */
	@Test public void testDefine() {
		Scope scope = new Scope();
		scope.define("var", 100);
		
		assertTrue(100 == scope.getVariable("var"));
		
		scope.define("var", 200);
		
		// var should not change
		assertTrue(100 == scope.getVariable("var"));
	}
	
	/**
	 * Assigning a variable should change it
	 */
	@Test public void testAssign() {
		Scope scope = new Scope();
		scope.define("var", 100);
		
		assertTrue(100 == scope.getVariable("var"));
		
		scope.assign("var", 200);
		
		// var should not change
		assertTrue(200 == scope.getVariable("var"));
	}
	
	/**
	 * Assigning a variable that has been defined in a parent scope should
	 * change that variable
	 */
	@Test public void testParentAssign() {
		Scope topScope = new Scope();
		topScope.define("var", 100);
		
		Scope subScope = topScope.push();
		subScope.assign("var", 200);

		assertFalse(subScope.hasLocal("var"));
		assertTrue(200 == topScope.getVariable("var"));
		assertTrue(200 == subScope.getVariable("var"));
	}
	
	/**
	 * Defining a variable that has been defined in a parent scope should
	 * create a duplicate
	 */
	@Test public void testDefineDefine() {

		Scope topScope = new Scope();
		topScope.define("var", 100);
		
		Scope subScope = topScope.push();
		subScope.define("var", 200);

		assertTrue(100 == topScope.getVariable("var"));
		assertTrue(200 == subScope.getVariable("var"));
	}
	
	/**
	 * 
	 */
	@Test public void testFunctionNoArgs() {
		Scope topScope = new Scope();
		Function f = new ExpressionFunction(new ValueExpression(32), new String[] {}, topScope.push());
		Double res = f.invoke(new Double[] {}, new ExpressionEvaluator());
		assertEquals(32, res, 0.1);
	}
	
	/**
	 * Functions should use arguments passed as its scope
	 */
	@Test public void testFunctionArgs() {
		Scope topScope = new Scope();
		// a(b) = 32+b
		Function f = new ExpressionFunction(
				new OperatorExpression(new ValueExpression(32), TokenType.PLUS, new NameExpression("b", null)), 
				new String[] {"b"}, topScope.push());
		
		Double res = f.invoke(new Double[] {32d}, new ExpressionEvaluator());
		assertEquals(64, res, 0.1);
	}
	
	/**
	 * Function arguments should shadow variables defined in an outer scope
	 */
	@Test public void testFunctionShadow() {
		Scope topScope = new Scope();
		topScope.define("b", 2d);
		
		// a(b) = 32+b
		Function f = new ExpressionFunction(
				new OperatorExpression(new ValueExpression(32), TokenType.PLUS, new NameExpression("b", null)), 
				new String[] {"b"}, topScope.push());
		
		Double res = f.invoke(new Double[] {32d}, new ExpressionEvaluator());
		assertEquals(64, res, 0.1);	// Result of function should be correct
		assertEquals(2d, topScope.getVariable("b"), 0.1); // global b should remain unchanged
	}
	
	/**
	 * Functions should be able to use variables defined in an outer scope
	 */
	@Test public void testFunctionScope() {
		Scope topScope = new Scope();
		// c = 4
		topScope.define("c", 4d);
		
		// a(b) = b+c
		Function f = new ExpressionFunction(
				new OperatorExpression(new NameExpression("c", null), 
						TokenType.PLUS, new NameExpression("b", null)), 
				new String[] {"b"}, topScope.push());
		
		Double res = f.invoke(new Double[] {32d}, new ExpressionEvaluator());
		assertEquals(36, res, 0.1);	// Result of function should be correct
	}

}
