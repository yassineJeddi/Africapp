/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.util.List;
import ma.bservices.tgcc.Entity.ChantierArticleQ;

/**
 *
 * @author IRAAMANE
 */
public interface ChantierArticleQDAO {

    public List<ChantierArticleQ> findAll();

    public List<ChantierArticleQ> findByArticleId(int article_id);
    
    public List<ChantierArticleQ> findByFilter(String ref, String des, String fam, String sfam, String ssfam, Integer id);

    public List<ChantierArticleQ> findByChantierId(int chantier_id);

    public List<ChantierArticleQ> findByCode(String code, int chantier_id);

    public ChantierArticleQ findByChantierArticle(int article_id, int chantier_id);

    public ChantierArticleQ findByRefArticle(String ref, int chantier_id);

    public void addChantierArticleQ(ChantierArticleQ chantierArticleQ);

    public void removeChantierArticleQ(ChantierArticleQ chantierArticleQ);

    public void updateChantierArticleQ(ChantierArticleQ chantierArticleQ);

    public ChantierArticleQ findLastIventilation();
}
