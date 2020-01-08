/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.AccidentTravail;
import ma.bservices.beans.DocumentAt;
import ma.bservices.dao.IDocumentAtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("documentAtService")
public class DocumentAtServiceImp   implements Serializable,IDocumentAtService{

    @Autowired
    IDocumentAtDao documentAtDao;

    public IDocumentAtDao getDocumentAtDao() {
        return documentAtDao;
    }

    public void setDocumentAtDao(IDocumentAtDao documentAtDao) {
        this.documentAtDao = documentAtDao;
    }
    
    @Override
    public void addDocumentAt(DocumentAt d) {
        documentAtDao.addDocumentAt(d);
    }

    @Override
    public void editDocumentAt(DocumentAt d) {
        documentAtDao.editDocumentAt(d);
    }

    @Override
    public void remouvDocumentAt(DocumentAt d) {
        documentAtDao.remouvDocumentAt(d);
    }

    @Override
    public DocumentAt documentAtById(Integer id) {
        return documentAtDao.documentAtById(id);
    }

    @Override
    public List<DocumentAt> allDocumentAtByAt(AccidentTravail a) {
        return documentAtDao.allDocumentAtByAt(a);
    }
    
}
