package main.java.model;

/**
 * Defines the methods required to define a mathematical Field for use in a Matrix.
 * Example concrete classes are the Reals, or Complex numbers.
 * @author isaac
 *
 * @param <T>
 */
public interface Field<T> {
	
	public T add(T a);
	
	public T multiply(T a);
	
	public T inverse();

}
