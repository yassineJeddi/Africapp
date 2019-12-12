/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;

import java.util.Date;
import java.util.List;
import ma.bservices.tgcc.Entity.AbsenceMensuel;
import ma.bservices.tgcc.Entity.Pointage;
import ma.bservices.tgcc.Entity.PointageMensuelQuinzinier;

/**
 *
 * @author j.allali
 */
public interface AbsenceService {

    public List<AbsenceMensuel> getAbsenceById(int id);

    public List<AbsenceMensuel> getAbsenceById_toCheck(int id_mensuel);

    public void valider_cancel__Absence(List<AbsenceMensuel> l_absences);

    public List<AbsenceMensuel> getMensuelByDate(Date date);

    public List<PointageMensuelQuinzinier> getAbsenceMensuelQById(int id);

    public void updatePointageMQ(AbsenceMensuel pointageMQ);

    public List<Pointage> AddAbsenceIntoList(List<Pointage> l_absence, Pointage _p);

    public void addAbsense(List<Pointage> l_p);
    
     public void add_absense(AbsenceMensuel _a);
     
     
     public List<AbsenceMensuel> getAbsencesChevauchement(AbsenceMensuel absence);
}
