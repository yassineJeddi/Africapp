/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.tgcc.Entity.Modem3G;
import ma.bservices.tgcc.dao.Mensuel.Modem3GDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author a.wattah
 */
@Service("modem3gService")
public class Modem3gServiceImpl implements Modem3gService, Serializable {

    @Autowired
    private Modem3GDAO modem3gDAO;

    public Modem3GDAO getModem3gDAO() {
        return modem3gDAO;
    }

    public void setModem3gDAO(Modem3GDAO modem3gDAO) {
        this.modem3gDAO = modem3gDAO;
    }

    @Override
    public List<Modem3G> findAll() {
        return this.modem3gDAO.findAll();
    }

    @Override
    public Boolean save(Modem3G modem3G) {
        return this.modem3gDAO.save(modem3G);
    }

    @Override
    public List<Modem3G> Consult3g(int id) {
        return this.modem3gDAO.Consult3g(id);
    }

    @Override
    public Boolean delete(Modem3G modem3G) {
        return this.modem3gDAO.delete(modem3G);
    }

    @Override
    public Boolean update(Modem3G modem3G) {
        return this.modem3gDAO.update(modem3G);
    }

    @Override
    public List<Modem3G> listModem3GById(String id) {
        return this.modem3gDAO.listModem3GById(id);
    }

    @Override
    public List<Modem3G> list3G_Non_Affecter() {
        return this.modem3gDAO.list3G_Non_Affecter();
    }

    @Override
    public List<Modem3G> list3G_Affeceter() {
        return this.modem3gDAO.list3G_Affeceter();
    }

    @Override
    public void desaffecter_3g_salarie(Modem3G modem3G) {
        this.modem3gDAO.desaffecter_3g_salarie(modem3G);
    }

    @Override
    public List<Modem3G> getListe_3gByImeiandNumero(String imei, String numero, String operateur, String numTel) {

        return this.modem3gDAO.getListe_3gByImeiandNumero(imei, numero, operateur, numTel);
    }

    @Override
    public List<Modem3G> getListe3gDifferentId(int id) {
        return this.modem3gDAO.getListe3gDifferentId(id);
    }

}
