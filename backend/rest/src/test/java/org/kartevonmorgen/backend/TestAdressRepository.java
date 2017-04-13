package org.kartevonmorgen.backend;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.kartevonmorgen.backend.db.AddressRepository;
import org.kartevonmorgen.backend.db.AddressRepositoryImpl;
import org.kartevonmorgen.backend.domain.Address;

public class TestAdressRepository
{

    private AddressRepository addressRepository;

    @Before
    public void init() {
	addressRepository = new AddressRepositoryImpl();
    }

    @Test
    public void test() {

	// Create an local Address object
	Address in = new Address();
	in.firstName = "Fred";
	in.lastName = "Hauschel";
	in.street = "Linnen";

	// Creates an Address in the storage using the local created Address object
	Address created = addressRepository.create(in);

	// Read the Address from the storage
	Address readed = addressRepository.read(created.id);

	// check if the readed Address object has the same values as the local created Address Object.
	assertEquals(in.firstName, readed.firstName);
	assertEquals(in.lastName, readed.lastName);
	assertEquals(in.street, readed.street);
	assertTrue(readed.id > 0); // We don't know the id, but we know, there has to be one!

	// detele the Address from the storage
	addressRepository.delete(readed.id);

	// Try to read the deleted Address from the storage
	Address deleted = addressRepository.read(readed.id);
	
	// Be sure, that the deleted Address was not found any more.
	assertEquals(null, deleted);
    }

}
