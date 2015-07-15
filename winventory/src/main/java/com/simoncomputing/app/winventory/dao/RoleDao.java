package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.Role;
import com.simoncomputing.app.winventory.domain.User;
import com.simoncomputing.app.winventory.util.DaoException;

public interface RoleDao { 

    public int create( Role value ) throws DaoException;

    public int update( Role value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public Role read( Map<String, Object> map ) throws DaoException;


    // PROTECTED CODE -->

    public List<Role> getAll();

    public int insert(Role newRole);
}