package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.PermissionToUser;
import com.simoncomputing.app.winventory.util.DaoException;

public interface PermissionToUserDao { 

    public int create( PermissionToUser value ) throws DaoException;

    public int update( PermissionToUser value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public PermissionToUser read( Map<String, Object> map ) throws DaoException;

    public List<PermissionToUser> getListByPermissionCode( String key ) throws DaoException;
    public List<PermissionToUser> getListByUserId( Integer key ) throws DaoException;

    // PROTECTED CODE -->

}