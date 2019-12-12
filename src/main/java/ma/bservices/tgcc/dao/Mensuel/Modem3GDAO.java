/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.List;
import ma.bservices.tgcc.Entity.Modem3G;

/**
 *
 * @author a.wattah
 */
public interface Modem3GDAO {

    public List<Modem3G> findAll();

    public Boolean save(Modem3G modem3G);

    public List<Modem3G> Consult3g(int id);
    
    public List<Modem3G> getListe3gDifferentId(int id);

    public Boolean delete(Modem3G modem3G);

    public Boolean update(Modem3G modem3G);

    public List<Modem3G> listModem3GById(String id);

    public List<Modem3G> list3G_Non_Affecter();

    public List<Modem3G> list3G_Affeceter();

    public List<Modem3G> getListe_3gByImeiandNumero(String imei, String numero, String operateur, String numTel);

    /**
     * methode qui permet de desaffecter 3g un mensuel
     *
     * @param modem3G
     */
    public void desaffecter_3g_salarie(Modem3G modem3G);

}
