package com.simoncomputing.app.winventory.dao;

import java.util.Map;

import com.simoncomputing.app.winventory.domain.RefPermission;
import com.simoncomputing.app.winventory.util.DaoException;

public interface RefPermissionDao { 

    public int create( RefPermission value ) throws DaoException;

    public int update( RefPermission value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public RefPermission read( Map<String, Object> map ) throws DaoException;


    // PROTECTED CODE -->

}