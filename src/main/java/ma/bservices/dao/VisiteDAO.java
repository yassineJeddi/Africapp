/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.DossierMedical;
import ma.bservices.tgcc.Entity.Visite;

/**
 *
 * @author BARAKA
 */
public interface VisiteDAO {
    public Long addVisite(Visite visite);
    public Long updateVisite(Visite visite);
    public boolean deleteVisite(Visite visite);
    public List<Visite> findAllVisite();
    public List<Visite> findVisiteByType(String type);
    public List<Visite> findVisitesByDossierMedical(DossierMedical dos);
    public Date lastDateVisiteByDossier(DossierMedical dos);
    String docteurLastVisiteByDossier(DossierMedical dos); 
}
