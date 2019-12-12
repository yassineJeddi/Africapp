/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.stock;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.stereotype.Component;

/**
 *
 * @author airaamane
 */

@Component
@ManagedBean(name = "historiqueStock")
@ViewScoped
public class HistoriqueStockMb implements Serializable{

    
    
    private int chantierGlobal;
    
    
    
}
