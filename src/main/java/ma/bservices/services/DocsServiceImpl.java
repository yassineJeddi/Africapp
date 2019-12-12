/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Docs;
import ma.bservices.dao.DocsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service("docsService")
public class DocsServiceImpl implements Serializable, DocsService {

    @Autowired
    DocsDAO docsDAO;

    public DocsDAO getDocsDAO() {
        return docsDAO;
    }

    public void setDocsDAO(DocsDAO docsDAO) {
        this.docsDAO = docsDAO;
    }  
    
    @Override
    public List<Docs> findAll() {
       return docsDAO.findAll();
    }

    @Override
    public Docs findByNodeRef(String nodeRef) {
        return docsDAO.findByNodeRef(nodeRef);
    }
    
}
