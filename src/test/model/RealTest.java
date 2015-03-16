package test.model;

import static org.junit.Assert.*;
import main.java.model.Real;

import org.junit.Before;
import org.junit.Test;

public class RealTest {
	
	Real a,b,c;
	
	@Before
	public void setup() {
		a = new Real(5);
		b = new Real(2.5);
		c = new Real(12.5);
	}
	
	@Test
	public void addTest() {
		assertTrue("", a.add(b).getValue() == 7.5);
	}
	
	@Test
	public void multTest() {
		assertTrue("", a.multiply(b).equals(c));
	}
	
	@Test
	public void toStringTest() {
		assertTrue("", a.toString().equals("5"));
		assertTrue("", c.toString().equals("12.5"));
	}
}
