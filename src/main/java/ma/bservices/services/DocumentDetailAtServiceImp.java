/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.DocumentDetailAt;
import ma.bservices.dao.IDocumentDetailAtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("documentDetailATService")
public class DocumentDetailAtServiceImp   implements Serializable,IDocumentDetailAtService {

    
    @Autowired
    IDocumentDetailAtDao documentDetailATDao;

    public IDocumentDetailAtDao getDocumentDetailATDao() {
        return documentDetailATDao;
    }

    public void setDocumentDetailATDao(IDocumentDetailAtDao documentDetailATDao) {
        this.documentDetailATDao = documentDetailATDao;
    }
    
    
    
    @Override
    public void addDocumentDetailAt(DocumentDetailAt d) {
        documentDetailATDao.addDocumentDetailAt(d);
    }

    @Override
    public void editDocumentDetailAt(DocumentDetailAt d) {
        documentDetailATDao.editDocumentDetailAt(d);
    }

    @Override
    public void remouveDocumentDetailAt(DocumentDetailAt d) {
        documentDetailATDao.remouveDocumentDetailAt(d);
    }

    @Override
    public DocumentDetailAt infoDocumentDetailAtById(Long id) {
        return documentDetailATDao.infoDocumentDetailAtById(id);
    }

    @Override
    public List<DocumentDetailAt> allDocumentDetailAt() {
        return documentDetailATDao.allDocumentDetailAt();
    }

    @Override
    public List<DocumentDetailAt> allDocumentDetailAtByIdDetailAt(Long id) {
        return documentDetailATDao.allDocumentDetailAtByIdDetailAt(id);
    }
    
}
