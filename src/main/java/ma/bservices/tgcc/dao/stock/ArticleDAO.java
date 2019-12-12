/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.util.List;
import ma.bservices.beans.Article;

/**
 *
 * @author IRAAMANE
 */
public interface ArticleDAO {

    public List<Article> findAll();

    public void ajouterArticle(Article article);
    
    public void removeArticle(Article article);

    public Article findOneByCode(int code);
    
    public Article findByDesignation(String designation);
    
     public Article findByRef(String ref);

    public void updateArticle(Article article);

}
