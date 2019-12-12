/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.Historique_CG;
import ma.bservices.tgcc.Entity.Historique_LoyerC;
import ma.bservices.tgcc.Entity.Historique_LoyerS;
import ma.bservices.tgcc.Entity.Historique_Modem3G;
import ma.bservices.tgcc.Entity.Historique_Ordinateur;
import ma.bservices.tgcc.Entity.Historique_Telephone;
import ma.bservices.tgcc.Entity.Historique_Voiture;
import ma.bservices.tgcc.Entity.Historique_VoitureChantier;
import ma.bservices.tgcc.Entity.LoyerChantier;
import ma.bservices.tgcc.Entity.LoyerSalarie;
import ma.bservices.tgcc.Entity.Telephone;
import ma.bservices.tgcc.Entity.Mensuel;
import ma.bservices.tgcc.Entity.Modem3G;
import ma.bservices.tgcc.Entity.Ordinateur;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.dao.Mensuel.CarteGasoilDAO;
import ma.bservices.tgcc.dao.Mensuel.HistoriqueCGDAO;
import ma.bservices.tgcc.dao.Mensuel.HistoriqueLoyerCDAO;
import ma.bservices.tgcc.dao.Mensuel.HistoriqueLoyerSDAO;
import ma.bservices.tgcc.dao.Mensuel.HistoriqueModemDAO;
import ma.bservices.tgcc.dao.Mensuel.HistoriqueOrdiDAO;
import ma.bservices.tgcc.dao.Mensuel.HistoriqueTelephoneDAO;
import ma.bservices.tgcc.dao.Mensuel.HistoriqueVoitureChDAO;
import ma.bservices.tgcc.dao.Mensuel.HistoriqueVoitureDAO;
import ma.bservices.tgcc.dao.Mensuel.TelephoneDAO;
import ma.bservices.tgcc.dao.Mensuel.MensuelDAO; 
import ma.bservices.tgcc.dao.Mensuel.VoitureDAO;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service("historiqueService")
public class HistoriqueServiceImpl implements Serializable, HistoriqueService {

    @Autowired
    private HistoriqueVoitureDAO hvDAO;

    @Autowired
    private HistoriqueVoitureChDAO hvchDAO;

    @Autowired
    private HistoriqueCGDAO hcgDAO;

    @Autowired
    private HistoriqueLoyerSDAO hlsDAO;

    @Autowired
    private HistoriqueLoyerCDAO hlcDAO;

    @Autowired
    private HistoriqueModemDAO hModemDAO;

    @Autowired
    private HistoriqueTelephoneDAO hTelephoneDAO;

    @Autowired
    private HistoriqueOrdiDAO hOrdiDAO;

    @Autowired
    private MensuelDAO mensuelDAO;

    @Autowired
    private ChantierDAO chantierDAO;

    @Autowired
    private VoitureDAO voitureDAO;

    @Autowired
    private CarteGasoilDAO carteDAO;

    @Autowired
    private TelephoneDAO loyerDAO;

    public HistoriqueVoitureDAO getHvDAO() {
        return hvDAO;
    }

    public void setHvDAO(HistoriqueVoitureDAO hvDAO) {
        this.hvDAO = hvDAO;
    }

    public MensuelDAO getMensuelDAO() {
        return mensuelDAO;
    }

    public void setMensuelDAO(MensuelDAO mensuelDAO) {
        this.mensuelDAO = mensuelDAO;
    }

    public HistoriqueLoyerSDAO getHlsDAO() {
        return hlsDAO;
    }

    public void setHlsDAO(HistoriqueLoyerSDAO hlsDAO) {
        this.hlsDAO = hlsDAO;
    }

    public HistoriqueLoyerCDAO getHlcDAO() {
        return hlcDAO;
    }

    public void setHlcDAO(HistoriqueLoyerCDAO hlcDAO) {
        this.hlcDAO = hlcDAO;
    }

    public TelephoneDAO getLoyerDAO() {
        return loyerDAO;
    }

    public void setLoyerDAO(TelephoneDAO loyerDAO) {
        this.loyerDAO = loyerDAO;
    }

    public HistoriqueOrdiDAO gethOrdiDAO() {
        return hOrdiDAO;
    }


    public void sethOrdiDAO(HistoriqueOrdiDAO hOrdiDAO) {
        this.hOrdiDAO = hOrdiDAO;
    }

    public HistoriqueVoitureChDAO getHvchDAO() {
        return hvchDAO;
    }

    public void setHvchDAO(HistoriqueVoitureChDAO hvchDAO) {
        this.hvchDAO = hvchDAO;
    }

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public void setChantierDAO(ChantierDAO chantierDAO) {
        this.chantierDAO = chantierDAO;
    }

    public VoitureDAO getVoitureDAO() {
        return voitureDAO;
    }

    public void setVoitureDAO(VoitureDAO voitureDAO) {
        this.voitureDAO = voitureDAO;
    }

    public HistoriqueCGDAO getHcgDAO() {
        return hcgDAO;
    }

    public void setHcgDAO(HistoriqueCGDAO hcgDAO) {
        this.hcgDAO = hcgDAO;
    }

    public HistoriqueTelephoneDAO gethTelephoneDAO() {
        return hTelephoneDAO;
    }

    public void sethTelephoneDAO(HistoriqueTelephoneDAO hTelephoneDAO) {
        this.hTelephoneDAO = hTelephoneDAO;
    }

    public HistoriqueModemDAO gethModemDAO() {
        return hModemDAO;
    }

    public void sethModemDAO(HistoriqueModemDAO hModemDAO) {
        this.hModemDAO = hModemDAO;
    }

    public CarteGasoilDAO getCarteDAO() {
        return carteDAO;
    }

    public void setCarteDAO(CarteGasoilDAO carteDAO) {
        this.carteDAO = carteDAO;
    }

    public TelephoneDAO getTelephoneDAO() {
        return loyerDAO;
    }

    public void setTelephoneDAO(TelephoneDAO loyerDAO) {
        this.loyerDAO = loyerDAO;
    }

    @Override
    public List<Historique_Voiture> findAllVoituresSalarie() {
        return hvDAO.findAll();
    }

    @Override
    public void addRecordVoitureSalarie(Historique_Voiture record, Long id_voiture, int id_mensuel, Date dateAff, Date dateDesaff) {
        record.setDateAffectationVoiture(dateAff);
        record.setDateDesaffectationVoiture(dateDesaff);
        Voiture voiture = voitureDAO.getVoitureByID(id_voiture);
        Mensuel mensuel = mensuelDAO.findById_integer(id_mensuel);

        record.setMensuel(mensuel);
        record.setVoiture(voiture);

        hvDAO.addRecordVoitureHistorique(record);

        System.out.println("============== RECORD ADDED ====== HVS ======= ");

    }

    @Override
    public List<Historique_VoitureChantier> findAllVoituresChantier() {
        return hvchDAO.findAll();
    }

    @Override
    public void addRecordVoitureChantier(Historique_VoitureChantier record, Voiture voiture, int id_chantier, Date dateAff, Date dateDesaff) {
        record.setDateAffectation(dateAff);
        record.setDateDesaffectation(dateDesaff);
        Chantier chantier = chantierDAO.findById(id_chantier);

        record.setChantier(chantier);
        record.setVoitureChantier(voiture);

        hvchDAO.addRecordVoitureChHistorique(record);

        System.out.println("============== RECORD ADDED ====== HVCH ======= ");

    }

    @Override
    public List<Historique_CG> findAllCG() {
        return hcgDAO.findAll();
    }
/*
    @Override
    public List<Historique_CGC> findAllCGC() {
        return hcgcDAO.findAll();
    }

    @Override
    public void addRecordCG(Historique_CG record, CarteGasoilSalarie carte, Mensuel mensuel, Date dateAff, Date dateDesaff) {

        record.setDateAffectation(dateAff);
        record.setDateDesaffectation(dateDesaff);

        record.setMensuel(mensuel);
        record.setCarteGasoil(carte);
        hcgDAO.addrecord(record);

        System.out.println("============== RECORD ADDED ====== HCG ======= ");

    }

    @Override
    public void addRecordCGC(Historique_CGC record, CarteGasoilChantier carte, Chantier chantier, Date dateAff, Date dateDesaff) {

        record.setDateAffectation(dateAff);
        record.setDateDesaffectation(dateDesaff);

        record.setChantier(chantier);
        record.setCarteGasoil(carte);
        hcgcDAO.addrecord(record);

        System.out.println("============== RECORD ADDED ====== HCGC ======= ");

    }
*/
    @Override
    public List<Historique_Telephone> findAllTels() {
        return hTelephoneDAO.findAll();
    }

    @Override
    public void addRecordTelephone(Historique_Telephone record, Telephone tel, Mensuel mensuel, Date dateAff, Date dateDesaff) {

        record.setDateAffectation(dateAff);
        record.setDateDesaffectation(dateDesaff);

        record.setMensuel(mensuel);
        record.setTelephone(tel);
        hTelephoneDAO.addrecord(record);

        System.out.println("============== RECORD ADDED ====== HTELEPHONE ======= ");

    }

    @Override
    public List<Historique_Ordinateur> findAllOrdis() {
        return hOrdiDAO.findAll();
    }

    @Override
    public void addRecordOrdinateur(Historique_Ordinateur record, Ordinateur ordi, Mensuel mensuel, Date dateAff, Date dateDesaff) {

        record.setDateAffectation(dateAff);
        record.setDateDesaffectation(dateDesaff);

        record.setMensuel(mensuel);
        record.setOrdinateur(ordi);
        hOrdiDAO.addrecord(record);

        System.out.println("============== RECORD ADDED ====== HORDINATEUR ======= ");

    }

    @Override
    public void addRecordLoyerC(Historique_LoyerC record, Chantier chantier, LoyerChantier loyerC, Date dateAff, Date dateDesaff) {

        record.setDateAffectation(dateAff);
        record.setDateDesaffectation(dateDesaff);

        record.setChantier(chantier);
        record.setLoyerC(loyerC);
        hlcDAO.addrecord(record);

        System.out.println("============== RECORD ADDED ====== HLOYERC ======= ");

    }

    @Override
    public void addRecordLoyerS(Historique_LoyerS record, Mensuel mensuel, LoyerSalarie loyerS, Date dateAff, Date dateDesaff) {

        record.setDateAffectation(dateAff);
        record.setDateDesaffectation(dateDesaff);

        record.setMensuel(mensuel);
        record.setLoyerS(loyerS);
        hlsDAO.addrecord(record);

        System.out.println("============== RECORD ADDED ====== HLOYERS ======= ");

    }

    @Override
    public List<Historique_Modem3G> findAllModems() {
        return hModemDAO.findAll();
    }

    @Override
    public List<Historique_LoyerS> findAllLoyerS() {
        return hlsDAO.findAll();
    }

    @Override
    public List<Historique_LoyerC> findAllLoyerC() {
        return hlcDAO.findAll();
    }

    @Override
    public void addRecordModem(Historique_Modem3G record, Modem3G modem, Mensuel mensuel, Date dateAff, Date dateDesaff) {

        record.setDateAffectation(dateAff);
        record.setDateDesaffectation(dateDesaff);

        record.setMensuel(mensuel);
        record.setModem(modem);
        hModemDAO.addrecord(record);

        System.out.println("============== RECORD ADDED ====== HMODEM ======= ");

    }

    @Override
    public List<Historique_Telephone> findByDateRange(Date from, Date to) {
        return hTelephoneDAO.findByDateRange(from, to);
    }

    @Override
    public List<Historique_Ordinateur> findByDateRangeOrdi(Date from, Date to) {
        return hOrdiDAO.findByDateRange(from, to);
    }

    @Override
    public List<Historique_Modem3G> findByDateRange3G(Date from, Date to) {
        return hModemDAO.findByDateRange(from, to);
    }

    @Override
    public List<Historique_Voiture> findByDateRangeVS(Date from, Date to) {
        return hvDAO.findByDateRange(from, to);
    }

    @Override
    public List<Historique_VoitureChantier> findByDateRangeVC(Date from, Date to) {
        return hvchDAO.findByDateRange(from, to);
    }

    @Override
    public List<Historique_CG> findByDateRangeCG(Date from, Date to) {
        return hcgDAO.findByDateRange(from, to);
    }
/*
    @Override
    public List<Historique_CGC> findByDateRangeCGC(Date from, Date to) {
        return hcgcDAO.findByDateRange(from, to);
    }
*/
    @Override
    public List<Historique_LoyerS> findByDateRangeLS(Date from, Date to) {
        return hlsDAO.findByDateRange(from, to);
    }

    @Override
    public List<Historique_LoyerC> findByDateRangeLC(Date from, Date to) {
        return hlcDAO.findByDateRange(from, to);
    }

}
