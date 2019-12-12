/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.beans.Docs;

/**
 *
 * @author air
 */
public interface DocsService {

    public List<Docs> findAll();
    public Docs findByNodeRef(String nodeRef);
}
