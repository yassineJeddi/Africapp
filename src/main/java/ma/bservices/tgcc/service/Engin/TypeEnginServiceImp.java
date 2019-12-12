/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.TypeEngin;
import ma.bservices.tgcc.dao.engin.ITypeEnginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine.jeddi
 */

@Service("typeEnginServiceImp")
public class TypeEnginServiceImp implements ITypeEnginService, Serializable{

    @Autowired
    private ITypeEnginDAO typeEnginDAO;

    public ITypeEnginDAO getTypeEnginDAO() {
        return typeEnginDAO;
    }

    public void setTypeEnginDAO(ITypeEnginDAO typeEnginDAO) {
        this.typeEnginDAO = typeEnginDAO;
    } 
    
    @Override
    public void addTypeEngin(TypeEngin t) {
        typeEnginDAO.addTypeEngin(t);
    }

    @Override
    public List<TypeEngin> allTypeEngin() {
        return typeEnginDAO.allTypeEngin();
    }
    
}
