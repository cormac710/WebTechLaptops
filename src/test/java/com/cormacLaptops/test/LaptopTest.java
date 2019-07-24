package com.cormacLaptops.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.cormacLaptops.model.Laptop;

public class LaptopTest {

	private Laptop laptop;

	@Before
	public void setUp() {
		laptop = new Laptop();
	}

	@Test
	public void testLaptopGettersAndSetters() {
		laptop.setId(1);
		laptop.setMake("lenovo");
		laptop.setModel("Q50");
		laptop.setPrice(700);
		laptop.setRam(4);
		laptop.setHardDrive(500);
		laptop.setProcessor("I3 Quad Core");
		laptop.setDescription("great new laptop");
		laptop.setImage("lenovo.jpg");

		assertEquals(1, laptop.getId());
		assertEquals("lenovo", laptop.getMake());
		assertEquals("Q50", laptop.getModel());
		assertEquals(700, laptop.getPrice(), 0.00);
		assertEquals(4, laptop.getRam());
		assertEquals(500, laptop.getHardDrive());
		assertEquals("I3 Quad Core", laptop.getProcessor());
		assertEquals("great new laptop", laptop.getDescription());
		assertEquals("lenovo.jpg", laptop.getImage());
		
	}

}
