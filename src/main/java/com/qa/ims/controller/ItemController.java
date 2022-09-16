package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

public class ItemController implements CrudController<Item> {

	public static final Logger LOGGER = LogManager.getLogger();
	
	private ItemDAO itemDAO;
	private Utils utils;
	
	public ItemController(ItemDAO itemDAO, Utils utils) {
		this.itemDAO = itemDAO;
		this.utils = utils;
	}

	/**
	 * Reads all items to the logger
	 */
	@Override
	public List<Item> readAll() {
		List<Item> items = itemDAO.readAll();
		if (items.size() > 0) {
			for (Item item : items) {
				LOGGER.info(item);
			}
		} else {
			LOGGER.info("There are currently no items in the system");
		}
		
		return items;
	}

	/**
	 * Creates an item by taking in user input
	 */
	@Override
	public Item create() {
		LOGGER.info("Please enter an item name");
		String name = utils.getString();
		LOGGER.info("Please enter a price, input should be numerical without symbols");
		Double price = utils.getDouble();
		LOGGER.info("Please enter how many are in stock");
		Long stock = utils.getLong();
		Item item = itemDAO.create(new Item(name, price, stock));
		LOGGER.info("Item created");
		return item;
	}

	/**
	 * Updates an existing item by taking in user input
	 */
	@Override
	public Item update() {
		LOGGER.info("Please enter the id of the item you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter a name");
		String name = utils.getString();
		LOGGER.info("Please enter a price");
		Double price = utils.getDouble();
		LOGGER.info("Please enter the number in stock");
		Long stock = utils.getLong();
		Item item = itemDAO.update(new Item(id, name, price, stock));
		if (item != null) {
			LOGGER.info("Item updated");
		}
		return item;
	}

	
	/**
	 * Deletes an existing item by taking in user input
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the item you would like to delete");
		Long id = utils.getLong();
		return itemDAO.delete(id);
	}


}
