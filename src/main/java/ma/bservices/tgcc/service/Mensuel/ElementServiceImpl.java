/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import ma.bservices.tgcc.dao.Mensuel.ElementDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wattah
 */

@Service("elementService")
public class ElementServiceImpl implements ElementService , Serializable{
 
    
    
    @Autowired
    private ElementDAO elementDao;

    
    
    public ElementDAO getElementDao() {
        return elementDao;
    }

    public void setElementDao(ElementDAO elementDao) {
        this.elementDao = elementDao;
    }

    
}
