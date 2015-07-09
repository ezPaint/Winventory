package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.RoleToUser;
import com.simoncomputing.app.winventory.util.DaoException;

public interface RoleToUserDao { 

    public int create( RoleToUser value ) throws DaoException;

    public int update( RoleToUser value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public RoleToUser read( Map<String, Object> map ) throws DaoException;

    public List<RoleToUser> getListByRoleId( Integer key ) throws DaoException;
    public List<RoleToUser> getListByUserId( Integer key ) throws DaoException;

    // PROTECTED CODE -->

}