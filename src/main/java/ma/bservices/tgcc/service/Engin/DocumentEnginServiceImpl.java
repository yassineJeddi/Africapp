/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Engin;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.DocumentEngin;
import ma.bservices.tgcc.dao.engin.DocumentEnginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service
public class DocumentEnginServiceImpl implements DocumentEnginService, Serializable {

    @Autowired
    DocumentEnginDAO documentEnginDAO;

    public DocumentEnginDAO getDocumentEnginDAO() {
        return documentEnginDAO;
    }

    public void setDocumentEnginDAO(DocumentEnginDAO documentEnginDAO) {
        this.documentEnginDAO = documentEnginDAO;
    }

    @Override
    public void save(DocumentEngin documentEngin) {
        this.documentEnginDAO.save(documentEngin);
    }

    @Override
    public List<DocumentEngin> getListDocumentEngins(int id) {
        return this.documentEnginDAO.getListDocumentEngins(id);
    }

    @Override
    public void delete(DocumentEngin documentEngin) {
        this.documentEnginDAO.delete(documentEngin);
    }

    @Override
    public void update(DocumentEngin documentEngin) {
        this.documentEnginDAO.update(documentEngin);
    }

}
