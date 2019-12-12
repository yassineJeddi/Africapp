/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.tgcc.Entity.TypeEngin;

/**
 *
 * @author yassine.jeddi
 */
public interface ITypeEnginDAO {
    
    public void addTypeEngin(TypeEngin t);
    public List<TypeEngin> allTypeEngin();
    
    
}
