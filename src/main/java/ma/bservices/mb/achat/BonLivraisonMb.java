/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.achat;

import com.sun.faces.mgbean.ManagedBeanCreationException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ma.bservices.beans.BonLivraison;
import ma.bservices.mb.services.Module;
import ma.bservices.paginate.BlPaginate;
import ma.bservices.services.AchatService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class BonLivraisonMb implements Serializable {

    private AchatService achatService;
    private List<BonLivraison> bls;
    private Integer page, nbr, idChantier, idUser, var;
    private BonLivraison bonLivraisonFind = new BonLivraison();
    private Boolean prev, next, last, first, pageId;

    public BonLivraison getBonLivraisonFind() {
        return bonLivraisonFind;
    }

    public void setBonLivraisonFind(BonLivraison bonLivraisonFind) {
        this.bonLivraisonFind = bonLivraisonFind;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public AchatService getAchatService() {
        return achatService;
    }

    public void setAchatService(AchatService achatService) {
        this.achatService = achatService;
    }

    public List<BonLivraison> getBls() {
        return bls;
    }

    public void setBls(List<BonLivraison> bls) {
        this.bls = bls;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getNbr() {
        return nbr;
    }

    public void setNbr(Integer nbr) {
        this.nbr = nbr;
    }

    public Integer getIdChantier() {
        return idChantier;
    }

    public void setIdChantier(Integer idChantier) {
        this.idChantier = idChantier;
    }

    public Integer getVar() {
        return var;
    }

    public void setVar(Integer var) {
        this.var = var;
    }

    public Boolean getPrev() {
        return prev;
    }

    public void setPrev(Boolean prev) {
        this.prev = prev;
    }

    public Boolean getNext() {
        return next;
    }

    public void setNext(Boolean next) {
        this.next = next;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getPageId() {
        return pageId;
    }

    public void setPageId(Boolean pageId) {
        this.pageId = pageId;
    }

    @PostConstruct
    public void init() {
        achatService = Module.ctx.getBean(AchatService.class);
        page = 1;
        try {
             bls = BlPaginate.page(page, bonLivraisonFind.getNumeroBL(), bonLivraisonFind.getNumeroBC(), bonLivraisonFind.getDateLivraison(), (idChantier != null && idChantier == 0) ? null : idChantier, (idUser != null && idUser == 0) ? null : idUser);
              nbr = BlPaginate.nbr;
                var = (nbr % 10 == 0) ? nbr / 10 : nbr / 10 + 1;
        if (var == 1) {
            last = true;
            pageId = true;
            first = true;
            next = true;
            prev = true;
        } else {
            last = false;
            pageId = false;
            first = true;
            next = false;
            prev = true;
        }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
      
      

    }

    public void onPaginate() {
        bls.clear();
        bls = BlPaginate.page(page, bonLivraisonFind.getNumeroBL(), bonLivraisonFind.getNumeroBC(), bonLivraisonFind.getDateLivraison(), (idChantier != null && idChantier == 0) ? null : idChantier, (idUser != null && idUser == 0) ? null : idUser);
//        nbr = BlPaginate.nbr;
          if (page == var) {
            last = true;
            pageId = true;
            first = false;
            next = true;
            prev = false;
        } else if (page == 1) {
            last = false;
            pageId = false;
            first = true;
            next = false;
            prev = true;
        } else {
            last = false;
            pageId = false;
            first = false;
            next = false;
            prev = false;
        }
    }

    public void onNext() {
        bls.clear();
        page += 1;
        bls = BlPaginate.page(page, bonLivraisonFind.getNumeroBL(), bonLivraisonFind.getNumeroBC(), bonLivraisonFind.getDateLivraison(), (idChantier != null && idChantier == 0) ? null : idChantier, (idUser != null && idUser == 0) ? null : idUser);
//        nbr = BlPaginate.nbr;
        if (Objects.equals(page, var)) {
            last = true;
            pageId = false;
            first = false;
            next = true;
            prev = false;
        } else {
            last = false;
            pageId = false;
            first = false;
            next = false;
            prev = false;
        }
    }

    public void onPrevious() {
        bls.clear();
        page -= 1;
        bls = BlPaginate.page(page, bonLivraisonFind.getNumeroBL(), bonLivraisonFind.getNumeroBC(), bonLivraisonFind.getDateLivraison(), (idChantier != null && idChantier == 0) ? null : idChantier, (idUser != null && idUser == 0) ? null : idUser);
//        nbr = BlPaginate.nbr;
        if (page == 1) {
            last = false;
            pageId = false;
            first = true;
            next = false;
            prev = true;
        } else {
            last = false;
            pageId = false;
            first = false;
            next = false;
            prev = false;
        }
    }

    public void onFirst() {
        page = 1;
        bls = BlPaginate.page(page, bonLivraisonFind.getNumeroBL(), bonLivraisonFind.getNumeroBC(), bonLivraisonFind.getDateLivraison(), (idChantier != null && idChantier == 0) ? null : idChantier, (idUser != null && idUser == 0) ? null : idUser);
//        nbr = BlPaginate.nbr;
        last = false;
        pageId = false;
        first = true;
        next = false;
        prev = true;

    }

    public void onLast() {
        page = var;
        bls = BlPaginate.page(page, bonLivraisonFind.getNumeroBL(), bonLivraisonFind.getNumeroBC(), bonLivraisonFind.getDateLivraison(), (idChantier != null && idChantier == 0) ? null : idChantier, (idUser != null && idUser == 0) ? null : idUser);
//        nbr = BlPaginate.nbr;
        last = true;
        pageId = false;
        first = false;
        next = true;
        prev = false;
    }

    public void recherche() {
        System.out.println("find BL Methode");
        System.out.println("recherche date " + bonLivraisonFind.getDateLivraison());
        page = 1;
        bls = BlPaginate.page(page,
                bonLivraisonFind.getNumeroBL() != null ? bonLivraisonFind.getNumeroBL() : "",
                bonLivraisonFind.getNumeroBC() != null ? bonLivraisonFind.getNumeroBC() : "",
                bonLivraisonFind.getDateLivraison(),
                idChantier != null && idChantier != 0 ? idChantier : null,
                idUser != null && idUser != 0 ? idUser : null);
        nbr = BlPaginate.nbr;
        var = (nbr % 10 == 0) ? nbr / 10 : nbr / 10 + 1;
    }

    /**
     * redirect to DetailBL MB
     *
     * @param idBL
     * @return
     */
    public String detail(Integer idBL) {

//        bonLivraison = achatService.getBonLivraison(idBL);
//        articleBLs = achatService.getArticlesBLByIdBL(idBL);
        return "detailBL.xhtml?faces-redirect=true&idBL=" + idBL;
    }
}
