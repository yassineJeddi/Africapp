/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.beans.Designation;

/**
 *
 * @author yassine.jeddi
 */
public interface IDesignationServices {
    
    public void addDesignation(Designation d);
    public Designation designationById(Integer id);
    public List<Designation> allDesignation();
    public List<Designation> allDesignationByFamille(String famille);
}
