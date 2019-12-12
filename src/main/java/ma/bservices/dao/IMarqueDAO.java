/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.Marque;

/**
 *
 * @author yassine.jeddi
 */
public interface IMarqueDAO {
    
    public void addMarque(Marque m);
    public List<Marque> allMarque();
    public List<Marque> allMarqueByType(String type);
}
