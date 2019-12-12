/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.dao;

import java.util.List;
import ma.bservices.beans.Chantier;
import ma.bservices.beans.NiveauFonction;
import ma.bservices.beans.Salarie;
import ma.bservices.tgcc.Entity.Organigrame;

/**
 *
 * @author airaamane
 */
public interface OrganigrameDAO {

    public List<Organigrame> findByChantier(Chantier chantier);

    public List<Organigrame> findDistinctNiveauByChantier(Chantier chantier);

    public List<Organigrame> findByChantierChef(Chantier chantier);

    public List<Integer> findByChantierNiveau(Chantier chantier, NiveauFonction niveau);

    public List<Organigrame> findOrgsByChantierNiveau(Chantier chantier, NiveauFonction niveau);

    public boolean deleteOrgsByChantierNiveau(Chantier chantier, NiveauFonction niveau);

    public Organigrame findByChantierNiveauSalarie(Chantier chantier, NiveauFonction niveau, Salarie salarie);

    public List<Organigrame> findByChantierNiveauChef(Chantier chantier, NiveauFonction niveau);

    public void save(Organigrame org);

    public void update(Organigrame org);

    public void delete(Organigrame org);

}
