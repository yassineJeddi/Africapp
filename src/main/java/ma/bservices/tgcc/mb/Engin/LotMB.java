package ma.bservices.tgcc.mb.Engin;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ma.bservice.tgcc.Constante.Message;
import ma.bservices.beans.Lot;
import ma.bservices.tgcc.service.Engin.LotService;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author medad_000
 */
@Component
@ManagedBean(name = "LotCfg")
@ViewScoped
public class LotMB implements Serializable {

    @ManagedProperty(value = "#{lotService}")
    private LotService lotService;

    private List<Lot> lots;

    private Lot lot = new Lot();

    public LotService getLotService() {
        return lotService;
    }

    public void setLotService(LotService lotService) {
        this.lotService = lotService;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    @PostConstruct
    public void init() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        lotService = ctx.getBean(LotService.class);
        lots = lotService.findAll();
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(Message.UPDATE_LOT, ((Lot) event.getObject()).getLibelle());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        lotService.updateLot(((Lot) event.getObject()));
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(Message.UPDATE_LOT_CANCEL, ((Lot) event.getObject()).getLibelle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteLot(Lot l) {

        FacesContext context = FacesContext.getCurrentInstance();
        Boolean delete = lotService.deleteLot(l.getId());
        if (delete == true) {

            context.addMessage(null, new FacesMessage("" + Message.DELETE_LOT, ""));
            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            lotService = ctx.getBean(LotService.class);
            lots = lotService.findAll();
        }

    }

    public void insertLot() {
        FacesContext context = FacesContext.getCurrentInstance();
        Boolean insert = lotService.insertLot(lot);
        if (insert == true) {
            context.addMessage(null, new FacesMessage("" + Message.ADD_LOT, ""));
            lot=new Lot();
        }

    }

}
