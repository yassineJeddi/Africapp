/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.dao.MailConfigDAO;
import ma.bservices.tgcc.Entity.MailConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author airaamane
 */

@Service("mailConfigService")
public class MailConfigServiceImpl implements Serializable, MailConfigService {

    @Autowired
    private MailConfigDAO mailConfigDAO;

    public MailConfigDAO getMailConfigDAO() {
        return mailConfigDAO;
    }

    public void setMailConfigDAO(MailConfigDAO mailConfigDAO) {
        this.mailConfigDAO = mailConfigDAO;
    }

    @Override
    public List<MailConfigBean> findByModule(String module) {
        return mailConfigDAO.findByModule(module);
    }

    @Override
    public void update(MailConfigBean bean) {
        mailConfigDAO.update(bean);
    }

    @Override
    public List<MailConfigBean> findAll() {
        return mailConfigDAO.findAll();
    }

}
