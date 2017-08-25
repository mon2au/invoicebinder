package com.invoicebinder.server.dataaccess;

import com.invoicebinder.shared.entity.address.Address;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDAO extends BaseDAO<Address, Long> {

	public AddressDAO() {
		super(Address.class);
	}

}
