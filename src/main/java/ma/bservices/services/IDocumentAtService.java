/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.beans.AccidentTravail;
import ma.bservices.beans.DocumentAt;

/**
 *
 * @author yassine
 */
public interface IDocumentAtService {
    
    public void addDocumentAt(DocumentAt d);
    public void editDocumentAt(DocumentAt d);
    public void remouvDocumentAt(DocumentAt d);
    public DocumentAt documentAtById(Integer id);
    public List<DocumentAt> allDocumentAtByAt(AccidentTravail a);
}
