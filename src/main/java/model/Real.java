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
	
	public final double value;
	
	public Real(double value) {
		this.value = value;
	}
	
	
	@Override
	public Real add(Real b) {
		return new Real(value + b.value);
	}

	@Override
	public Real multiply(Real b) {
		return new Real(value * b.value);
	}

	@Override
	public Real inverse() {
		return new Real(1/value);
	}

}
