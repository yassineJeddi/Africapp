/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.TransferStock;

/**
 *
 * @author IRAAMANE
 */
public interface TransferStockService {

    public List<TransferStock> findAll();

    public void removeTransferStock(TransferStock transferStock);

    public List<TransferStock> findAllTransfers();
    
     public List<TransferStock> findByIntervalDate(int chantier_id, Date dateFrom, Date dateTo);
    
    public void cancelRec(TransferStock transfer, int status_id);    
    

    public List<TransferStock> findAllRetours();

    public void addTransferStock(TransferStock transfertStock, int article_id, int id_chantier_emetteur, int id_chantier_recepteur, Double quantite, Double quantiteReception, int statusId, Date transferDate, boolean isRetour, boolean isRec, boolean isRetRec, Integer ref);

    public void receptTransfertStock(TransferStock transfertStock, int statusId, boolean isRec, Double quantiteReception, Date dateReception);

    public void retourTransfertStock(TransferStock transfertStock, int statusId, Double quantiteRetour, Date dateRetDate);

    public List<TransferStock> findById(int transfer_id, int chantier_id);

    public List<TransferStock> findByRef(int transfer_id);

    public List<TransferStock> findByStatus(int status_id);

    public void setGlobalStatus(TransferStock transferStock, boolean status);

    public List<TransferStock> findByChantierEmetteur(int id_chantier_emetteur);

    public List<TransferStock> findByChantierRecepteur(int id_chantier_recepteur);

    public List<TransferStock> findRByChantierEmetteur(int id_chantier_emetteur);

    public List<TransferStock> findRByChantierRecepteur(int id_chantier_recepteur);

}
