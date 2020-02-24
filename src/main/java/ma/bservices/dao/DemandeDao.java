/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.Article;
import ma.bservices.tgcc.Entity.DemandeDetail;
import ma.bservices.tgcc.Entity.DemandeEntete;
import ma.bservices.tgcc.Entity.Engin;

/**
 *
 * @author admin
 */
public interface DemandeDao {
    
    DemandeEntete findDemandeEnteteById(long id);
    List<DemandeEntete> getListDemandeEntete();
    long addDemandeEntete(DemandeEntete demandeEntete);
    List<Engin> getListEngin();
    long updateDemandeEntete(DemandeEntete demandeEntete);
    long deleteDemandeEntete(DemandeEntete demandeEntete);
    DemandeDetail FindDemandeDetailById(long id);
    List<DemandeDetail> getListDemnadeDetailbyIdEntete(long id);
    long addDemandeDetail(DemandeDetail demandeDetail);
    long updateDemandeDetail(DemandeDetail demandeDetail);
    long deleteDemandeDetail(DemandeDetail demandeDetail);
    Object nombreDemandesInterne(String numeroDA, String chantier,String atelier, String etat, String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA, boolean userIsAdmin);
    List<DemandeEntete> listeDemandesInterne(String numeroDA, String chantier,String atelier, String etat, String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA, int start, int limit, boolean userIsAdmin);
    DemandeEntete getDemandeEntete(long idDA);
    Object nombreArticlesDemandeInterne(long idDA);
    int nombreDemandeInternebyDemandeur(int Demandeur,Date dateD);
    List<DemandeDetail> DemandeDetail(long idDA);
    DemandeDetail getDemandeDetail(long idArticleDA);
    boolean articleExiste(String codeArticle);
    boolean articleDADejaExiste(String codeArticle, long da);
    Object nombreRechercheArticle(String querySearch, String fam1, String fam2, String fam3, String reqDos, String designation);
    List<Article> listeRechercheArticle(int start, int limit, String querySearch, String fam1, String fam2, String fam3, String reqDos, String designation);
    Object nombreRechercheEngin(String querySearch, String Ref);
    List<Engin> listeRechercheEngin(int start, int limit, String Ref, String designation);
    
}
