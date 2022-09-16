package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class ItemDAOTest {
	
	private final ItemDAO dao = new ItemDAO();
	
	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}
	
	@Test
	public void testCreate() {
		long first = dao.readAll().get(0).getId();
		int intfirst = (int) first;
		long LPlusOne = new Integer(intfirst + 1);
		int IPlusOne = new Integer(intfirst + 1);
		final Item testItem = new Item(LPlusOne, "Testing", 543.21, 5l);
		assertEquals(testItem, dao.create(testItem));
		System.out.println(dao.delete(intfirst));
		System.out.println(dao.delete(IPlusOne));
		
	}
	
	@Test
	public void testReadAll() {
		List<Item> testItems = new ArrayList<>();
		long first = dao.readAll().get(0).getId();
		int intfirst = (int) first;
		testItems.add(new Item(first, "testItem", 123.45, 6l));
		assertEquals(testItems, dao.readAll());
		System.out.println(dao.delete(intfirst));
	}
	
	@Test
	public void testReadLatest() {
		long first = dao.readAll().get(0).getId();
		int intfirst = (int) first;
		assertEquals(new Item(first, "testItem", 123.45, 6l), dao.readLatest());
		System.out.println(dao.readAll());
		System.out.println(dao.delete(intfirst));
		System.out.println(dao.readAll());
	}
	
	@Test
	public void testRead() {
		long first = dao.readAll().get(0).getId();
		int intfirst = (int) first;
		final long ID = first;
		assertEquals(new Item(ID, "testItem", 123.45, 6l), dao.read(ID));
		System.out.println(dao.delete(intfirst));
	}

	@Test
	public void testUpdate() {
		long first = dao.readAll().get(0).getId();
		int intfirst = (int) first;
		final long ID = first;
		final Item updatedItem = new Item(ID, "testItem", 123.45, 8l);
		assertEquals(updatedItem, dao.update(updatedItem));
		dao.update(updatedItem);
		System.out.println(dao.delete(intfirst));
	}
	
	@Test
	public void testUpdateInvalidItem() {
		long first = dao.readAll().get(0).getId();
		int intfirst = (int) first;
		final Item updatedItem = new Item(4000L, "testItem", 50.0, 123l);
		assertEquals(null, dao.update(updatedItem));
		System.out.println(dao.delete(intfirst));
	}

	@Test
	public void testDelete() {
		long first = dao.readAll().get(0).getId();
		int intfirst = (int) first;
		assertEquals(1, dao.delete(intfirst));
	}

}
