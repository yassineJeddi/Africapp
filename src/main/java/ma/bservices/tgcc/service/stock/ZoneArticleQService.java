/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.ZoneArticleQ;

/**
 *
 * @author IRAAMANE
 */
public interface ZoneArticleQService {

    public List<ZoneArticleQ> findAll();
    
    public List<ZoneArticleQ> findByArticleId(int article_id, int chantier_id);
    
    public List<ZoneArticleQ> findByChantier(int id_chantier);
    
    public ZoneArticleQ findByZoneArticle(int article_id, int zone_id, int lot_id);
    
    public ZoneArticleQ findById(int id);

    public void addZoneArticleQ(ZoneArticleQ zoneArticleQ, int article_id, int zone_id, int lot_id, Double quantite, int chantier_id);
    
    public void addZoneArticleQQ(ZoneArticleQ zoneArticleQ, int article_id, int zone_id, int lot_id, Double quantite, int chantier_id, Date dateaffect);

    public void updateZoneArticleQQ(ZoneArticleQ zoneArticleQ, Double quantite, Date dateaffect);
    
    public void updateZoneArticleQ(ZoneArticleQ zoneArticleQ, Double quantite);
    
    public void updateZoneArticleQAll(ZoneArticleQ zoneArticleQ, int zone_id, int lot_id,  Double quantite);

    public void removeZoneArticleQ(ZoneArticleQ zoneArticleQ);

}
