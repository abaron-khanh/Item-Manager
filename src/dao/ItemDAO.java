/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Item;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class ItemDAO implements Serializable{
    
    public static List<Item> getAllItems() throws SQLException
    {
        List<Item> itemList = new ArrayList<>();
        Connection c = null;
        Statement sm = null;
        ResultSet rs = null;
        try 
        {
            c = db_utils.DBUtils.MakeConnection(); 
            sm = c.createStatement();
            rs = sm.executeQuery("Select itemCode, itemName, unit, price, supplying, supCode From tblItems");
            while(rs.next())
            {
                String itemCode = rs.getString("itemCode");
                String itemName = rs.getString("itemName");
                String itemUnit = rs.getString("unit");
                float price = rs.getFloat("price");
                String supplyingString = rs.getString("supplying");
                boolean supplying = (supplyingString.equalsIgnoreCase("1"));
                String supCode = rs.getString("supCode");
                Item item = new Item(itemCode, itemName, itemUnit, price, supCode, supplying);
                itemList.add(item); 
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }
        finally 
        {
            if(rs!=null)
                rs.close();
            if(sm!=null)
                sm.close();
            if(c!=null)
                c.close();
        }
        return itemList;
    }
            
    
    public Vector<Item> loadItems() throws Exception
    {
        Vector<Item> row = new Vector();
        for(Item item : getAllItems())
        {
            row.add(new Item(item.getItemCode(), item.getItemName(), item.getItemUnit(), item.getPrice(), item.getSupplier(), item.isSupplying()));
        }
        return row;
    }
    
    public static int insertItem(Item i) throws Exception
    {
        Connection c = null;
        PreparedStatement ps = null;
        try 
        {
            c = db_utils.DBUtils.MakeConnection();
            ps = c.prepareStatement("Insert tblItems Values(?,?,?,?,?,?)");
            ps.setString(1, i.getItemCode());
            ps.setString(2, i.getItemName());
            ps.setString(3, i.getItemUnit());
            ps.setFloat(4, i.getPrice());
            ps.setString(6, i.getSupplier());
            ps.setString(5, i.isSupplying()==true?"1":"0");   
            return ps.executeUpdate();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            if(ps!=null)
                ps.close();
            if(c!=null)
                c.close();
        }
        return 0;
    }
    
    public static int updateItem(Item i) throws Exception
    {
        Connection c = null;
        PreparedStatement ps = null;
        try 
        {
            c = db_utils.DBUtils.MakeConnection();
            ps = c.prepareStatement("Update tblItems Set itemName = ?, supCode = ?, unit = ?, price = ?, supplying = ? Where itemCode = ?");
            ps.setString(6, i.getItemCode());
            ps.setString(2, i.getSupplier());
            ps.setString(1, i.getItemName());
            ps.setString(3, i.getItemUnit());
            ps.setString(5, i.isSupplying()==true?"1":"0");
            ps.setFloat(4, i.getPrice());
            return ps.executeUpdate();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            if(ps!=null)
                ps.close();
            if(c!=null)
                c.close();
        }
        return 0;
    }
    public static Item getItemByCode(String code) throws Exception
    {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try 
        {
            c = db_utils.DBUtils.MakeConnection();
            ps = c.prepareStatement("Select itemCode, itemName, unit, price, supCode, supplying From tblItems Where itemCode = ?");
            ps.setString(1, code);
            rs = ps.executeQuery();
            if(rs.next())
            {
                Item e = new Item(rs.getString("itemCode"), rs.getString("itemName"), rs.getString("unit"), rs.getFloat("price"), rs.getString("supCode"), rs.getBoolean("supplying"));
                return e;
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(c!=null)
                c.close();
        }
        return null;
    }
    
    public static int deleteItem(String code) throws Exception
    {
        Connection c = null;
        PreparedStatement ps = null;
        try 
        {
            c = db_utils.DBUtils.MakeConnection();
            ps = c.prepareStatement("Delete From tblItems Where itemCode = ?");
            ps.setString(1, code);
            return ps.executeUpdate();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static boolean validateItemData(boolean addNew, String itemCode, String itemName, String unit, String price)
    {
        if(addNew == true)
        {
            if(itemCode.length()==0 || itemCode.length()>10)
            {
                JOptionPane.showMessageDialog(null, "Code must be not null or less than 10 character!");
                return false;
            }
            if(!itemCode.matches("\\w{1,10}$"))
            {
                JOptionPane.showMessageDialog(null, "Code can not contain special character!");
                return false;
            }
        }
        if(itemName.length()==0 || itemName.length()>50)
        {
            JOptionPane.showMessageDialog(null, "Name must be not null or less than 50 character!");
                return false;
        }
        if(unit.length()==0 || unit.length()>10)
        {
            JOptionPane.showMessageDialog(null, "Unit must be not null or less than 50 character!");
            return false;
        }
//        if(!check.matches("\\w{1,50}$"))
//        {
//            JOptionPane.showMessageDialog(this, "Unit can not contain special character!");
//            return false;
//        }
        if(!price.matches("^[0-9]{1,}[.]?[0-9]{0,}"))
        {
            JOptionPane.showMessageDialog(null, "Price must be a number!");
            return false;
        }
        if(Float.parseFloat(price)==0)
        {
            JOptionPane.showMessageDialog(null, "Price must be greater than 0!");
            return false;
        }
        return true;
    }
}
