/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.util.List;
import ma.bservices.beans.Article;


/**
 *
 * @author IRAAMANE
 */
public interface ArticleService {

    public List<Article> findAll();

    public Article findOneByCode(int codeArticle);

    public Article findByRef(String ref);
    
    public Article findByDesignation(String designation);

    public void removeArticle(Article article);

    public void addArticle(Article article, String ref, String des, String fam, String sfam, String ssfam, String typConso, String nature, String unite, Double qteTotale, Double qteDispo, Double qteConso);

    public void updateArticle(Article article, Double qteTotale, Double qteDispo, Double qteConso);

}
