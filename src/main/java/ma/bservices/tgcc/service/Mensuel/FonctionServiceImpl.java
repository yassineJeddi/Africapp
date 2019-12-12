/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.io.Serializable;
import java.util.List;
import ma.bservices.beans.Fonction;
import ma.bservices.beans.TypeDocument;
import ma.bservices.tgcc.dao.Mensuel.FonctionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author j.allali
 */
@Service("fonctionService")
public class FonctionServiceImpl implements FonctionService, Serializable {

    @Autowired
    private FonctionDAO fonctionDAO;

    public FonctionDAO getFonctionDAO() {
        return fonctionDAO;
    }

    public void setFonctionDAO(FonctionDAO fonctionDAO) {
        this.fonctionDAO = fonctionDAO;
    }

    @Override
    public List<Fonction> findAll() {
        return fonctionDAO.findAll();
    }

    @Override
    public List<Fonction> l_FonctionsByTypeDoc(int id) {
        return this.fonctionDAO.l_FonctionsByTypeDoc(id);
    }

    @Override
    public Fonction findByCode(String code) {
        return fonctionDAO.findByCode(code);
    }

    @Override
    public void setListTypeDocsObligatoires(Fonction f, List<TypeDocument> list) {
        if (f.getTypesDocument() != null && f.getTypesDocument().size() > 0) {
            f.getTypesDocument().clear();

        }

        for (TypeDocument typ : list) {
            if (!(f.getTypesDocument().contains(typ))) {
                f.getTypesDocument().add(typ);
            }
        }
        fonctionDAO.update(f);
    }

    @Override
    public void importFonctionDiva() {
        fonctionDAO.importFonctionDiva();
    }
    

}
