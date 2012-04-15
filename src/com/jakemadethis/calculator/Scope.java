package com.jakemadethis.calculator;

import java.util.HashMap;
import java.util.Map;

public class Scope {

	private final Map<String, Obj> variables = new HashMap<String, Obj>();
	//private final Map<String, Function> functions = new HashMap<String, Function>();
	private Scope parent;
	
	public Scope() {
		this.parent = null;
	}
	private Scope(Scope parent) {
		this.parent = parent;
	}
	
	/**
	 * Pushes a new scope onto the stack
	 * @return the new scope
	 */
	public Scope push() {
		return new Scope(this);
	}
	
	/**
	 * Pushes a new scope previous to this one
	 * @return the new scope
	 */
	public Scope pushBefore() {
		Scope scope = new Scope(parent);
		parent = scope;
		return scope;
	}
	
	/**
	 * Assigns a variable to the scope.
	 * It walks up the tree until it finds a definition
	 * @param name Name of the variable
	 * @param value Value of the variable
	 * @return Returns true if the assignment was successful
	 */
	public boolean assign(String name, Obj value) {
		name = name.toLowerCase();
		
		Scope scope = this;
		while (scope != null) {
			if (scope.hasLocal(name)) {
				scope.variables.put(name, value);
				return true;
			}
			scope = scope.parent;
		}
		return false;
	}
	public boolean assign(String name, double value) {
		return assign(name, new Variable(value));
	}
	
	/**
	 * Defines a variable in this scope, if the definition already exists
	 * then it does nothing
	 * @param name
	 * @param value
	 */
	public void define(String name, Obj value) {
		if (variables.containsKey(name)) return;
		variables.put(name, value);
	}
	public void define(String name, double value) {
		define(name, new Variable(value));
	}
	
	/**
	 * Tries to assign a variable, if it doesn't exist then defines it
	 * @param name
	 * @param value
	 */
	public void assignOrDefine(String name, Obj value) {
		if (getVariable(name) != null) {
			assign(name, value);
		}
		else {
			define(name, value);
		}
	}
	public void assignOrDefine(String name, double value) {
		assignOrDefine(name, new Variable(value));
	}
	
	/**
	 * Returns the value of a variable
	 * Walks up the scope tree to find it
	 * @param name
	 * @return The value of the variable or null if it doesn't exist
	 */
	public Double getVariable(String name) {
		Obj obj = getObj(name);
		if (obj == null) return null;
		if (!(obj instanceof Variable)) return null;
		Variable v = (Variable) obj;
		return v.value;
	}
	
	/**
	 * Returns a function instance
	 * Walks up the scope tree to find it
	 * @param name
	 * @return The value of the variable or null if it doesn't exist
	 */
	public Function getFunction(String name) {
		Obj obj = getObj(name);
		if (obj == null) return null;
		if (!(obj instanceof Function)) return null;
		return (Function) obj;
	}
	
	/**
	 * Returns an object
	 * Walks up the scope tree to find it
	 * @param name
	 * @return
	 */
	public Obj getObj(String name) {
		name = name.toLowerCase();
		
		Scope scope = this;
		while (scope != null) {
			if (scope.hasLocal(name)) {
				return scope.variables.get(name);
			}
			scope = scope.parent;
		}
		return null;
	}
	
	/**
	 * Gets the parent scope
	 * @return Null if there is not parent
	 */
	public Scope getParent() {
		return parent;
	}
	
	/**
	 * Removes all local variables and functions
	 * @return
	 */
	public void clearLocals() {
		variables.clear();
		//functions.clear();
	}
	
	/**
	 * Gets whether this scope has a variable defined
	 * @param name
	 * @return
	 */
	public boolean hasLocal(String name) {
		return variables.get(name.toLowerCase()) != null;
	}
	
	/**
	 * Removes the parent scope and updates parent to match
	 */
	public void removeParent() {
		if (this.parent == null) throw new RuntimeException("No such parent scope");
		parent = parent.parent;
	}
	
	
	/*public void defineFunction(String name, String[] args, Expression body) {
		Function function = new ExpressionFunction(body, args, this.push());
		functions.put(name.toLowerCase(), function);
	}*/
	/*public Double callFunction(String name, Double[] args) {
		Function function = functions.get(name.toLowerCase());
		if (args.length != function.getArgsCount()) {
			throw new RuntimeException("Expected "+function.getArgsCount()+" arguments");
		}
		return function.invoke(this, args);
	}*/
}
