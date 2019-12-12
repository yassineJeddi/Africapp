/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.util.List;
import ma.bservices.tgcc.Entity.StatusTransfert;

/**
 *
 * @author IRAAMANE
 */
public interface StatusTransferService {
 
    public List<StatusTransfert> findAll();
    
}
