/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.List;
import ma.bservices.beans.Document;
import ma.bservices.tgcc.Entity.Voiture;

/**
 *
 * @author a.wattah
 */
public interface DocumentDAO {

    public List<Document> findAll();

    public Boolean save(Document document);

    public Boolean deleteDocument(Document a);

    public Boolean deleteVoiture(Voiture a);

    public List<Document> getDocumentByLoyer(Long id);
    public List<Document> getDocumentByVoiture(Long id);
    public List<Document> getDocumentByCarteGZ(Long id);
    public List<Document> getListDocumentById(Integer id);

    public void update(Document document);
    
    public List<Document> findAllNoLocation();

}
