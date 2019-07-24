package com.cormacLaptops.test;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.cormacLaptops.model.Laptop;
import com.cormacLaptops.test.util.RestApiHelper;
import com.cormacLaptops.test.util.RestResponse;
import com.cormacLaptops.test.util.UtilsDAOExt;
import com.google.gson.Gson;

public class LaptopRestAPITest {

	@Before
	public void setUp() {
		final UtilsDAOExt utilsDAO = new UtilsDAOExt();
		utilsDAO.deleteTable();
		utilsDAO.addThreeRows();
	}

	@Test
	public void testPostLaptop() {
		final String fileName = "TestDataFile";
		final Map<String, String> headers = new LinkedHashMap<String, String>();

		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");

		RestResponse response = RestApiHelper.performPostRequest("http://localhost:8080/cormacLaptops/rest/laptops",
				fileName, headers);
		assertEquals(HttpStatus.SC_CREATED, response.getStatusCode());

		final Laptop laptop = new Gson().fromJson(response.getResponseBody(), Laptop.class);
		final String url = "http://localhost:8080/cormacLaptops/rest/laptops/" + laptop.getId();
		response = RestApiHelper.performGetRequest(url, null);

		assertEquals("Toshiba", laptop.getMake());
		assertEquals("A12", laptop.getModel());
		assertEquals(150, laptop.getPrice(), 0.01);
		assertEquals(8, laptop.getRam());
		assertEquals(500, laptop.getHardDrive());
		assertEquals("i3", laptop.getProcessor());
		assertEquals("a great new laptop", laptop.getDescription());
	}

	@Test
	public void testGetLaptops() {
		final String url = "http://localhost:8080/cormacLaptops/rest/laptops";
		final RestResponse response = RestApiHelper.performGetRequest(url, null);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());

		final Laptop[] laptops = new Gson().fromJson(response.getResponseBody(), Laptop[].class);
		assertEquals(3, laptops.length);

		Laptop laptop = laptops[0];
		assertEquals("Lenovo", laptop.getMake());
		assertEquals("Q50", laptop.getModel());

		laptop = laptops[1];
		assertEquals("MacBook", laptop.getMake());
		assertEquals("Pro", laptop.getModel());
	}

	@Test
	public void testGetLaptopById() {
		final String url = "http://localhost:8080/cormacLaptops/rest/laptops/2";
		final RestResponse response = RestApiHelper.performGetRequest(url, null);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());

		final Laptop laptop = new Gson().fromJson(response.getResponseBody(), Laptop.class);
		assertEquals("MacBook", laptop.getMake());
		assertEquals("Pro", laptop.getModel());
	}

	@Test
	public void testPutLaptop() {
		final String fileName = "TestDataPut";
		final Map<String, String> headers = new LinkedHashMap<String, String>();

		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");

		final String url = "http://localhost:8080/cormacLaptops/rest/laptops/3";
		RestResponse response = RestApiHelper.performPutRequest(url, fileName, headers);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());

		final Laptop laptop = new Gson().fromJson(response.getResponseBody(), Laptop.class);
		response = RestApiHelper.performGetRequest(url, null);

		assertEquals("toshiba", laptop.getMake());
		assertEquals("ZX12", laptop.getModel());
		assertEquals(750, laptop.getPrice(), 0.01);
		assertEquals(4, laptop.getRam());
		assertEquals(1000, laptop.getHardDrive());
		assertEquals("i7", laptop.getProcessor());
		assertEquals("Updated Desc", laptop.getDescription());
	}

	@Test
	public void testSearch() {
		final String url = "http://localhost:8080/cormacLaptops/rest/laptops/search?make=MacBook";
		final RestResponse response = RestApiHelper.performGetRequest(url, null);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());

		final Laptop[] laptops = new Gson().fromJson(response.getResponseBody(), Laptop[].class);
		assertEquals(1, laptops.length);

		assertEquals("MacBook", laptops[0].getMake());
		assertEquals("Pro", laptops[0].getModel());
	}

	@Test
	public void testSearchByRam() {
		final String url = "http://localhost:8080/cormacLaptops/rest/laptops/ram/4";
		final RestResponse response = RestApiHelper.performGetRequest(url, null);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());

		final Laptop[] laptops = new Gson().fromJson(response.getResponseBody(), Laptop[].class);
		assertEquals(1, laptops.length);

		assertEquals("MacBook", laptops[0].getMake());
		assertEquals("Pro", laptops[0].getModel());
	}

	@Test
	public void testDeleteLaptop() {
		String url = "http://localhost:8080/cormacLaptops/rest/laptops";
		RestResponse response = RestApiHelper.performGetRequest(url, null);
		Laptop[] laptops = new Gson().fromJson(response.getResponseBody(), Laptop[].class);
		assertEquals(3, laptops.length);

		url = "http://localhost:8080/cormacLaptops/rest/laptops/2";
		response = RestApiHelper.performDeleteRequest(url);
		assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatusCode());
		response = RestApiHelper.performGetRequest(url, null);

		final Laptop laptop = new Gson().fromJson(response.getResponseBody(), Laptop.class);
		assertEquals(null, laptop);

		url = "http://localhost:8080/cormacLaptops/rest/laptops";
		response = RestApiHelper.performGetRequest(url, null);
		laptops = new Gson().fromJson(response.getResponseBody(), Laptop[].class);
		assertEquals(2, laptops.length);
	}

}
