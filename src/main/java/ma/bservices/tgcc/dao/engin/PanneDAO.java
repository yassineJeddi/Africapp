/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import ma.bservices.tgcc.Entity.Engin;
import ma.bservices.tgcc.Entity.Panne;

/**
 *
 * @author j.allali
 */
public interface PanneDAO {

    public void add(Panne panne);

    public void update(Panne panne);
}
