package com.simoncomputing.app.winventory.domain;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.util.BoException;


/**
* The HardwareToSoftware Table.
*/
public class HardwareToSoftware {

    private Long      hardwareId;
    private Long      softwareId;

    public Long      getHardwareId() { return hardwareId; }
    public void      setHardwareId( Long value ) { hardwareId = value; }
    public Long      getSoftwareId() { return softwareId; }
    public void      setSoftwareId( Long value ) { softwareId = value; }
    // PROTECTED CODE -->
    private Hardware h;
    /**
     * Get a hardware object based on a hardware ID. 
     * PRECONDITION: hardwareId must NOT be null, hardwareId MUST be an integer
     * @return
     * @throws NumberFormatException
     * @throws BoException
     */
    public Hardware getHardware() throws NumberFormatException, BoException{
        h = HardwareBo.getInstance().read(Long.valueOf(hardwareId.toString()));
        return h;
    }
//    public HardwareToSoftware(Long hwId, Long swId){
//        setHardwareId(hwId);
//        setSoftwareId(swId);
//    }

}