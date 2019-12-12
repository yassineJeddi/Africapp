/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel.bean;

import java.util.Comparator;
import ma.bservices.tgcc.Entity.Mensuel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author zakaria.dem
 */
public class LazySorterMensuel implements Comparator<Mensuel> {

    private String sortField;

    private SortOrder sortOrder;

    public LazySorterMensuel(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Mensuel o1, Mensuel o2) {
        try {
            Object value1 = Mensuel.class.getField(this.sortField).get(o1);
            Object value2 = Mensuel.class.getField(this.sortField).get(o1);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
