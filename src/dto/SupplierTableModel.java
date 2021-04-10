/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author PC
 */
public class SupplierTableModel <E> extends AbstractTableModel
{
    String[] header;
    int[] indexes;
    Vector<Supplier> supplierData;

    public SupplierTableModel(String[] header, int[] indexes) {
        int i=0;
        this.header = new String[header.length];
        for (i = 0; i < header.length; i++) 
        {
            this.header[i]=header[i];
        }
        this.indexes = new int[indexes.length];
        for(i=0; i<header.length; i++)
        {
            this.indexes[i]=indexes[i];
        }
        this.supplierData = new Vector<Supplier>();
    }

    public Vector<Supplier> getData()
    {
        return supplierData;
    }

    public void setData(Vector<Supplier> supplierData) {
        this.supplierData = supplierData;
    }
    
    
    @Override
    public int getRowCount() {
        return supplierData.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(int column) {
        return (column>=0 && column<header.length)?header[column]:"";
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex<0 || rowIndex>=supplierData.size() || columnIndex<0 || columnIndex>=header.length)
            return null;
        Supplier supp = supplierData.get(rowIndex);
        switch(indexes[columnIndex])
        {
            case 0: return supp.getSupplierCode();
            case 1: return supp.getSupplierName();
            case 2: return supp.getSupplierAddress();
            case 3: return supp.isCollaborating();
        }
        return null;
    }
    
}
