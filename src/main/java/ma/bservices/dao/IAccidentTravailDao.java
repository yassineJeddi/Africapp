/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.AccidentTravail;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.Salarie;

/**
 *
 * @author yassine
 */
public interface IAccidentTravailDao {
    
    public void addAccidentTravail(AccidentTravail a);
    public void editAccidentTravail(AccidentTravail a);
    public void remouveAccidentTravail(AccidentTravail a);
    public List<AccidentTravail> allAccidentTravail();
    public AccidentTravail allAccidentTravailById(Integer id);
    public List<AccidentTravail> allAccidentTravailByChantier(Chantier c);
    public List<AccidentTravail> allAccidentTravailBySalarie(Salarie s);
    
    
}
