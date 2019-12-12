/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;
import ma.bservices.tgcc.Entity.Citerne;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.TraceCiterne;

/**
 *
 * @author a.wattah
 */
public interface CiterneDAO {

    public Citerne findCiternById(int id);
    public List<Citerne> find_all_Citerne();
    public List<Citerne> find_allCiterneNon_archiver();
    public void save_citerne(Citerne citerne);
    public void update_citerne(Citerne citerne);
    public void save_bon_caisse_citerne(Bon_Livraison_Citerne bon_Livraison_Citerne);
    public void delete(Citerne citerne);
    public List<Engin> getEnginByChantierId(int id);

    /**
     * methode c pour add chantier sec a citerne
     *
     * @param citerne
     */
    public void merge_citerne(Citerne citerne);
    
     /**
     * methodes  pour gestion du traceciterne
     *
     */
    
    public void addTraceCiterne(TraceCiterne t);
    public void editTraceCiterne(TraceCiterne t);
    public void remouvTraceCiterne(TraceCiterne t);
    public TraceCiterne findTraceCiterneById(int id);
    public List<TraceCiterne> findAllTraceCiterne();
    public List<TraceCiterne> findAllTraceCiterneByCiterne(Citerne c);
    public List<TraceCiterne> findAllTraceCiterneByCiterneDist(Citerne c);

}
