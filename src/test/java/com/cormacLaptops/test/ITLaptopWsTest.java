package com.cormacLaptops.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cormacLaptops.data.LaptopDao;
import com.cormacLaptops.model.Laptop;
import com.cormacLaptops.rest.JaxRsActivator;
import com.cormacLaptops.rest.LaptopWs;
import com.cormacLaptops.test.util.UtilsDAO;

@RunWith(Arquillian.class)
public class ITLaptopWsTest {
	@Deployment
	public static JavaArchive createTestArchive() {
		final JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "Test.jar")
				.addClasses(JaxRsActivator.class, LaptopDao.class, UtilsDAO.class, Laptop.class, LaptopWs.class)
				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		return jar;
	}

	@EJB
	private LaptopWs laptopWs;

	@EJB
	private LaptopDao laptopDao;

	@EJB
	private final UtilsDAO utilsDao = new UtilsDAO();

	Laptop laptop = new Laptop();

	@Before
	public void setUp() {
		utilsDao.deleteTable();
		laptop.setMake("Lenovo");
		laptop.setModel("Q50");
		laptop.setRam(4);
		laptop.setPrice(500);
		laptop.setProcessor("i7");
		laptop.setDescription("its great");
		laptop.setImage("lenovo.jpg");
		laptop.setHardDrive(1000);
		laptopDao.addLaptop(laptop);

		laptop = new Laptop();
		laptop.setMake("HP");
		laptop.setModel("x10");
		laptop.setRam(4);
		laptop.setPrice(500);
		laptop.setProcessor("i3");
		laptop.setDescription("its the best");
		laptop.setImage("hp.jpg");
		laptop.setHardDrive(500);
		laptopDao.addLaptop(laptop);
	}

	@Test
	public void findById() {
		final Laptop laptopToCompare = laptopWs.getLaptop(laptop.getId());
		assertTrue(laptopToCompare.equals(laptopToCompare));
	}

	@Test
	public void testGetAllLaptopsWs() {
		final Response response = laptopWs.findAll();
		final List<Laptop> laptopList = (List<Laptop>) response.getEntity();
		assertEquals("Data fetch = data persisted", laptopList.size(), 2);
		final Laptop laptopToCompare = laptopList.get(0);
		assertEquals("Lenovo", laptopToCompare.getMake());
		assertEquals("Q50", laptopToCompare.getModel());
		assertEquals("i7", laptopToCompare.getProcessor());
		assertTrue(response.getStatus() == 200);
	}

	@Test
	public void testAddLaptop() {
		laptop = new Laptop();
		laptop.setMake("HP");
		laptop.setModel("x20");
		laptop.setRam(6);
		laptop.setPrice(500);
		laptop.setProcessor("i3");
		laptop.setDescription("its the best");
		laptop.setImage("hp.jpg");
		laptop.setHardDrive(500);
		laptopWs.saveLaptop(laptop);

		final Response response = laptopWs.findAll();
		final List<Laptop> laptopList = (List<Laptop>) response.getEntity();
		assertTrue(response.getStatus() == 200);
		assertEquals("Data fetch = data persisted", laptopList.size(), 3);
	}

	@Test
	public void testSearch() {
		final Response response = laptopWs.getLaptopByMake("Lenovo");
		final List<Laptop> laptopList = (List<Laptop>) response.getEntity();
		assertTrue(response.getStatus() == 200);
		// make sure each laptop returned is Lenovo
		for (final Laptop laptop : laptopList) {
			assertEquals("Lenovo", laptop.getMake());
		}
	}

	@Test
	public void testSearchByRam() {
		final Response response = laptopWs.getLaptopBytRam(4);
		final List<Laptop> laptopList = (List<Laptop>) response.getEntity();
		assertTrue(response.getStatus() == 200);
		for (final Laptop laptop : laptopList) {
			assertEquals(4, laptop.getRam());
		}
	}

	@Test
	public void testByRamRange() {
		final Response response = laptopWs.getLaptopByRam(3, 5);
		final List<Laptop> laptopList = (List<Laptop>) response.getEntity();
		assertTrue(response.getStatus() == 200);
		for (final Laptop laptop : laptopList) {
			assertTrue(laptop.getRam() >= 3 && laptop.getRam() <= 5);
		}
	}

	@Test
	public void testEditLaptop() {
		laptop.setHardDrive(123);
		laptop.setPrice(122);
		final Response response = laptopWs.updateLaptop(laptop);
		final Laptop laptopToCompare = (Laptop) response.getEntity();
		assertTrue(response.getStatus() == 200);
		assertTrue(laptop.equals(laptopToCompare));
	}

	@Test
	public void testDeleteLaptop() {
		final Response response = laptopWs.removeLaptop(laptop.getId());
		assertTrue(response.getStatus() == 204);
	}
}
