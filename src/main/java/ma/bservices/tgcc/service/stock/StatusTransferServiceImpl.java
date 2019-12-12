/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.StatusTransfert;
import ma.bservices.tgcc.dao.stock.StatusTransferDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author IRAAMANE
 */
@Service("statusTransferService")
public class StatusTransferServiceImpl implements StatusTransferService, Serializable {
    
     @Autowired
    private StatusTransferDAO statusTransferDAO;

    public StatusTransferDAO getStatusTransferDAO() {
        return statusTransferDAO;
    }

    public void setStatusTransferDAO(StatusTransferDAO statusTransferDAO) {
        this.statusTransferDAO = statusTransferDAO;
    }
    
     @Override
    public List<StatusTransfert> findAll() {
        return statusTransferDAO.findAll();
    }
}
