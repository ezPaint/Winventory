package com.simoncomputing.app.winventory.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;
import com.simoncomputing.app.winventory.util.DaoException;

public interface SoftwareDao { 

    public int create( Software value ) throws DaoException;

    public int update( Software value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public Software read( Map<String, Object> map ) throws DaoException;

    public List<Software> getListByName( String key ) throws DaoException;
    public List<Software> getListBySerialNo( String key ) throws DaoException;
    public List<Software> getListByLicenseKey( String key ) throws DaoException;
    public List<Software> getListByPurchasedDate( Date key ) throws DaoException;
    public List<Software> getListByExpirationDate( Date key ) throws DaoException;

    // PROTECTED CODE -->
    /**
     * Retrieves all software objects from database (Software table) matching search term.
     */
    public List<Software> search(String searchText) throws DaoException;
    
    /**
     * Retrieves max size software objects from database (Software table).
     */
    public List<Software> getDefaultResults(int size) throws DaoException;

    /**
     * Get a list of software objects between (inclusive) the dates start and end.
     */
    public List<Software> getListByPurchaseRange(@Param("start") Date start, @Param("end") Date end)
            throws DaoException;
    
    /**
     * Retrieve a list of software objects with an expiration date between 'start' and 'end'
     */
    public List<Software> getListByExpirationRange(@Param("start") Date start,
            @Param("end") Date end) throws DaoException;
    
    /**
     * Get a list of software objects within a cost range
     */
    public List<Software> getListByCostRange(@Param("minCost") String minCost, @Param("maxCost") String maxCost) throws DaoException;

    /**
     * Retrieves all software objects from the database (Software table).
     */
    public List<Software> getAll() throws DaoException;
    
    /**
     * Retrieves all software objects from the database (Software table) matching search terms.
     */
    public List<Software> searchAdvanced( @Param("fields") List<String> fields, @Param("inputs") ArrayList<ArrayList<String>> inputs ) throws DaoException; 

    /**
     * Deletes all data (rows) from SOFTWARE
     */
    public List<Software> deleteAll() throws DaoException; 

}