package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.Address;
import com.simoncomputing.app.winventory.util.DaoException;

public interface AddressDao { 

    public int create( Address value ) throws DaoException;

    public int update( Address value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public Address read( Map<String, Object> map ) throws DaoException;


    // PROTECTED CODE -->
    
    public List<Address> getAll() throws DaoException;

    public List<Address> getAllActive() throws DaoException;
}