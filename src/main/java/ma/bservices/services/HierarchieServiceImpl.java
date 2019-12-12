/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.ChantierHierarchie;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.FonctionLocal;
import ma.bservices.beans.NiveauFonction;
import ma.bservices.beans.Salarie;
import ma.bservices.beans.SalarieChantier;
import ma.bservices.dao.HierarchieDao;
import ma.bservices.dao.NiveauFonctionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mahdi
 */
@Service("hierarchieService")
public class HierarchieServiceImpl implements Serializable, HierarchieService {

    @Autowired
    HierarchieDao hierarchieDao;
    @Autowired
    NiveauFonctionDao niveauFonctionDao;

    public HierarchieDao getHierarchieDao() {
        return hierarchieDao;
    }

    public void setHierarchieDao(HierarchieDao hierarchieDao) {
        this.hierarchieDao = hierarchieDao;
    }

    public NiveauFonctionDao getNiveauFonctionDao() {
        return niveauFonctionDao;
    }

    public void setNiveauFonctionDao(NiveauFonctionDao niveauFonctionDao) {
        this.niveauFonctionDao = niveauFonctionDao;
    }

    @Override
    public boolean save(ChantierHierarchie ch) {
        return (ch != null) ? hierarchieDao.ajouter(ch) : false;
    }

    @Override
    public boolean delete(ChantierHierarchie ch) {
        return (ch != null) ? hierarchieDao.supprimer(ch) : false;
    }

    @Override
    public List<ChantierHierarchie> loadAll() {
        return hierarchieDao.gets();
    }

    @Override
    public ChantierHierarchie getById(Integer idHierarchie) {
        return (idHierarchie != null) ? hierarchieDao.get(idHierarchie) : null;
    }

    @Override
    public List<Salarie> getSalarieCadre(Integer idChantier) {

        System.out.println("Start Cadre");
        List<NiveauFonction> niveaux = niveauFonctionDao.findAll();
        List<Salarie> result = new ArrayList<>();
        List<Salarie> salaries = listSalarieHaveFonctionWithNiveau(niveaux);
        for (Salarie salarie : salaries) {
            List<Chantier> c = salarie.getChantiers();
            for (Chantier c1 : c) {
                if (c1.getId().equals(idChantier)) {
                    result.add(salarie);
                }
            }
        }
        System.out.println("end Cadre avec : " + result.size());
        return result;
    }

    public List<Salarie> listSalarieHaveFonctionWithNiveau(List<NiveauFonction> niveaux) {
        System.out.println("start List");
        List<Fonction> fonctions = new ArrayList<>();
        for (NiveauFonction n : niveaux) {
            List<FonctionLocal> fls = n.getFls();
            for (FonctionLocal f : fls) {
                fonctions.add(f.getFonction());
            }
        }
        System.out.println("end first loop");
        List<Salarie> salaries = new ArrayList<>();
        for (Fonction f : fonctions) {
            salaries.addAll(f.getSalaries());
        }
        System.out.println("end function : " + salaries.size());
        return salaries;
    }

    @Override
    public List<Salarie> listSalarieByNiveauAndChantier(NiveauFonction niveau, Integer idChantier) {
        List<Fonction> fonctions = new LinkedList<>();
        System.out.println("start List fonction by niveau");
        List<FonctionLocal> fls = niveau.getFls();
        for (FonctionLocal f : fls) {
            fonctions.add(f.getFonction());
        }
        System.out.println("get Salarie by Function");
        List<Salarie> salaries = new LinkedList<>();
        for (Fonction f : fonctions) {
            salaries.addAll(f.getSalaries());
        }
        System.out.println("get result Salaries by chantier");
        List<Salarie> result = new LinkedList<>();
        for (Salarie salarie : salaries) {
            List<Chantier> c = salarie.getChantiers();
            for (Chantier c1 : c) {
                if (c1.getId().equals(idChantier)) {
                    result.add(salarie);
                }
            }
        }
        System.out.println("end function : " + result.size());
        return result;
    }

    @Override
    public List<Salarie> getSalarieSuperieur(Integer idChantier, NiveauFonction niveau) {
        List<NiveauFonction> Preniveaux = niveauFonctionDao.findAll();
        List<NiveauFonction> niveaux = Preniveaux.subList(0, Preniveaux.indexOf(niveau));
        List<Salarie> result = new ArrayList<>();
        List<Salarie> salaries = listSalarieHaveFonctionWithNiveau(niveaux);
        for (Salarie salarie : salaries) {
            List<Chantier> c = salarie.getChantiers();
            for (Chantier c1 : c) {
                if (c1.getId().equals(idChantier)) {
                    result.add(salarie);
                }
            }
        }
        return result;
    }

    @Override
    public ChantierHierarchie getById(SalarieChantier sc) {
        return (sc != null) ? hierarchieDao.get(sc) : null;
    }

}
