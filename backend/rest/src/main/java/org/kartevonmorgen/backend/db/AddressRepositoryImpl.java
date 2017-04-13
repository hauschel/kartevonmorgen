package org.kartevonmorgen.backend.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.kartevonmorgen.backend.Config;
import org.kartevonmorgen.backend.domain.Address;

public class AddressRepositoryImpl implements AddressRepository
{

    private Connection connection;

    public AddressRepositoryImpl()
    {
	try
	{
	    Class.forName("org.h2.Driver");
	    connection = DriverManager.getConnection(Config.DB_NAME);
	} catch (Exception e)
	{

	    throw new RuntimeException("Database initialisation error!", e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kartevonmorgen.backend.db.AddressRepository#createAddress(org.
     * kartevonmorgen.backend.db.Address)
     */
    @Override
    public Address create(Address addressParam) {
	Address result = null;

	try
	{
	    Statement statement = connection.createStatement();

	    final ResultSet resultSet = statement.executeQuery("call NEXT VALUE FOR ADDRESS_ID;");

	    long id = 0;
	    if (resultSet.next())
	    {
		id = resultSet.getLong(1);
	    }
	    assert id != 0;

	    PreparedStatement preparedStatement = connection
		    .prepareStatement("INSERT INTO ADDRESS values (?, ?, ?, ?);");
	    preparedStatement.setLong(1, id);
	    preparedStatement.setString(2, addressParam.getFirstName());
	    preparedStatement.setString(3, addressParam.getLastName());
	    preparedStatement.setString(4, addressParam.getStreet());
	    preparedStatement.executeUpdate();

	    result = read(id);
	} catch (SQLException e)
	{
	    throw new RuntimeException("Error while accessing database (" + Config.DB_NAME + ").", e);
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * @see org.kartevonmorgen.backend.db.AddressRepository#read(long)
     */
    @Override
    public Address read(long id) {
	Address result = null;
	try
	{
	    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ADDRESS WHERE ID = ?;");
	    preparedStatement.setLong(1, id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    List<Address> adressList = convert(resultSet);
	    if (!adressList.isEmpty())
	    {
		result = adressList.get(0);
	    }
	} catch (SQLException e)
	{
	    throw new RuntimeException("Error while accessing database (" + Config.DB_NAME + ").", e);
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kartevonmorgen.backend.db.AddressRepository#readAllAddresses()
     */
    @Override
    public List<Address> list() {
	List<Address> result = new ArrayList<Address>();
	try
	{
	    Statement statement = connection.createStatement();
	    final ResultSet resultSet = statement.executeQuery("SELECT * FROM ADDRESS;");
	    result = convert(resultSet);
	} catch (SQLException e)
	{
	    throw new RuntimeException("Error while accessing database (" + Config.DB_NAME + ").", e);
	}
	return result;
    }

    private List<Address> convert(final ResultSet resultSetParam) throws SQLException {
	List<Address> result = new ArrayList<Address>();
	while (resultSetParam.next())
	{
	    result.add(new Address(resultSetParam.getLong("ID"), resultSetParam.getString("FIRSTNAME"),
		    resultSetParam.getString("LASTNAME"), resultSetParam.getString("STREET")));
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * @see org.kartevonmorgen.backend.db.AddressRepository#delete(long)
     */
    @Override
    public boolean delete(long id) {
	boolean result = true;
	try
	{
	    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ADDRESS WHERE ID = ?;");
	    preparedStatement.setLong(1, id);
	    preparedStatement.executeUpdate();
	} catch (SQLException e)
	{
	    throw new RuntimeException("Error while accessing database (" + Config.DB_NAME + ").", e);
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * @see org.kartevonmorgen.backend.db.AddressRepository#update(org.kartevonmorgen.backend.domain.Address)
     */
    @Override
    public Address update(Address address) {
	Address result = null;
	try
	{
	    PreparedStatement preparedStatement = connection
		    .prepareStatement("UPDATE ADDRESS SET firstname = ?, lastname = ?, street = ?;");
	    preparedStatement.setString(1, address.firstName);
	    preparedStatement.setString(2, address.lastName);
	    preparedStatement.setString(3, address.street);
	    preparedStatement.executeUpdate();
	    result = read(address.getId());
	} catch (SQLException e)
	{
	    throw new RuntimeException("Error while accessing database (" + Config.DB_NAME + ").", e);
	}
	return result;
    }
}
