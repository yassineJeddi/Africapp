/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.Article;
import ma.bservices.beans.Permission;
import ma.bservices.beans.Utilisateur;
import static ma.bservices.services.AchatService.logger;
import ma.bservices.tgcc.Entity.DemandeDetail;
import ma.bservices.tgcc.Entity.DemandeEntete;
import ma.bservices.tgcc.Entity.Engin;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author admin
 */
public interface DemandeService {
    
    DemandeEntete findDemandeEnteteById(long id);
    List<DemandeEntete> getListDemandeEntete();
    List<Engin> getListEngin();
    long addDemandeEntete(DemandeEntete demandeEntete);
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
    Object nombreRechercheEngin(String Ref,   String designation);
    List<Engin> listeRechercheEngin(int start, int limit, String Ref,  String designation);

}