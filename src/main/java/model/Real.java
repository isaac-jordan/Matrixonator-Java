/**
 * 
 */
package main.java.model;

/**
 * A concrete class that implements the Real Field with doubles.
 * @author Isaac Jordan
 *
 */
public class Real implements Field<Real> {
	
	private final double value;
	
	public Real(double value) {
		this.value = value;
	}
	
	public Real add(Real b) {
		return new Real(value + b.value);
	}

	public Real multiply(Real b) {
		return new Real(value * b.value);
	}

	public Real inverse() {
		return new Real(1/value);
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

}
