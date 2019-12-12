/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.List;
import ma.bservices.tgcc.Entity.Element;

/**
 *
 * @author a.wattah
 */
public interface ElementDAO {
    
    
    public void update(Element element);
    public List<Element> getElementByIdMensuel(int id);
    public void delete(Element element);
    public void save(Element element);
    
}
