/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.PointageLot;
import ma.bservices.beans.Salarie;
import ma.bservices.dao.PointageLotDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mahdi
 */
@Service("pointageLotService")
public class PointageLotServiceImpl implements Serializable, PointageLotService {

    @Autowired
    PointageLotDao pointageLotDao;
    protected static Logger logger = Logger.getLogger("service");

    @Override
    public List<PointageLot> loadAll() {
        try {
            return pointageLotDao.getAll();
        } catch (Exception ex) {
            logger.debug("erreur " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<PointageLot> recherche(Date datepointage, Salarie s, Chantier c) {   
       return pointageLotDao.getListPointageParLotBySalarie(datepointage, s, c);
    }

    @Override
    public boolean pointage(PointageLot p) {
        logger.debug("pointage ajouter");
        return (p != null) ? pointageLotDao.pointer(p) : false;
    }

    @Override
    public boolean updatePointage(PointageLot p) {
        logger.debug("pointage modifier");
        return (p != null) ? pointageLotDao.updatePointage(p) : false;
    }

    public PointageLotDao getPointageLotDao() {
        return pointageLotDao;
    }

    public void setPointageLotDao(PointageLotDao pointageLotDao) {
        this.pointageLotDao = pointageLotDao;
    }

    @Override
    public Map<String, Boolean> weekRecap(Integer year, Integer week) {
        System.out.println("ggggggggg TEST WEEK REACAP");
        Date date_TOday = new Date();
        Map<String, Boolean> days_of_week = new HashMap<>();
        DateFormat days = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE);
        DateFormat days_letters = new SimpleDateFormat("EEEE", Locale.FRANCE);
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.YEAR, year);
        int day_number = 0;
        do {
            days_of_week.put(days_letters.format(cal.getTime()) + ", " + days.format(cal.getTime()), verifierPointage(cal.getTime()));
            cal.add(Calendar.DATE, 1);
            day_number++;
        } while (day_number < 7 && (cal.getTime().compareTo(date_TOday) <= 0));
        return days_of_week;
    }

    @Override
    public boolean verifierPointage(Date d) {
        return !recherche(d, null, null).isEmpty();
    }

    @Override
    public PointageLot getLastPointage(Chantier chantier, Salarie chef) {
        return pointageLotDao.getLastPointage(chantier, chef);
    }

}
