/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.DetailAT;

/**
 *
 * @author yassine
 */
public interface IDetailATDao {
    
    public void addDetailAT(DetailAT d);
    public void editDetailAT(DetailAT d);
    public void remouveDetailAT(DetailAT d);
    public List<DetailAT> allDetailAT();
    public List<DetailAT> allDetailATByAtId(Long id);
    public DetailAT detailATById(Long id);
    
}
