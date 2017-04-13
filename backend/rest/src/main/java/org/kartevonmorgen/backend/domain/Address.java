package org.kartevonmorgen.backend.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Address {

	public long id;
    public String firstName;
    public String lastName;
    public String street;

    public Address() {
    }

    public Address(Address addressParam) {
    	if(addressParam != null)
    	{
    		this.id  = addressParam.getId();
	    	this.firstName = addressParam.getFirstName();
	    	this.lastName = addressParam.getLastName();
	    	this.street = addressParam.getStreet();
    	}
    }
    
    @Override
	public String toString() {
		return "AddressImpl [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", street=" + street
				+ "]";
	}

	public Address(long idParam, String firstNameParam, String lastNameParam, String streetParam) {
        this.id = idParam;
		this.firstName = firstNameParam;
        this.lastName = lastNameParam;
        this.street = streetParam;
    }

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getStreet() {
		return street;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		return true;
	}
}

