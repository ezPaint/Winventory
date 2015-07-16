package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.HardwareToSoftware;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.DaoException;

public interface HardwareToSoftwareDao { 

    public int create(@Param("hardwareId")  Long hardwareId, @Param("softwareId") Long softwareId ) throws DaoException;

    public int update( HardwareToSoftware value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public HardwareToSoftware read( Map<String, Object> map ) throws DaoException;

    public List<HardwareToSoftware> getListByHardwareId( Long key ) throws DaoException;
    public List<HardwareToSoftware> getListBySoftwareId( Long key ) throws DaoException;

    // PROTECTED CODE -->
    public List<Software> getSoftwareByHardwareId( Long key ) throws DaoException;
    public List<Hardware> getHardwareBySoftwareId( Long key ) throws DaoException;

}