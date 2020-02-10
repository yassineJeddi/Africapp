/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;
import ma.bservices.tgcc.Entity.Citerne;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.TraceCiterne;
import ma.bservices.tgcc.dao.Mensuel.MensuelDAO;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import ma.bservices.tgcc.dao.engin.CiterneDAO;
import ma.bservices.tgcc.dao.engin.EnginDAO;
import ma.bservices.tgcc.dao.engin.LivraisonCiterneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("citerneService")
public class CiterneServiceImpl implements CiterneService, Serializable {

    @Autowired
    CiterneDAO citerneDAO;

    @Autowired
    ChantierDAO chantierDAO;

    @Autowired
    LivraisonCiterneDAO livraisonCiterneDAO;

    @Autowired
    EnginDAO enginDAO;

    @Autowired
    MensuelDAO mensuelDAO;

    /**
     * getters and setters
     */
    public LivraisonCiterneDAO getLivraisonCiterneDAO() {
        return livraisonCiterneDAO;
    }

    public void setLivraisonCiterneDAO(LivraisonCiterneDAO livraisonCiterneDAO) {
        this.livraisonCiterneDAO = livraisonCiterneDAO;
    }

    public MensuelDAO getMensuelDAO() {
        return mensuelDAO;
    }

    public void setMensuelDAO(MensuelDAO mensuelDAO) {
        this.mensuelDAO = mensuelDAO;
    }

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public void setChantierDAO(ChantierDAO chantierDAO) {
        this.chantierDAO = chantierDAO;
    }

    public EnginDAO getEnginDAO() {
        return enginDAO;
    }

    public void setEnginDAO(EnginDAO enginDAO) {
        this.enginDAO = enginDAO;
    }

    public CiterneDAO getCiterneDAO() {
        return citerneDAO;
    }

    public void setCiterneDAO(CiterneDAO citerneDAO) {
        this.citerneDAO = citerneDAO;
    }

    /**
     * end setters and getters
     */
    /**
     * methode pour recupere liste des Citerne
     *
     * @return
     */
    
    
    @Override
    public Citerne findCiternById(int id) {
        return this.citerneDAO.findCiternById(id);
    }
    
    @Override
    public List<Citerne> find_all_Citerne() {
        return this.citerneDAO.find_all_Citerne();
    }

    @Override
    public void save_citerne(Citerne citerne, String[] l_chantier_to) {

        LinkedList<Chantier> l = new LinkedList<>();

        for (String l_chantier_to1 : l_chantier_to) {
            Chantier chantier = chantierDAO.findById(l_chantier_to1);
            l.add(chantier);
        }

        citerne.setL_chantiers(l);

        citerneDAO.save_citerne(citerne);

    }

    @Override
    public void update_citerne(Citerne citerne) {
        this.citerneDAO.update_citerne(citerne);
    }

    @Override
    public void save_bon_caisse_citerne_engin(Bon_Livraison_Citerne bon_Livraison_Citerne, String en) {

        Engin engin = enginDAO.findOneByCode(en);

        bon_Livraison_Citerne.setEngin(engin);

        citerneDAO.save_bon_caisse_citerne(bon_Livraison_Citerne);

    }

    @Override
    public void save_bon_caisse_citerne_mensuel(Bon_Livraison_Citerne bon_Livraison_Citerne, Integer men) {

        Mensuel mensuel = mensuelDAO.findById_integer(men);

        bon_Livraison_Citerne.setMensuel(mensuel);

        citerneDAO.save_bon_caisse_citerne(bon_Livraison_Citerne);
    }

    @Override
    public void delete(Citerne c) {

//        Bon_Livraison_Citerne bon_Livraison_Citerne = this.livraisonCiterneDAO.get_bon_livraison_by_Id(c.getId());
//
//        if (bon_Livraison_Citerne != null) {
//
//            if (bon_Livraison_Citerne.getEngin() != null) {
//                bon_Livraison_Citerne.setEngin(null);
//            }
//
//            if (bon_Livraison_Citerne.getMensuel() != null) {
//                bon_Livraison_Citerne.setMensuel(null);
//            }
//            if (bon_Livraison_Citerne.getEngin() == null || bon_Livraison_Citerne.getMensuel() == null) {
//                this.livraisonCiterneDAO.update(bon_Livraison_Citerne);
//            }
//            this.livraisonCiterneDAO.delete(bon_Livraison_Citerne);
//        }
        List<Chantier> l_chantiers = this.getListeChantierByCiterne(c.getId());

        if (l_chantiers != null) {
            for (Chantier ch_ : l_chantiers) {
                ch_.setDisplay_chantier_Principal(Boolean.TRUE);
            }
        }

        if (c.getChantier_Principal() != null) {

            c.getChantier_Principal().setDisplay_chantier_Principal(Boolean.TRUE);
        }

        c.setArchiver(Boolean.TRUE);

        this.citerneDAO.update_citerne(c);
    }

    @Override
    public void delete_chantierSec_citerne(Citerne citerne, Chantier chantier) {

        List<Chantier> l_chantiers = this.getListeChantierByCiterne(citerne.getId());

        l_chantiers.remove(chantier);

        citerne.setL_chantiers(l_chantiers);

        System.out.println("entre : " + l_chantiers.size());

        this.citerneDAO.update_citerne(citerne);
    }

    @Override
     public Bon_Livraison_Citerne lastAlimentationEngin(Engin e) {
        return citerneDAO.lastAlimentationEngin(e);
    }

    @Override
    public void add_chantierSec_citerne(Citerne citerne, String[] str) {

        List<Chantier> l_chan_s = this.getListeChantierByCiterne(citerne.getId());

        for (int i = 0; i < str.length; i++) {

            Chantier chantier = chantierDAO.findById(str[i]);

            l_chan_s.add(chantier);

        }

        citerne.setL_chantiers(l_chan_s);

        citerneDAO.merge_citerne(citerne);
    }

    @Override
    public void update_citerne_chantierSec(Citerne citerne, String[] str) {

        List<Chantier> l_chantiers = this.getListeChantierByCiterne(citerne.getId());

        for (int i = 0; i < str.length; i++) {

            Chantier chantier = chantierDAO.findById(str[i]);

            l_chantiers.add(chantier);

        }

        this.citerneDAO.update_citerne(citerne);
    }

    @Override
    public List<Chantier> getListeChantierByCiterne(Integer idCiterne) {

        List<Chantier> result = new ArrayList<>();
        List<Citerne> l_citernes = this.find_all_Citerne();
        for (Citerne l_citerne : l_citernes) {
            if (l_citerne.getId().equals(idCiterne)) {
                for (Chantier c : l_citerne.getL_chantiers()) {
                    result.add(c);
                }
            }
        }
        return result;

    }

    @Override
    public List<Citerne> find_allCiterneNon_archiver() {
        return this.citerneDAO.find_allCiterneNon_archiver();
    }

    @Override
    public List<Engin> getEnginByChantierId(int id) {

        return this.citerneDAO.getEnginByChantierId(id);
    }

    @Override
    public List<Chantier> getChantierSecandaireByIdCiterne(int id) {

        List<Chantier> results = new ArrayList<>();

        List<Chantier> l_chantiers = this.chantierDAO.findAll();

        if (l_chantiers != null) {
            if (!l_chantiers.isEmpty()) {
                for (Chantier ch_ : l_chantiers) {
                }
            }
        }

        return results;

    }

    @Override
    public void addTraceCiterne(TraceCiterne t) {
        citerneDAO.addTraceCiterne(t);
    }

    @Override
    public void editTraceCiterne(TraceCiterne t) {
        citerneDAO.editTraceCiterne(t);
    }

    @Override
    public void remouvTraceCiterne(TraceCiterne t) {
        citerneDAO.remouvTraceCiterne(t);
    }

    @Override
    public TraceCiterne findTraceCiterneById(int id) {
        return citerneDAO.findTraceCiterneById(id);
    }

    @Override
    public List<TraceCiterne> findAllTraceCiterne() {
        return citerneDAO.findAllTraceCiterne();
    }

    @Override
    public List<TraceCiterne> findAllTraceCiterneByCiterne(Citerne c) {
        return citerneDAO.findAllTraceCiterneByCiterne(c);
    }

    @Override
    public List<TraceCiterne> findAllTraceCiterneByCiterneDist(Citerne c) {
        return citerneDAO.findAllTraceCiterneByCiterneDist(c);
    }


}
