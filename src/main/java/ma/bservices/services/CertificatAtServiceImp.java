/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.AccidentTravail;
import ma.bservices.beans.CertificatAt;
import ma.bservices.dao.ICertificatAtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */

@Service("certificatAtService")
public class CertificatAtServiceImp  implements Serializable,ICertificatAtService {
    
    @Autowired
    ICertificatAtDao certificatAtDao;

    public ICertificatAtDao getCertificatAtDao() {
        return certificatAtDao;
    }

    public void setCertificatAtDao(ICertificatAtDao certificatAtDao) {
        this.certificatAtDao = certificatAtDao;
    }
    
    

    @Override
    public void addCertificatAt(CertificatAt d) {
        certificatAtDao.addCertificatAt(d);
    }

    @Override
    public void editCertificatAt(CertificatAt d) {
        certificatAtDao.editCertificatAt(d);
    }

    @Override
    public void remouvCertificatAt(CertificatAt d) {
        certificatAtDao.remouvCertificatAt(d);
    }

    @Override
    public CertificatAt certificatAtById(Integer id) {
        return certificatAtDao.certificatAtById(id);
    }

    @Override
    public List<CertificatAt> allcertificatAtByAt(AccidentTravail a) {
        return certificatAtDao.allCertificatAtByAt(a);
    }
    
}
