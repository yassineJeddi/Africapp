/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.engin;

import java.util.List;
import ma.bservices.tgcc.Entity.DocumentEngin;

/**
 *
 * @author a.wattah
 */
public interface DocumentEnginDAO {

    public void save(DocumentEngin documentEngin);

    public List<DocumentEngin> getListDocumentEngins(int id);

    public void delete(DocumentEngin documentEngin);

    public void update(DocumentEngin documentEngin);




}
