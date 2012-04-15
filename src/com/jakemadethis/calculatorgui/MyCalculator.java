package com.jakemadethis.calculatorgui;

import com.jakemadethis.calculator.ExpressionEvaluator;
import com.jakemadethis.calculator.Function;
import com.jakemadethis.calculator.LinedCalculator;
import com.jakemadethis.calculator.Scope;

/**
 * Defines built-in constants and functions
 * @author Jake
 *
 */
public class MyCalculator extends LinedCalculator {

	private static Scope scope;
	public static Scope topScope() {
		if (scope != null) return scope;
		
		scope = new Scope();
		scope.define("pi", Math.PI);
		scope.define("e", Math.E);
		scope.define("sin", new Function(1) {
			@Override public Double invoke(Double[] args, ExpressionEvaluator evaluator) {
				return Math.sin(args[0]);
			}
		});
		scope.define("cos", new Function(1) {
			@Override public Double invoke(Double[] args, ExpressionEvaluator evaluator) {
				return Math.cos(args[0]);
			}
		});
		scope.define("tan", new Function(1) {
			@Override public Double invoke(Double[] args, ExpressionEvaluator evaluator) {
				return Math.tan(args[0]);
			}
		});
		scope.define("asin", new Function(1) {
			@Override public Double invoke(Double[] args, ExpressionEvaluator evaluator) {
				return Math.asin(args[0]);
			}
		});
		scope.define("acos", new Function(1) {
			@Override public Double invoke(Double[] args, ExpressionEvaluator evaluator) {
				return Math.acos(args[0]);
			}
		});
		scope.define("atan", new Function(1) {
			@Override public Double invoke(Double[] args, ExpressionEvaluator evaluator) {
				return Math.atan(args[0]);
			}
		});
		scope.define("abs", new Function(1) {
			@Override public Double invoke(Double[] args, ExpressionEvaluator evaluator) {
				return Math.abs(args[0]);
			}
		});
		return scope;
	}
	public MyCalculator() {
		super(new ExpressionEvaluator(MyParserDefinitions.PARSER_DEFINITIONS), 
				MyParserDefinitions.PARSER_DEFINITIONS, topScope());
		
	}
}
