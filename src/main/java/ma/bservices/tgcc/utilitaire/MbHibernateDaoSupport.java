/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 *
 * @author Zakaria DRISSI EL MALIANI
 */
public class MbHibernateDaoSupport extends HibernateDaoSupport{
    
    //le nom de la methode peu import, il sert just pour injecter sessionFactory    
    @Autowired
    public void methode(SessionFactory sessionFactory)
    {
        setSessionFactory(sessionFactory);
    }
}
