/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.stock;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.TransferStock;

/**
 *
 * @author IRAAMANE
 */
public interface TransferStockDAO {

    public List<TransferStock> findAll();
    
     public List<TransferStock> findAllBoth();

    public List<TransferStock> findAllRetours();
    
     public List<TransferStock> findByIntervalDate(int chantier_id, Date date_from, Date date_to);
    
    public void removeTransferStock(TransferStock transferStock);

    public List<TransferStock> findById(int id, int chantier_id);
    
    public List<TransferStock> findByRef(int ref);

    public void addTransfertStock(TransferStock transfertStock);

    public void updateTransfertStock(TransferStock transfertStock);

    public List<TransferStock> findByStatus(int status_id);

    public List<TransferStock> findByChantierEmetteur(int id_chantier_emetteur);

    public List<TransferStock> findByChantierRecepteur(int id_chantier_recepteur);
    
     public List<TransferStock> findRByChantierEmetteur(int id_chantier_emetteur);

    public List<TransferStock> findRByChantierRecepteur(int id_chantier_recepteur);

    public void receptTransfertStock(TransferStock transfertStock);

}
