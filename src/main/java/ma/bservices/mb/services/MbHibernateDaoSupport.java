/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.mb.services;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 *
 * @author admin
 */
public class MbHibernateDaoSupport extends HibernateDaoSupport {

    //le nom de la methode peu import, il sert just pour injecter sessionFactory    
    @Autowired
    public void methode(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
}
