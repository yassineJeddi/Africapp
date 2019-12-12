/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.stock;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ma.bservices.beans.Article;
import ma.bservices.beans.Chantier;
import ma.bservices.tgcc.Entity.StatusTransfert;
import ma.bservices.tgcc.Entity.TransferStock;
import ma.bservices.tgcc.dao.engin.ChantierDAO;
import ma.bservices.tgcc.dao.stock.ArticleDAO;
import ma.bservices.tgcc.dao.stock.StatusTransferDAO;
import ma.bservices.tgcc.dao.stock.TransferStockDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author IRAAMANE
 */
@Service("transferStockService")
public class TransferStockServiceImpl implements TransferStockService, Serializable {

    @Autowired
    TransferStockDAO transferStockDAO;

    @Autowired
    ArticleDAO articleDAO;

    @Autowired
    ChantierDAO chantierDAO;

    @Autowired
    StatusTransferDAO statusDAO;

    public TransferStockDAO getTransferStockDAO() {
        return transferStockDAO;
    }

    public void setTransferStockDAO(TransferStockDAO transferStockDAO) {
        this.transferStockDAO = transferStockDAO;
    }

    public ArticleDAO getArticleDAO() {
        return articleDAO;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public ChantierDAO getChantierDAO() {
        return chantierDAO;
    }

    public void setChantierDAO(ChantierDAO chantierDAO) {
        this.chantierDAO = chantierDAO;
    }

    public StatusTransferDAO getStatusDAO() {
        return statusDAO;
    }

    public void setStatusDAO(StatusTransferDAO statusDAO) {
        this.statusDAO = statusDAO;
    }

    @Override
    public void addTransferStock(TransferStock transfertStock, int article_id, int id_chantier_emetteur, int id_chantier_recepteur, Double quantite, Double quantiteReception, int statusId, Date dateTransfert, boolean isRetour, boolean isRec,boolean isRetRec, Integer ref) {
        // get article
        Article article = articleDAO.findOneByCode(article_id);
        //get chantier emetteur
        Chantier chantier_emetteur = chantierDAO.findById(id_chantier_emetteur);
        //get chantier recepteur
        Chantier chantier_recepteur = chantierDAO.findById(id_chantier_recepteur);
        //get status transfert
        StatusTransfert status = statusDAO.findById(statusId);

        //set article, chantierEmetteur, chantierRecepteur, quantite, status, date, isRetour du transfert
        transfertStock.setArticleId(article);
        transfertStock.setChantierEmetteurId(chantier_emetteur);
        transfertStock.setChantierRecepteurId(chantier_recepteur);
        transfertStock.setQuantite(quantite);
        transfertStock.setQuantiteReception(quantiteReception);
        transfertStock.setStatusTransferId(status);
        transfertStock.setDateTransferStock(dateTransfert);
        transfertStock.setIsRetour(isRetour);
        transfertStock.setIsRec(isRec);
        transfertStock.setIsRetourRec(isRetRec);
        transfertStock.setReferenceTransfer(ref);

        //save to database
        transferStockDAO.addTransfertStock(transfertStock);

        System.out.println("=====================TRANSFER ADDED SUCCESSFULLY =================");

    }

    @Override
    public List<TransferStock> findAllTransfers() {
        return transferStockDAO.findAll();
    }
    
      @Override
    public List<TransferStock> findByIntervalDate(int chantier_id, Date date_from, Date date_to) {
        return transferStockDAO.findByIntervalDate(chantier_id, date_from, date_to);
    }

    @Override
    public List<TransferStock> findAllRetours() {
        return transferStockDAO.findAllRetours();
    }

    @Override
    public List<TransferStock> findAll() {
        return transferStockDAO.findAllBoth();
    }

    @Override
    public void removeTransferStock(TransferStock transferStock) {
        transferStockDAO.removeTransferStock(transferStock);
    }

    @Override
    public List<TransferStock> findById(int transfer_id, int chantier_id) {
        return transferStockDAO.findById(transfer_id, chantier_id);
    }

    @Override
    public List<TransferStock> findByRef(int transfer_id) {
        return transferStockDAO.findByRef(transfer_id);
    }

    @Override
    public List<TransferStock> findByStatus(int status_id) {
        return transferStockDAO.findByStatus(status_id);
    }

    @Override
    public List<TransferStock> findByChantierEmetteur(int id_chantier_emetteur) {
        return transferStockDAO.findByChantierEmetteur(id_chantier_emetteur);
    }

    @Override
    public List<TransferStock> findByChantierRecepteur(int id_chantier_recepteur) {
        return transferStockDAO.findByChantierRecepteur(id_chantier_recepteur);
    }

    @Override
    public List<TransferStock> findRByChantierEmetteur(int id_chantier_emetteur) {
        return transferStockDAO.findRByChantierEmetteur(id_chantier_emetteur);
    }

    @Override
    public List<TransferStock> findRByChantierRecepteur(int id_chantier_recepteur) {
        return transferStockDAO.findRByChantierRecepteur(id_chantier_recepteur);
    }
    
    @Override
    public void cancelRec(TransferStock transfer, int status_id){
     StatusTransfert status = statusDAO.findById(status_id);
     transfer.setStatusTransferId(status);
     transfer.setQuantiteReception(0d);
    transferStockDAO.receptTransfertStock(transfer);    
    }

    @Override
    public void receptTransfertStock(TransferStock transfertStock, int statusId, boolean isRec, Double quantiteReception, Date dateReception) {
        //get status transfert
        StatusTransfert status = statusDAO.findById(statusId);

        transfertStock.setStatusTransferId(status);
        transfertStock.setQuantiteReception(quantiteReception);
        transfertStock.setDateReceptionStock(dateReception);
        transfertStock.setIsRec(isRec);
        transferStockDAO.receptTransfertStock(transfertStock);
    }

    @Override
    public void retourTransfertStock(TransferStock transfertStock, int statusId, Double quantiteRetour, Date dateRetDate) {
        //get status transfert
        StatusTransfert status = statusDAO.findById(statusId);

        transfertStock.setStatusTransferId(status);
        transfertStock.setQuantiteRetour(quantiteRetour);
        transfertStock.setDateRetourStock(dateRetDate);
        transferStockDAO.receptTransfertStock(transfertStock);
    }

    @Override
    public void setGlobalStatus(TransferStock transferStock, boolean status) {
        transferStock.setIsRec(status);
    }

}
