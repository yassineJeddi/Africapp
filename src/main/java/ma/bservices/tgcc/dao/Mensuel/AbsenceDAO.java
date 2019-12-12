/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.dao.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.AbsenceMensuel;
import ma.bservices.tgcc.Entity.PointageMensuelQuinzinier;

/**
 *
 * @author j.allali
 */
public interface AbsenceDAO {

    public List<AbsenceMensuel> getAbsenceById(int id);

    public List<AbsenceMensuel> getAbsenceById_tocheck(int id);

    public List<PointageMensuelQuinzinier> getAbsenceMensuelQById(int id);

    public List<AbsenceMensuel> getMensuelByDate(Date date);

    public void updatePointageMQ(AbsenceMensuel pointageMQ);

    public void save(AbsenceMensuel _absense);

    public void delete(AbsenceMensuel _absense);
    
    public List<AbsenceMensuel> getAbsencesChevauchement(AbsenceMensuel absence);
}
