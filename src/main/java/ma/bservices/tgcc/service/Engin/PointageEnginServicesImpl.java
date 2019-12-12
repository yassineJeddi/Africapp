/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma.bservice.tgcc.Constante.Constante;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Lot;
import ma.bservices.beans.Utilisateur;
import ma.bservices.beans.Zone;
import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.EtatEngin;
import ma.bservices.tgcc.Entity.Panne;
import ma.bservices.tgcc.Entity.PointageEngin;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import ma.bservices.tgcc.dao.engin.EnginDAO;
import ma.bservices.tgcc.dao.engin.EtatEnginDAO;
import ma.bservices.tgcc.dao.engin.LotDAO;
import ma.bservices.tgcc.dao.engin.PointageEnginDAO;
import ma.bservices.tgcc.dao.engin.UtilisateurDAO;
import ma.bservices.tgcc.dao.engin.ZoneDAO;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("pointageEnginService")
public class PointageEnginServicesImpl implements PointageEnginServices, Serializable {

    @Autowired
    private PointageEnginDAO pointageEnginDAO;

    @Autowired
    private LotDAO lotDAO;

    @Autowired
    private ChantierDAO chantierDAO;

    @Autowired
    private ZoneDAO ZoneDAO;

    @Autowired
    private EtatEnginDAO etatEnginDAO;

    @Autowired
    private EnginDAO enginDAO;

    @Autowired
    private UtilisateurDAO utilisateurDAO;

    private EnginService enginService;

    // methodes
    @Override
    public List<PointageEngin> findAll() {
        return pointageEnginDAO.findAll();
    }

    @Override
    public List<PointageEngin> RechercheEngin(String code, String Designation, String marque, Date date_from, Date date_to, int chantier) {
        return pointageEnginDAO.RechercheEngin(code, Designation, marque, date_from, date_to, chantier);
    }
    
    @Override
    public void saveListPointage(List<PointageEngin> pe) {
        pointageEnginDAO.saveListPointage(pe);
    }

    /**
     * fait le pointage d'une liste de pointage d'engin
     *
     * @param pe
     * @param datePointage
     * @return
     */
    @Override
    public void pointageEnginAction(List<PointageEngin> pe, Date datePointage) {

        Chantier ch_DEPOT = chantierDAO.get_depot();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (int i = 0; i < pe.size(); i++) {

            pe.get(i).setDateCreation(datePointage);

            //ADD Chantier AFFINITE
            if (pe.get(i).getIdChantierAffinite() != null) {
                Chantier CA = chantierDAO.findById(pe.get(i).getIdChantierAffinite());
                pe.get(i).setChantierAffinite(CA);
            }

            //il faut verifier si le lot a été selectionné
            //String lotLabel = pe.get(i).getIdLot().getLibelle();
            //Lot lot_to_add = lotDAO.findOneByLabel(lotLabel);

            //pe.get(i).setIdLot(lot_to_add);

            //ajouter les zones.
            //String[] array_zones = pe.get(i).getZones_str();

            //SET ETAT FOR POINTAGE_ENGIN
            System.out.println("--------_________ ETAT ENGIN _______-------" + pe.get(i).getIDEngin().getEtat());
            EtatEngin etat = etatEnginDAO.findOneByLabel(pe.get(i).getIDEngin().getEtat());

            if (etat instanceof EtatEngin) {
                pe.get(i).setIdEtat(etat);
                pe.get(i).getIDEngin().setEtat(etat.getLibelleEtat());
            }
          /**  if (array_zones != null) {
                for (int j = 0; j < array_zones.length; j++) {
                    Zone zone_to_add = ZoneDAO.findById(Integer.parseInt(array_zones[j]));
                    pe.get(i).getZoneList().add(zone_to_add);
                }
            } else {
                System.out.println("RETURNING....");
      
            }
*/
            //Save Pointer Par
            Utilisateur u = utilisateurDAO.UserByLogin(auth.getPrincipal().toString());
            pe.get(i).setUser(u);

//            enginDAO.ajouterEngin(pe.get(i).getIDEngin());
            System.out.println("SAVING...");
            pointageEnginDAO.save(pe.get(i));

            //on gère le transfert de l'engin en cas de mise en transfert ou mise en panne
            if (ch_DEPOT != null) {

                pe.get(i).getIDEngin().setModifierPar(auth.getPrincipal().toString());
                enginDAO.ajouterEngin(pe.get(i).getIDEngin());

                if (pe.get(i).getIDEngin().getEtatTransfert() == Boolean.TRUE) {
                    pe.get(i).getIDEngin().setPrjapId(ch_DEPOT);
                    enginDAO.ajouterEngin(pe.get(i).getIDEngin());
                }

                if (pe.get(i).getIDEngin().getEtat().compareTo(Constante.CODE_ETAT_ENGIN_PANNE) == 0) {

                    Panne panne = new Panne();

                    panne.setCompteurHoraire(pe.get(i).getIDEngin().getComteurHoraire());
                    panne.setCompteurKilometrique(pe.get(i).getIDEngin().getCompteurKilometrique());
                    panne.setDate(datePointage);
                    panne.setEngin(pe.get(i).getIDEngin());

                    enginService.addEnginPanne(panne);

//                    pe.get(i).getIDEngin().setPrjapId(ch_DEPOT);
//                    enginDAO.ajouterEngin(pe.get(i).getIDEngin());
                }

            }

        }
       
    }

    /**
     * retourne la date du dernier pointage au niveau d'un engin On peut pointer
     * les engins d'un chantier tant qu'on n'est pas arriver à la date de son
     * dernier pointage
     *
     * @param chantier_id
     * @return
     */
    @Override
    public Date lastDayPointed(int chantier_id) {
        PointageEngin p = new  PointageEngin();
        try {
           
        p = pointageEnginDAO.getLastPointageEnginByChantierId(chantier_id);

        //DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        if (last_pe instanceof PointageEngin) {
//            return last_pe.getDateCreation();
//            Date last_pe_date = last_pe.getDateCreation();
//            try {
//                Date last_pe_date = date;
//                return last_pe_date;
//            } catch (ParseException ex) {
//                System.out.println("parsin date Erreur ");
//                Logger.getLogger(PointageEnginServicesImpl.class.getName()).log(Level.SEVERE, null, ex);
//                return null;
//            }
//        } 
        } catch (Exception e) {
            System.out.println("Erreur lastDayPointed car "+e.getMessage());
        }
        return p.getDateCreation();
    }

    @Override
    public Boolean havePointed(int chantier_id, Date day) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date_format = df.format(day);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);

        String date_tomorrow = df.format(cal.getTime());
        
        

        List<PointageEngin> l = pointageEnginDAO.getPointageEnginByDateAndChantierId(chantier_id, date_format, date_tomorrow);

        
        if (l != null && l.size() > 0) {
           
            return true;
        }
        return false;
    }

    @Override
    public void updateEtat(Engin peUpdate) {
        this.pointageEnginDAO.updateEtat(peUpdate);
    }

    @Override
    public List<Zone> findAllZones(Integer id_pointage) {
        return this.pointageEnginDAO.getZoneList(id_pointage);
    }

    @Override
    public List<Engin> findAllPanne() {
        return this.pointageEnginDAO.findAllPanne();
    }

    /**
     * **************************************************************************************************************
     ****************************************************************************************************************
     * GETTERS AND SETTERS
     * ******************************************************************************************
     * ****************************************************************************************************************
     * ***************************************************************************************************************
     */
    @Override
    public List<Engin> getCode(String code) {
        return pointageEnginDAO.getCode(code);
    }

    @Override
    public void addEnginPanne(Engin poiE) {
        pointageEnginDAO.addEnginPanne(poiE);
    }

    @Override
    public Engin addEngin(String code) {
        return pointageEnginDAO.addEngin(code);
    }

    @Override
    public Chantier addChantier(String chantier) {
        return pointageEnginDAO.addChantier(chantier);

    }

    public UtilisateurDAO getUtilisateurDAO() {
        return utilisateurDAO;
    }

    public void setUtilisateurDAO(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    public EnginDAO getEnginDAO() {
        return enginDAO;
    }

    public void setEnginDAO(EnginDAO enginDAO) {
        this.enginDAO = enginDAO;
    }

    public PointageEnginDAO getPointageEnginDAO() {
        return pointageEnginDAO;
    }

    public void setPointageEnginDAO(PointageEnginDAO pointageEnginDAO) {
        this.pointageEnginDAO = pointageEnginDAO;
    }

    public LotDAO getLotDAO() {
        return lotDAO;
    }

    public void setLotDAO(LotDAO lotDAO) {
        this.lotDAO = lotDAO;
    }

    public ZoneDAO getZoneDAO() {
        return ZoneDAO;
    }

    public EtatEnginDAO getEtatEnginDAO() {
        return etatEnginDAO;
    }

    public void setEtatEnginDAO(EtatEnginDAO etatEnginDAO) {
        this.etatEnginDAO = etatEnginDAO;
    }

    public void setZoneDAO(ZoneDAO ZoneDAO) {
        this.ZoneDAO = ZoneDAO;
    }

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public EnginService getEnginService() {
        return enginService;
    }

    public void setEnginService(EnginService enginService) {
        this.enginService = enginService;
    }

    @Override
    public List<PointageEngin> getPointageEnginByDate_Diff_Depo(int idEngin) {
        return pointageEnginDAO.getPointageEnginByDate_Diff_Depo(idEngin);
    }

    @Override
    public void addEngin_PAnne(Engin peUpdate) {
        peUpdate.setDateCREATION(new Date());
        peUpdate.setEtat("panne");
        pointageEnginDAO.updateEtat(peUpdate);
    }

    @Override
    public PointageEngin lastPointageEngin(Integer idChantier, Integer idEngin) {
        return pointageEnginDAO.lastPointageEngin(idChantier, idEngin);
    }
}
