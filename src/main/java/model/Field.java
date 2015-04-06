package main.java.model;

/**
 * Defines the methods required to define a mathematical Field for use in a Matrix.
 * Example concrete classes are the Reals, or Complex numbers.
 * @author Isaac Jordan
 *
 */
public interface Field {
	
	public Field add(Field a);
	
	public Field multiply(Field a);
	
	public Field inverse();
	
	public Field negate();
	
	public Field getUnity();
	
	public Field getZero();
	
	public void normalise();
	
	public boolean equals(Object o);
	

}
