package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.RefPermissionToRole;
import com.simoncomputing.app.winventory.util.DaoException;

public interface RefPermissionToRoleDao { 

    public int create( RefPermissionToRole value ) throws DaoException;

    public int update( RefPermissionToRole value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public RefPermissionToRole read( Map<String, Object> map ) throws DaoException;

    public List<RefPermissionToRole> getListByPermissionId( Integer key ) throws DaoException;
    public List<RefPermissionToRole> getListByRoleId( Integer key ) throws DaoException;

    // PROTECTED CODE -->
    public List<RefPermissionToRole> getAll();
    public void insert(Map<String,Object> map);
}