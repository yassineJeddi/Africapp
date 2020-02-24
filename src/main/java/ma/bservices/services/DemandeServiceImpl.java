/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.Article;
import ma.bservices.dao.DemandeDao;
import ma.bservices.tgcc.Entity.DemandeDetail;
import ma.bservices.tgcc.Entity.DemandeEntete;
import ma.bservices.tgcc.Entity.Engin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service("demandeService")
public class DemandeServiceImpl implements DemandeService , Serializable {

    	private static final long serialVersionUID = 1L;
	@Autowired 
	DemandeDao demandeDao;
        
    @Override
    public DemandeEntete findDemandeEnteteById(long id) {
        return demandeDao.findDemandeEnteteById(id);
    }

    @Override
    public List<DemandeEntete> getListDemandeEntete() {
        return demandeDao.getListDemandeEntete();
    }

    @Override
    public long addDemandeEntete(DemandeEntete demandeEntete) {
        return demandeDao.addDemandeEntete(demandeEntete);
    }

    @Override
    public long updateDemandeEntete(DemandeEntete demandeEntete) {
        return demandeDao.updateDemandeEntete(demandeEntete);
    }

    @Override
    public long deleteDemandeEntete(DemandeEntete demandeEntete) {
        return demandeDao.deleteDemandeEntete(demandeEntete);
    }

    @Override
    public DemandeDetail FindDemandeDetailById(long id) {
        return demandeDao.FindDemandeDetailById(id);
    }

    @Override
    public List<DemandeDetail> getListDemnadeDetailbyIdEntete(long id) {
       return demandeDao.getListDemnadeDetailbyIdEntete(id);
    }

    @Override
    public long addDemandeDetail(DemandeDetail demandeDetail) {
        return demandeDao.addDemandeDetail(demandeDetail);
    }

    @Override
    public long updateDemandeDetail(DemandeDetail demandeDetail) {
       return demandeDao.updateDemandeDetail(demandeDetail);
    }

    @Override
    public long deleteDemandeDetail(DemandeDetail demandeDetail) {
        return demandeDao.deleteDemandeDetail(demandeDetail);
    }

    @Override
    public Object nombreDemandesInterne(String numeroDA, String chantier, String atelier, String etat, String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA, boolean userIsAdmin) {
       return demandeDao.nombreDemandesInterne(numeroDA, chantier, atelier, etat, demandeur,  dateAjoutDe,  dateAjoutA,  dateLivraisonSouhaiteeDe,  dateLivraisonSouhaiteeA,  userIsAdmin);
    }

    @Override
    public List<DemandeEntete> listeDemandesInterne(String numeroDA, String chantier, String atelier, String etat, String demandeur, Date dateAjoutDe, Date dateAjoutA, Date dateLivraisonSouhaiteeDe, Date dateLivraisonSouhaiteeA, int start, int limit, boolean userIsAdmin) {
        return demandeDao.listeDemandesInterne( numeroDA,  chantier,  atelier,  etat,  demandeur,  dateAjoutDe,  dateAjoutA,  dateLivraisonSouhaiteeDe,  dateLivraisonSouhaiteeA,  start,  limit,  userIsAdmin)  ;
    }

    @Override
    public DemandeEntete getDemandeEntete(long idDA) {
        return  demandeDao.getDemandeEntete(idDA);
    }

    @Override
    public Object nombreArticlesDemandeInterne(long idDA) {
       return demandeDao.nombreArticlesDemandeInterne(idDA);
    }

    @Override
    public List<DemandeDetail> DemandeDetail(long idDA) {
        return demandeDao.DemandeDetail(idDA);
    }

    @Override
    public DemandeDetail getDemandeDetail(long idArticleDA) {
          return demandeDao.getDemandeDetail(idArticleDA);
    }

    @Override
    public boolean articleExiste(String codeArticle) {
        return demandeDao.articleExiste(codeArticle);
    }

    @Override
    public boolean articleDADejaExiste(String codeArticle, long da) {
        return demandeDao.articleDADejaExiste(codeArticle, da);
    }

    @Override
    public Object nombreRechercheArticle(String querySearch, String fam1, String fam2, String fam3, String reqDos, String designation) {
       return  demandeDao.nombreRechercheArticle(querySearch, fam1, fam2, fam3, reqDos, designation);
    } 
    
    
    @Override
    public List<Article> listeRechercheArticle(int start, int limit, String querySearch, String fam1, String fam2, String fam3, String reqDos, String designation) {
        return demandeDao.listeRechercheArticle(start, limit, querySearch, fam1, fam2, fam3, reqDos, designation);
    }

    public DemandeDao getDemandeDao() {
        return demandeDao;
    }

    public void setDemandeDao(DemandeDao demandeDao) {
        this.demandeDao = demandeDao;
    }

    @Override
    public int nombreDemandeInternebyDemandeur(int Demandeur, Date dateD) {
        return demandeDao.nombreDemandeInternebyDemandeur(Demandeur, dateD);
    }

    @Override
    public List<Engin> getListEngin() {
         return demandeDao.getListEngin();
    }

    @Override
    public Object nombreRechercheEngin(String Ref, String designation) {
        return demandeDao.nombreRechercheEngin(Ref, designation);
    }

    @Override
    public List<Engin> listeRechercheEngin(int start, int limit, String Ref, String designation) {
      return demandeDao.listeRechercheEngin(start, limit, Ref, designation);
    }
    
    
    
}
