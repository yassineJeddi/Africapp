/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.List;
import ma.bservices.beans.AccidentTravail;
import ma.bservices.beans.CertificatAt;

/**
 *
 * @author yassine
 */
public interface ICertificatAtService {
    
    public void addCertificatAt(CertificatAt d);
    public void editCertificatAt(CertificatAt d);
    public void remouvCertificatAt(CertificatAt d);
    public CertificatAt certificatAtById(Integer id);
    public List<CertificatAt> allcertificatAtByAt(AccidentTravail a);
}
