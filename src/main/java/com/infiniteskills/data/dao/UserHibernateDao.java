package com.infiniteskills.data.dao;

import java.util.List;

import com.infiniteskills.data.dao.interfaces.UserDao;
import com.infiniteskills.data.entities.User;

public class UserHibernateDao extends AbstractHibernateDao<User,Long> implements UserDao {

	@Override
	public List<User> findByFirstName(String firstName) {
		//add implementation here...
		return null;
	}

}
