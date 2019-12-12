/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.util.List;
import ma.bservices.tgcc.Entity.LotArticleXls;

/**
 *
 * @author airaamane
 */
public interface LotArticleXlsDAO {
    
    public List<LotArticleXls> findAll();
    
    public List<LotArticleXls> findByArticleId(Integer articleId);
    
    public LotArticleXls findByArticleLot(Integer articleId, Integer lotId);
    
    public void save(LotArticleXls entry);
    
    public void update(LotArticleXls entry);
    
    public void delete(LotArticleXls entry);
    
    
}
