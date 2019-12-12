/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import ma.bservices.tgcc.Entity.Mensuel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author zakaria.dem
 */
public class LazyMensuelDataModel extends LazyDataModel<Mensuel> {

    private List<Mensuel> datasource;

    public LazyMensuelDataModel(List<Mensuel> datasource) {
        this.datasource = datasource;
    }

    @Override
    public Mensuel getRowData(String rowKey) {
        for (Mensuel car : datasource) {
            if (car.getId().equals(rowKey)) {
                return car;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Mensuel car) {
        return car.getId();
    }

    @Override
    public List<Mensuel> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Mensuel> data = new ArrayList<Mensuel>();

        //filter
        for (Mensuel mensuel : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(mensuel.getClass().getField(filterProperty).get(mensuel));

                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                data.add(mensuel);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazySorterMensuel(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }

}
