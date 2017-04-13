package org.kartevonmorgen.backend.db;

import java.util.List;

import org.kartevonmorgen.backend.domain.Address;

public interface AddressRepository
{
    /**
     * Creates an Address Object in the storage.
     * @param addressParam
     * @return
     */
    Address create(Address addressParam);

    /**
     * Reads a Address from the storage.
     * @param id The unique id of the address to read.
     * @return The address for the given id or null, if there is no Address for the given id.
     */
    Address read(long id);

    /**
     * Reads all Addresses from the storage 
     * @return All Addresses from the storage
     */
    List<Address> list();

    /**
     * 
     * @param id The unique id of the address to read.
     * @return True, if the Address was deleted, false if the deletion failed.
     */
    boolean delete(long id);

    /**
     * Updates Fields of the Address. If parameters in the passed Address object are empty / null, they will be overwritten in the storage with ""/null !!
     * @param address The Address, that contains the changes and the none changed fields.
     * @return The updated Address, which has the same content as the passed one, but maybe this is another java instance! 
     */
    Address update(Address address);

}