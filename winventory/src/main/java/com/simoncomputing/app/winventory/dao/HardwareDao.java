package com.simoncomputing.app.winventory.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.util.DaoException;

public interface HardwareDao { 

    public int create( Hardware value ) throws DaoException;

    public int update( Hardware value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public Hardware read( Map<String, Object> map ) throws DaoException;

    public List<Hardware> getListByLocationId( Long key ) throws DaoException;
    public List<Hardware> getListByUserId( Long key ) throws DaoException;

    // PROTECTED CODE -->

    public List<Hardware> getAll() throws DaoException;
    
    public List<Hardware> getStorage() throws DaoException;
    
    public List<Hardware> getInUse() throws DaoException;
    
    public List<Hardware> searchBasic( String search ) throws DaoException;
    
    public List<String> getTopTypes( Integer limit ) throws DaoException; 
    
    public List<Hardware> searchAdvanced( @Param("columns") List<String> columns, 
            @Param("searches") ArrayList<ArrayList<String>> searches,
            @Param("stored") Boolean stored,
            @Param("owned") Boolean owned,
            @Param("active") Boolean active, 
            @Param("inActive") Boolean inActive,
            @Param("cost") Boolean cost,
            @Param("minCost") double minCost,
            @Param("maxCost") double maxCost,
            @Param("date") Boolean date,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate) throws DaoException; 

}