package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.Location;
import com.simoncomputing.app.winventory.util.DaoException;

public interface LocationDao { 

    public int create( Location value ) throws DaoException;

    public int update( Location value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public Location read( Map<String, Object> map ) throws DaoException;

    public List<Location> getListByAddressId( Integer key ) throws DaoException;

    // PROTECTED CODE -->

}