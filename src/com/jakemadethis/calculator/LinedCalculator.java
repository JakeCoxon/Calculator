package com.jakemadethis.calculator;

import java.util.Iterator;
import java.util.LinkedList;

import com.jakemadethis.calculator.expressions.Expression;

/**
 * A calculator class that has rows identified by row numbers
 * Allows updating a single line and re-evaluating multiple lines at ones
 * @author Jake
 *
 */
public class LinedCalculator {
	private class Row {
		Expression input = null;
		String output = "";
		Scope scope;
		Double value = null;
		public Row(Scope scope) {
			this.scope = scope;
		}
	}
	final LinkedList<Row> rows;
	
	private final ExpressionEvaluator evaluator;
	private final Scope topScope;

	private final ParserDefinitions definitions;

	public LinedCalculator(ExpressionEvaluator evaluator, ParserDefinitions definitions, Scope topScope) {
		this.definitions = definitions;
		if (topScope == null) this.topScope = new Scope();
		else this.topScope = topScope;
		
		rows = new LinkedList<Row>();
		
		this.evaluator = evaluator;
		rows.add(new Row(topScope.push()));
	}
	
	/**
	 * Inserts an empty row at the position
	 * @param newId
	 */
	public void addRow(int newId) {
		if (newId == rows.size()) { // Last scope
			rows.add(newId, new Row(rows.getLast().scope.push()));
		}
		else {
			rows.add(newId, new Row(rows.get(newId).scope.pushBefore()));
		}
	}
	
	public void updateLine(int id, String text) {
		Row row = rows.get(id);
		row.scope.clearLocals();
		if (text == null || text.length() == 0) {
			row.input = null;
			row.output = "";
		}
		else {
			Parser parser = new Parser(definitions, new Lexer(text, definitions));
			row.output = "";
			row.value = null;
			try {
				Expression expr = parser.parseExpression();
				row.input = expr;
			}
			catch (TokenException ex) {
				row.output = ex.getMessage();
				row.input = null;
			}
			catch (ExpressionException ex) {
				row.output = ex.getMessage();
				row.input = null;
			}
		}
	}
	
	public void recalculateFrom(int id) {
		Iterator<Row> iterator = rows.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			Row row = iterator.next();
			if (i >= id) {
				row.scope.clearLocals();
				if (row.input != null) {
					row.value = 0.0;
					try {
						row.value = calculateOutput(row.input, row.scope);
						row.output = row.value == null ? "" : row.value.toString();
					}
					catch (NullPointerException ex) {
						row.output = "";
					}
					catch (ExpressionException ex) {
						row.output = ex.getMessage();
					}
					catch (TokenException ex) {
						row.output = ex.getMessage();
					}
				}
			}
			i++;
		}
	}

	private Double calculateOutput(Expression input, Scope scope) {
		Double value = input.accept(evaluator, scope);
		return value;
	}

	public void removeLine(int lineNumber) {
		if (rows.size() == 1) throw new RuntimeException("Cannot remove final row");
		if (lineNumber < rows.size()-1) {
			rows.get(lineNumber+1).scope.removeParent();
		}
		rows.remove(lineNumber);
	}
	
	public Expression getInput(int row) {
		return rows.get(row).input;
	}
	public String getOutput(int row) {
		return rows.get(row).output;
	}
	public Double getValue(int row) {
		return rows.get(row).value;
	}
	
	public int getRowCount() {
		return rows.size();
	}



	
}
