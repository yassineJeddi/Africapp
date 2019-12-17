/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.DocumentDetailAt;

/**
 *
 * @author yassine
 */
public interface IDocumentDetailAtDao {
    
    public void addDocumentDetailAt(DocumentDetailAt d);
    public void editDocumentDetailAt(DocumentDetailAt d);
    public void remouveDocumentDetailAt(DocumentDetailAt d);
    public DocumentDetailAt infoDocumentDetailAtById(Long id);
    public List<DocumentDetailAt> allDocumentDetailAt();
    public List<DocumentDetailAt> allDocumentDetailAtByIdDetailAt(Long id);
}
