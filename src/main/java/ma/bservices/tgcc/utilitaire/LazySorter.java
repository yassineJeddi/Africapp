/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

import java.util.Comparator;
import ma.bservices.beans.Salarie;
import org.primefaces.model.SortOrder;

/**
 *
 * @author admin
 */
public class LazySorter implements Comparator<Salarie> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(Salarie sal1, Salarie sal2) {
        try {
            Object value1 = Salarie.class.getField(this.sortField).get(sal1);
            Object value2 = Salarie.class.getField(this.sortField).get(sal2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
