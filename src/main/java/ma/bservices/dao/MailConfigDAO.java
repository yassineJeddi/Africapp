/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.tgcc.Entity.MailConfigBean;

/**
 *
 * @author airaamane
 */
public interface MailConfigDAO {

    public List<MailConfigBean> findByModule(String module);

    public void update(MailConfigBean bean);

    public List<MailConfigBean> findAll();
}
