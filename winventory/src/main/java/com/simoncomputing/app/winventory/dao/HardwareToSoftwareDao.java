package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.HardwareToSoftware;
import com.simoncomputing.app.winventory.util.DaoException;

public interface HardwareToSoftwareDao { 

    public int create( HardwareToSoftware value ) throws DaoException;

    public int update( HardwareToSoftware value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public HardwareToSoftware read( Map<String, Object> map ) throws DaoException;

    public List<HardwareToSoftware> getListByHardwareId( Integer key ) throws DaoException;
    public List<HardwareToSoftware> getListBySoftwareId( Integer key ) throws DaoException;

    // PROTECTED CODE -->

}