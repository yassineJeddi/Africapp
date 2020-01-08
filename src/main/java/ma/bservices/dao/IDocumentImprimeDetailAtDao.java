/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.DocumentImprimeDetailAt;

/**
 *
 * @author yassine
 */
public interface IDocumentImprimeDetailAtDao {
    public void addDocumentDetailAt(DocumentImprimeDetailAt d);
    public void editDocumentDetailAt(DocumentImprimeDetailAt d);
    public void remouveDocumentDetailAt(DocumentImprimeDetailAt d);
    public DocumentImprimeDetailAt infoDocumentDetailAtById(Long id);
    public List<DocumentImprimeDetailAt> allDocumentDetailAt();
    public List<DocumentImprimeDetailAt> allDocumentDetailAtByIdDetailAt(Long id);
}
