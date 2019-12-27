/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Salarie;
import ma.bservices.dao.SalarieDao;
import ma.bservices.tgcc.Entity.Mensuel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("salarieServiceIn")
public class SalarieServiceImpl implements Serializable, SalarieServiceIn {

    @Autowired
    SalarieDao salarieDao;

    public SalarieDao getSalarieDao() {
        return salarieDao;
    }

    public void setSalarieDao(SalarieDao salarieDao) {
        this.salarieDao = salarieDao;
    }

    /**
     * afficher la liste des salaries qui n'ont aucun supp
     *
     * @return
     */
    @Override
    public List<Salarie> listNotAffected() {
        return salarieDao.listNotAffected();
    }

    /**
     * afficher la liste des supp
     *
     * @return
     */
    @Override
    public List<Salarie> listSupp() {
        return salarieDao.listSupp();
    }
    
    @Override
    public List<Salarie> listSalarieByChantierId(int idChantier) {
        return salarieDao.listSalarieByChantierId(idChantier);
    }

    /**
     * Test le salarie supp doit être diff du salarie et s'ils sont nulls ou non
     *
     * @param s
     * @param supp
     * @return
     */
    @Override
    public Boolean affecterSupp(Salarie s, Salarie supp) {
        if (s != null) {
            if (supp != null) {
                if (s.getId() != supp.getId()) {
                    return salarieDao.affecterSupp(s, supp);
                }
            }
        }
        return null;
    }

    /**
     * on doit pas ajouter un salarie nulls (on a ajouter cette methode pour
     * l'utiliser dans le test unitaire)
     *
     * @param s
     * @return
     */
    @Override
    public Salarie addSalarie(Salarie s) {
        if (s != null) {
            return salarieDao.addSalarie(s);
        }
        return null;
    }

    /**
     * on doit pas supprimer un salarie nulls (on a ajouter cette methode pour
     * l'utiliser dans le test unitaire)
     *
     * @param s
     * @return
     */
    @Override
    public Salarie deleteSalarie(Salarie s) {
        if (s != null) {
            return salarieDao.deleteSalarie(s);
        }
        return null;
    }

    /**
     * on desafete un salarie supp d'un salarie
     *
     * @param s
     * @return
     */
    @Override
    public Boolean deleteSupp(Salarie s) {
        if (s != null) {
            return salarieDao.deleteSupp(s);
        }
        return false;
    }

    /**
     * get la liste des salaries selon leurs supp
     *
     * @param s
     * @return
     */
    @Override
    public List<Salarie> listSalarieBySupp(Salarie s) {
        if (s != null) {
            return salarieDao.listSalarieBySupp(s);
        }
        return null;
    }

    @Override
    public List<Salarie> getSalarieChefEquipe() {
        return salarieDao.getSalarieChefEquipe();
    }

//    @Override
//    public List<Salarie> getSalarieChefEquipeByChantier(int idChantier) {
//        return salarieDao.getSalarieChefEquipeByChantier(idChantier);
//    }
    @Override
    public List<Salarie> listSalarieBySupp() {
        return salarieDao.listSalarieBySupp();
    }

    @Override
    public Salarie updateSalarie(Salarie s) {
        return salarieDao.updateSalarie(s);
    }

    @Override
    public List<Salarie> getSalarieNotChef(Integer idc, Integer etatID) {
        return salarieDao.getSalarieNotChef(idc, etatID);
    }
    
    
    /**
     * 
     * Recupère de la base de données la liste des salarié avec le type de pointage : TYPE_FONCTION_POINTAGE_UPSIT
     * dans une chantier en paramètere, qui ne sont pas chef d'équipe et qui sont en état Actif ou Actif provisoire.
     * 
     * @param idc Id du chantier
     * @return List<Salarie>
    */
    @Override
    public List<Salarie> getActifSalarie(Integer idc) {
        return salarieDao.getActifSalarieWithSup(idc);
    }

    @Override
    public List<String> distinct_Salarie_Ville() {
        return this.salarieDao.getListVilleDistinct();
    }

    @Override
    public List<Mensuel> getMensuelChefEquipe() {
        return salarieDao.getMensuelsChefEquipe();
    }
    
    /**
     * Retourne la liste des mensuels chef d'équipe dans un chantier envoyé en paramètressss
     * 
     * @param idChantier
     * @return List<Mensuel>
     */
    @Override
    public List<Mensuel> getMensuelChefEquipeInChantier(int chantier) {
        return salarieDao.getMensuelsChefEquipeInChantier(chantier);
    }

    @Override
    public List<Object> getSalarieHearchChef(Integer idChantier, Integer idChef) {
       return salarieDao.getSalarieHearchChef(idChantier, idChef);
    }

    @Override
    public Salarie getSalarieByID(Integer idc) {
         return salarieDao.getSalarieByID(idc);
    }

    @Override
    public List<Salarie> listSalarieByListChantier(String listChantiers) {
        return salarieDao.listSalarieByListChantier(listChantiers);
    }
    @Override
    public List<Salarie> listSalarieBlackListSorti() {
         return salarieDao.listSalarieBlackListSorti();
    } 
}
