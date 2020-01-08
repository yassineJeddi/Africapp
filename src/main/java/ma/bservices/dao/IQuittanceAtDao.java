/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.AccidentTravail;
import ma.bservices.beans.QuittanceAt;

/**
 *
 * @author yassine
 */
public interface IQuittanceAtDao {
    
    public void addQuittanceAt(QuittanceAt d);
    public void editQuittanceAt(QuittanceAt d);
    public void remouvQuittanceAt(QuittanceAt d);
    public QuittanceAt quittanceAtById(Integer id);
    public List<QuittanceAt> allQuittanceAtByAt(AccidentTravail a);
    
}
