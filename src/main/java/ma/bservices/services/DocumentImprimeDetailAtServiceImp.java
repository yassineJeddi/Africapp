/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.DocumentImprimeDetailAt;
import ma.bservices.dao.IDocumentImprimeDetailAtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("documentImprimeDetailATService")
public class DocumentImprimeDetailAtServiceImp   implements Serializable,IDocumentImprimeDetailAtService{

    @Autowired
    IDocumentImprimeDetailAtDao documentImprimeDetailATDao;

    public IDocumentImprimeDetailAtDao getDocumentImprimeDetailATDao() {
        return documentImprimeDetailATDao;
    }

    public void setDocumentImprimeDetailATDao(IDocumentImprimeDetailAtDao documentImprimeDetailATDao) {
        this.documentImprimeDetailATDao = documentImprimeDetailATDao;
    }

    @Override
    public void addDocumentDetailAt(DocumentImprimeDetailAt d) {
        documentImprimeDetailATDao.addDocumentDetailAt(d);
    }

    @Override
    public void editDocumentDetailAt(DocumentImprimeDetailAt d) {
        documentImprimeDetailATDao.editDocumentDetailAt(d);
    }

    @Override
    public void remouveDocumentDetailAt(DocumentImprimeDetailAt d) {
        documentImprimeDetailATDao.remouveDocumentDetailAt(d);
    }

    @Override
    public DocumentImprimeDetailAt infoDocumentDetailAtById(Long id) {
        return documentImprimeDetailATDao.infoDocumentDetailAtById(id);
    }

    @Override
    public List<DocumentImprimeDetailAt> allDocumentDetailAt() {
        return documentImprimeDetailATDao.allDocumentDetailAt();
    }

    @Override
    public List<DocumentImprimeDetailAt> allDocumentDetailAtByIdDetailAt(Long id) {
        return documentImprimeDetailATDao.allDocumentDetailAtByIdDetailAt(id);
    }
    
}
