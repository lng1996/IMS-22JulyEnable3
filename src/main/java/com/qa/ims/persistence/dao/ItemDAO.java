package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class ItemDAO implements Dao<Item> {
	
	public static final Logger LOGGER = LogManager.getLogger();

	
	/**
	 * Creates an Item instance from the result set
	 */
	@Override
	public Item modelFromResultSet(ResultSet resultSet) {
		try {
			Long id = resultSet.getLong("id");
			String name = resultSet.getString("name");
			Double price = resultSet.getDouble("price");
			Long stock = resultSet.getLong("stock");
			return new Item(id, name, price, stock);
		} catch(SQLException e) {
			LOGGER.debug(e);
			LOGGER.info("Item does not exist");
			return null;
		}
		
	}
	
	
	/**
	 * Reads all items from the database
	 */
	@Override
	public List<Item> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items");) {
			List<Item> items = new ArrayList<>();
			while (resultSet.next()) {
				items.add(modelFromResultSet(resultSet));
			}
			return items;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	
	/**
	 * Reads an item from the database using the id
	 */
	@Override
	public Item read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM items WHERE id = ?");) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * Reads the last created item from the database
	 */
	public Item readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	
	/**
	 * Creates an item in the database
	 */
	@Override
	public Item create(Item item) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO items(name, price, stock) VALUES (?,?,?)");) {
			statement.setString(1, item.getName());
			statement.setDouble(2, item.getPrice());
			statement.setLong(3, item.getStock());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	
	/**
	 * Updates an item in the database
	 */
	@Override
	public Item update(Item item) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE items SET name = ?, price = ?, stock = ? WHERE id = ?");) {
			statement.setString(1, item.getName());
			statement.setDouble(2, item.getPrice());
			statement.setLong(3, item.getStock());
			statement.setLong(4, item.getId());
			statement.executeUpdate();
			return read(item.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	
	/**
	 * Deletes an item in the database
	 */
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM items WHERE id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}	

}
