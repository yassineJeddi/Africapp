/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.tgcc.Entity.Attestation;

/**
 *
 * @author yassine
 */
public interface IAtestationService {
    
    public void addAtestation(Attestation a);
    public List<Attestation> allAtestationBySalarie(int idSalarie);
    public List<Attestation> allAtestationChantier();
    
}
