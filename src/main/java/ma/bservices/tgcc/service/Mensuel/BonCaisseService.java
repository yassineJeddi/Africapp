/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.util.List;
import ma.bservices.tgcc.Entity.BonCaisse;
import ma.bservices.tgcc.Entity.LoyerSalarie;

/**
 *
 * @author j.allali
 */
public interface BonCaisseService {

    public List<BonCaisse> findAll();

    public void saveBonCaisse(BonCaisse BC);

    public void updateBonCaisse(BonCaisse BC);

    public BonCaisse getbcById(Long id);

    public LoyerSalarie AfficherListMensuelByidLoyer(Long id);

    public List<BonCaisse> getbcByIdLoyerSalarie(Long id);

    public void removeBCChantierByLC(BonCaisse BC);

    public BonCaisse getbcByIdLoyerChantier(Long id);

    public List<BonCaisse> getbcByIdLoyerChan(Long id);

    public BonCaisse getbcByIdBonCaisse(Long id);
    
    
}
