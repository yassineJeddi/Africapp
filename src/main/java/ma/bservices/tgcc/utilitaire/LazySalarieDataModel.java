/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import ma.bservices.beans.Salarie;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author admin
 */
public class LazySalarieDataModel extends LazyDataModel<Salarie> {
    private List<Salarie> datasource;
     
    public LazySalarieDataModel(List<Salarie> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public Salarie getRowData(String rowKey) {
        for(Salarie salarie : datasource) {
            if(salarie.getId().equals(rowKey))
                return salarie;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Salarie salarie) {
        return salarie.getId();
    }
 
    @Override
    public List<Salarie> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Salarie> data = new ArrayList<Salarie>();
 
        //filter
        for(Salarie salarie : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(salarie.getClass().getField(filterProperty).get(salarie));
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(salarie);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}
