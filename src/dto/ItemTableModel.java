/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import dao.SupplierDAO;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author PC
 */
public class ItemTableModel <E> extends AbstractTableModel
{
    String[] header;
    int[] indexes;
    Vector<Item> itemData;
    SupplierDAO suppDAO;
    
    public ItemTableModel(String[] header, int[] indexes) throws Exception {
        suppDAO = new SupplierDAO();
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
        this.itemData = new Vector<Item>();
    }
    
    public Vector<Item> getData()
    {
        return itemData;
    }

    public void setData(Vector<Item> itemData) {
        this.itemData = itemData;
    }

    @Override
    public int getRowCount() {
        return itemData.size();
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
        if(rowIndex<0 || rowIndex>=itemData.size() || columnIndex<0 || columnIndex>=header.length)
            return null;
        Item item = itemData.get(rowIndex);
        switch(indexes[columnIndex])
        {
            case 0: return item.getItemCode();
            case 1: return item.getItemName();
            case 2: {
            try 
            {
                return suppDAO.loadSupplierName(item.getSupplier());
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(ItemTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            case 3: return item.getItemUnit();
            case 4: return item.getPrice();
            case 5: return item.isSupplying();
        }
        return null;
    }
    
    
}
