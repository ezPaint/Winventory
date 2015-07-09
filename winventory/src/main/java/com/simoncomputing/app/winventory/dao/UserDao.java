package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.DaoException;

public interface UserDao { 

    public int create( User value ) throws DaoException;

    public int update( User value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public User read( Map<String, Object> map ) throws DaoException;

    public List<User> getListByRoleId( Integer key ) throws DaoException;

    // PROTECTED CODE -->
	
	public User getUserByUsername(String username);
	
	public User getUserByEmail(String email);
	
	public List<String> getPermissionsByUserKey(long key);
	
	public List<String> getPermissionsByUser(User u);
	
	public List<User> getAll();

}