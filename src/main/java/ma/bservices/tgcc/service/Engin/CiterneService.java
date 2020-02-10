/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Bon_Livraison_Citerne;
import ma.bservices.tgcc.Entity.Citerne;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.TraceCiterne;

/**
 *
 * @author a.wattah
 */
public interface CiterneService {

    public Citerne findCiternById(int id);
    public List<Citerne> find_all_Citerne();
    public List<Citerne> find_allCiterneNon_archiver();
    public void save_citerne(Citerne citern, String[] l_chantier_to);
    public void update_citerne(Citerne citerne);
    public void update_citerne_chantierSec(Citerne citerne, String[] str);
    public void save_bon_caisse_citerne_engin(Bon_Livraison_Citerne bon_Livraison_Citerne, String engin);
    public void save_bon_caisse_citerne_mensuel(Bon_Livraison_Citerne bon_Livraison_Citerne, Integer mensuel);
    public void delete(Citerne c);
    public void delete_chantierSec_citerne(Citerne citerne, Chantier chantier);
    public void add_chantierSec_citerne(Citerne citerne, String[] str);
    public Bon_Livraison_Citerne lastAlimentationEngin(Engin e);
    public List<Chantier> getListeChantierByCiterne(Integer idCiterne);
    public List<Engin> getEnginByChantierId(int id);
    public List<Chantier> getChantierSecandaireByIdCiterne(int id);
    
    
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
