/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.io.Serializable;
import java.util.List;
import ma.bservices.dao.IAttestationDao;
import ma.bservices.tgcc.Entity.Attestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yassine
 */
@Service("atestationServiceImp")
public class AtestationServiceImp implements Serializable,IAtestationService {

    
    @Autowired
    IAttestationDao attestationDao;

    public IAttestationDao getAttestationDao() {
        return attestationDao;
    }

    public void setAttestationDao(IAttestationDao attestationDao) {
        this.attestationDao = attestationDao;
    }    
    
    @Override
    public void addAtestation(Attestation a) {
        attestationDao.addAtestation(a);
    }

    @Override
    public List<Attestation> allAtestationBySalarie(int idSalarie) {
       return attestationDao.allAtestationBySalarie(idSalarie);
    }

    @Override
    public List<Attestation> allAtestationChantier() {
       return attestationDao.allAtestationChantier();
    }

    
    
}
