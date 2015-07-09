package com.simoncomputing.app.winventory.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.DaoException;

public interface SoftwareDao {

    public int create(Software value) throws DaoException;

    public int update(Software value) throws DaoException;

    public int delete(Map<String, Object> map) throws DaoException;

    public Software read(Map<String, Object> map) throws DaoException;

    public List<Software> getListByName(String key) throws DaoException;
    public List<Software> getListBySerialNo(String key) throws DaoException;
    public List<Software> getListByLicenseKey(String key) throws DaoException;
    public List<Software> getListByPurchasedDate(Date key) throws DaoException;
    public List<Software> getListByExpirationDate(Date key) throws DaoException;

    // PROTECTED CODE -->
    public List<Software> search(String searchText) throws DaoException;
    public List<Software> getDefaultResults(int size) throws DaoException;

    public List<Software> getListByPurchaseRange(@Param("start") Date start, @Param("end") Date end)
            throws DaoException;
    public List<Software> getListByExpirationRange(@Param("start") Date start,
            @Param("end") Date end) throws DaoException;

    public List<Software> getAll() throws DaoException;
    
    //Hasn't been tested -- theoretical advanced search method
    //needs to be added to the .xml
    public List<Software> searchAdvanced( @Param("columns") List<String> columns, @Param("searches") ArrayList<ArrayList<String>> searches ) throws DaoException; 
}