/**
 * 
 */
package main.java.model;

/**
 * A concrete class that implements the Real Field with doubles.
 * @author Isaac Jordan
 *
 */
public class Real implements Field {
	
	private double value;
	private static final Real ZERO = new Real(0.0);
	private static final Real UNITY = new Real(1.0);
	
	public Real(double value) {
		this.value = value;
	}
	
	public Real add(Field b) {
		Real bee = (Real) b;
		return new Real(value + bee.value);
	}

	public Real multiply(Field b) {
		Real bee = (Real) b;
		return new Real(value * bee.value);
	}

	public Real inverse() {
		return new Real(1/value);
	}
	
	public Real negate() {
		return new Real(value*-1);
	}
	
	@Override
	public boolean equals(Object o) {
		return value == ((Real) o).getValue();
	}
	
	@Override
	public String toString() {
		return ((long) value == value ? "" + (long) value : "" + value);
	}
	
	public double getValue() {
		return value;
	}
	
	public Real getZero() {
		return ZERO;
	}
	
	public Real getUnity() {
		return UNITY;
	}
	
	public void normalise() {
		if (value == -0.0) {
			value = 0.0;
		}
	}

}
