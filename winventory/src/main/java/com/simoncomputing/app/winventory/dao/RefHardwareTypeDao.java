package com.simoncomputing.app.winventory.dao;

import java.util.Map;

import com.simoncomputing.app.winventory.domain.RefHardwareType;
import com.simoncomputing.app.winventory.util.DaoException;

public interface RefHardwareTypeDao { 

    public int create( RefHardwareType value ) throws DaoException;

    public int update( RefHardwareType value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public RefHardwareType read( Map<String, Object> map ) throws DaoException;


    // PROTECTED CODE -->

}