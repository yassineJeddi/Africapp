/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.services;

import java.util.Date;
import java.util.List;
import ma.bservices.beans.Presence;

/**
 *
 * @author yassine
 */
public interface IPresenceService {
    
    public List<Presence> allPresenceByChantier(Integer chantierId);
    public List<Presence> allPresenceByChantierAndDate(Integer chantierId,Date dd,Date df);    
    public List<Presence> allPresenceBySalarie(Integer salarieId);
    
}
