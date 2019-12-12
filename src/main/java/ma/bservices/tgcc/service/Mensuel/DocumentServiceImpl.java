/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Document;
import ma.bservices.tgcc.Entity.Voiture;
import ma.bservices.tgcc.dao.Mensuel.DocumentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService, Serializable {

    @Autowired
    private DocumentDAO documentDAO;

    public DocumentDAO getDocumentDAO() {
        return documentDAO;
    }

    public void setDocumentDAO(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    @Override
    public List<Document> findAll() {
        return this.documentDAO.findAll();
    }

    @Override
    public Boolean save(Document document) {

        return this.documentDAO.save(document);

    }

    @Override
    public Boolean deleteDocument(Document a) {

        return this.documentDAO.deleteDocument(a);
    }

    @Override
    public Boolean deleteVoiture(Voiture a) {
        return this.documentDAO.deleteVoiture(a);
    }

    @Override
    public List<Document> getDocumentByVoiture(Long id) {
        return this.documentDAO.getDocumentByVoiture(id);
    }

    @Override
    public List<Document> getDocumentByLoyer(Long id) {
        return this.documentDAO.getDocumentByLoyer(id);
    }

    @Override
    public List<Document> getDocumentByCarteGZ(Long id){
        return this.documentDAO.getDocumentByCarteGZ(id);
    }
    @Override
    public List<Document> getListDocumentById(Integer id) {
        return documentDAO.getListDocumentById(id);
    }

    @Override
    public void update(Document document) {
        documentDAO.update(document);
    }
       @Override
    public List<Document> findAllNoLocation() {
        return this.documentDAO.findAllNoLocation();
    }

}
