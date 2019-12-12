/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.achat;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ma.bservices.beans.ArticleBL;
import ma.bservices.beans.BonLivraison;
import ma.bservices.mb.services.Module;
import ma.bservices.services.AchatService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahdi
 */
@Component
@ManagedBean
@ViewScoped
public class DetailBLMb implements Serializable {

    private AchatService achatService;
    private BonLivraison bonLivraison = new BonLivraison();
    private List<ArticleBL> articleBLs;

    public List<ArticleBL> getArticleBLs() {
        return articleBLs;
    }

    public void setArticleBLs(List<ArticleBL> articleBLs) {
        this.articleBLs = articleBLs;
    }

    public BonLivraison getBonLivraison() {
        return bonLivraison;
    }

    public void setBonLivraison(BonLivraison bonLivraison) {
        this.bonLivraison = bonLivraison;
    }

    public AchatService getAchatService() {
        return achatService;
    }

    public void setAchatService(AchatService achatService) {
        this.achatService = achatService;
    }

    @PostConstruct
    public void init()  {

        achatService = Module.ctx.getBean(AchatService.class);

        int id = -1;
        //details method
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();
        if (requestParameters.containsKey("idBL")) {
            id = Integer.valueOf(requestParameters.get("idBL"));

        } else {
        }

        bonLivraison = achatService.getBonLivraison(id);
        articleBLs = achatService.getArticlesBLByIdBL(id);

        achatService = Module.ctx.getBean(AchatService.class);

    }

}
