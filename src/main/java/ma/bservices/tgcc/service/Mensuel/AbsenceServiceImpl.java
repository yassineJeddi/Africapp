/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.Calendar;
import static java.util.Calendar.HOUR_OF_DAY;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import ma.bservices.tgcc.Entity.AbsenceMensuel;
import ma.bservices.tgcc.Entity.Pointage;
import ma.bservices.tgcc.Entity.PointageMensuelQuinzinier;
import ma.bservices.tgcc.dao.Mensuel.AbsenceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
//@Transactional("absenceService")
@Service("absenceService")
public class AbsenceServiceImpl implements AbsenceService, Serializable {

    @Autowired
    private AbsenceDAO absenceDAO;

    public AbsenceDAO getAbsenceDAO() {
        return absenceDAO;
    }

    public void setAbsenceDAO(AbsenceDAO absenceDAO) {
        this.absenceDAO = absenceDAO;
    }

    @Override
    public List<AbsenceMensuel> getAbsenceById(int id) {
        return absenceDAO.getAbsenceById(id);
    }

    @Override
    public List<AbsenceMensuel> getMensuelByDate(Date date) {
        return absenceDAO.getMensuelByDate(date);
    }

    @Override
    public List<PointageMensuelQuinzinier> getAbsenceMensuelQById(int id) {
        return absenceDAO.getAbsenceMensuelQById(id);
    }

    @Override
    public void updatePointageMQ(AbsenceMensuel pointageMQ) {
        absenceDAO.updatePointageMQ(pointageMQ);
    }

    @Override
    public List<AbsenceMensuel> getAbsenceById_toCheck(int id_mensuel) {
        return absenceDAO.getAbsenceById_tocheck(id_mensuel);
    }

    /**
     * rassemble les pointages considéré comme des absences et les rassemble
     * dans une liste pour ensuite en créer un objet Absense
     *
     * @param l_absence
     * @param _p
     * @return
     */
    @Override
    public List<Pointage> AddAbsenceIntoList(List<Pointage> l_absence, Pointage _p) {

        if (l_absence == null) {
            l_absence = new LinkedList<>();
        }

        Boolean added = false;

        //les informations de l'abscence via le pointage
        AbsenceMensuel _a = new AbsenceMensuel();
        _a.setSalarie(_p.getMensuel());

        if (l_absence.isEmpty()) {
            l_absence.add(_p);
            added = true;
        } else {

            Pointage _last = l_absence.get(l_absence.size() - 1);
            if ((_last.getAutre().compareTo(_p.getAutre()) == 0)) {
                Calendar cal_last = Calendar.getInstance();
                cal_last.setTime(_last.getDay());

                Calendar cal_current = Calendar.getInstance();
                cal_current.setTime(_p.getDay());

                if ((_last.getCrenau().getId() < PointageService.CRANEAU_16_A_18)
                        && (_last.getCrenau().getId() == (_p.getCrenau().getId() - 1))
                        && (_p.getDay().equals(_last.getDay()))) {

                    l_absence.add(_p);
                    added = true;

                } else if ((_last.getCrenau().getId() == PointageService.CRANEAU_16_A_18)
                        && _p.getCrenau().getId() == 0) {

                    cal_last.add(Calendar.DATE, 1);
                    if (cal_last.getTime().equals(cal_current.getTime())) {
                        l_absence.add(_p);
                        added = true;
                    }
                }
            }
        }

        if (added == false) {
            addAbsense(l_absence);
            l_absence = new LinkedList<Pointage>();
            l_absence.add(_p);
        }

        return l_absence;
    }

    public void addAbsense(List<Pointage> l_p) {

        if ((l_p != null) && (!l_p.isEmpty())) {

            AbsenceMensuel _a = new AbsenceMensuel();

            if (l_p.size() == 1) {

                Pointage _first = l_p.get(0);

                Calendar cal_debut = Calendar.getInstance();
                cal_debut.setTime(_first.getDay());
                cal_debut.set(HOUR_OF_DAY, _first.getCrenau().getFrom());

                cal_debut.set(Calendar.MINUTE, 0);
                cal_debut.set(Calendar.SECOND, 0);

                _a.setDateDebut(cal_debut.getTime());

                cal_debut.set(HOUR_OF_DAY, _first.getCrenau().getTo());

                _a.setDateFin(cal_debut.getTime());
                _a.setTypeAbsence(_first.getAutre());
                _a.setSalarie(_first.getMensuel());
                _a.setCommentaire(_first.getAutreType());

                absenceDAO.save(_a);

            } else {

                Pointage _first = l_p.get(0);
                Pointage _last = l_p.get(l_p.size() - 1);

                Calendar cal_debut = Calendar.getInstance();
                Calendar cal_fin = Calendar.getInstance();

                cal_debut.setTime(_first.getDay());
                cal_fin.setTime(_last.getDay());

                cal_debut.set(HOUR_OF_DAY, _first.getCrenau().getFrom());
                cal_fin.set(HOUR_OF_DAY, _last.getCrenau().getTo());

                cal_fin.set(Calendar.MINUTE, 0);
                cal_fin.set(Calendar.SECOND, 0);

                cal_debut.set(Calendar.MINUTE, 0);
                cal_debut.set(Calendar.SECOND, 0);

                _a.setDateDebut(cal_debut.getTime());
                _a.setDateFin(cal_fin.getTime());
                _a.setTypeAbsence(_first.getAutre());
                _a.setSalarie(_first.getMensuel());
                _a.setCommentaire(_first.getAutreType());

                System.out.println("date deb : " + _a.getDateDebut().toString());
                System.out.println("date fin : " + _a.getDateFin().toString());
                absenceDAO.save(_a);

            }

        }

    }

    /**
     *
     * met à jour un objet abscence on le valide, on l'annule
     *
     * @param l_absences
     */
    @Override
    public void valider_cancel__Absence(List<AbsenceMensuel> l_absences) {

        for (int i = 0; i < l_absences.size(); i++) {

            if (l_absences.get(i).getChecked() != null && l_absences.get(i).getChecked() == 0) {
                l_absences.get(i).setChecked(null);
            }

            absenceDAO.save(l_absences.get(i));
        }

    }
    
    /**
     * enregistre une absence sans passer par le pointage déclaratif
     * @param _a 
     */
    
    public void add_absense(AbsenceMensuel _a){
        
        //si il n'y a aucune absence dans les même horaires
        
        absenceDAO.save(_a);
    }
    
    /**
     * return la liste des absences qui sont en chevauchement avec un nouvelle absence crée
     * @param absence
     * @return 
     */
    @Override
    public List<AbsenceMensuel> getAbsencesChevauchement(AbsenceMensuel absence) {
        return absenceDAO.getAbsencesChevauchement(absence);
    }

}
