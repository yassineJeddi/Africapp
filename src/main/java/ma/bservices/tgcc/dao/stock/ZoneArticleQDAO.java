/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.util.List;
import ma.bservices.tgcc.Entity.ZoneArticleQ;

/**
 *
 * @author IRAAMANE
 */
public interface ZoneArticleQDAO {

    public List<ZoneArticleQ> findAll();
    
    public List<ZoneArticleQ> findByArticleId(int article_id, int chantier_id);
    
    public List<ZoneArticleQ> findByChantier(int chantier_id);
    
    public ZoneArticleQ findByZoneArticle(int article_id, int zone_id, int lot_id);
    
    public ZoneArticleQ findById(int id);
    
    
    
    

    public void addZoneArticleQ(ZoneArticleQ zoneArticleQ);

    public void removeZoneArticleQ(ZoneArticleQ zoneArticleQ);

    public void updateZoneArticleQ(ZoneArticleQ zoneArticleQ);
    
}
