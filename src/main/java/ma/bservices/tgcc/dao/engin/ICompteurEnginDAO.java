/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.CompteurrEngin;

/**
 *
 * @author yassine
 */
public interface ICompteurEnginDAO {
    public List<CompteurrEngin> allCompteurrEngin();
    public List<CompteurrEngin> allCompteurrEnginByIdEngin(Integer id_engin);
    public CompteurrEngin findCompteurrEnginById(Integer id);
    public CompteurrEngin findCompteurrEnginByDate(Date d);
    public void saveCompteurrEngin(CompteurrEngin c);
    public void editCompteurrEngin(CompteurrEngin c);
    public void remouveCompteurrEngin(CompteurrEngin c);
    
}
