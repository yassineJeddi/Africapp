/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.util.List;
import ma.bservices.tgcc.Entity.MvmtCaisse;

/**
 *
 * @author yassine.jeddi
 */
public interface IMvmtCaisseService {    
    
    public void addMvmtCaisse(MvmtCaisse m);
    public void editMvmtCaisse(MvmtCaisse m);
    public void remouvMvmtCaisse(MvmtCaisse m);
    public MvmtCaisse mvmtCaisseByID(int id);
    public List<MvmtCaisse> allMvmtCaisse();
    public List<MvmtCaisse> allMvmtCaisseByIdCaisse(int idCaisse);
    public String soldeCaisse(int idCaisse);
    
}
