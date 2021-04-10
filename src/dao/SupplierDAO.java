/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Supplier;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class SupplierDAO implements Serializable{

    public SupplierDAO() throws Exception {
    }
    
    public static List<Supplier> getAllSuppliers() throws Exception
    {
        List<Supplier> supplierList = new ArrayList<>();
        Connection c = null;
        Statement sm = null;
        ResultSet rs = null;
        try 
        {
            c = db_utils.DBUtils.MakeConnection();
            sm = c.createStatement();
            rs = sm.executeQuery("Select supCode, supName, address, collaborating From tblSuppliers");
            while(rs.next())
            {
                String suppCode = rs.getString("supCode");
                String suppName = rs.getString("supName");
                String suppAddress = rs.getString("address");
                String collaboratingString = rs.getString("collaborating");
                boolean collaborating = (collaboratingString.equalsIgnoreCase("1"));
                Supplier supplier = new Supplier(suppCode, suppName, suppAddress, collaborating);
                supplierList.add(supplier);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
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
        return supplierList;
    }
    
    public Vector<Supplier> loadSuppliers() throws Exception
    {
        Vector<Supplier> row = new Vector();
        for(Supplier supp : getAllSuppliers())
        {
            row.add(new Supplier(supp.getSupplierCode(), supp.getSupplierName(), supp.getSupplierAddress(), supp.isCollaborating()));
        }
        return row;
    }
    
    public String loadSupplierName(String supCode) throws Exception
    {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try 
        {
            c = db_utils.DBUtils.MakeConnection();
            ps = c.prepareStatement("Select supName From tblSuppliers Where supCode = ?");
            ps.setString(1, supCode);
            rs = ps.executeQuery();
            if(rs.next())
            {
                return supCode + " - " + rs.getString("supName");
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
    
    public static int insertSupplier(Supplier s) throws Exception
    {
        Connection c = null;
        PreparedStatement ps = null;
        try 
        {
            c = db_utils.DBUtils.MakeConnection();
            ps = c.prepareStatement("Insert tblSuppliers Values(?,?,?,?)");
            ps.setString(1, s.getSupplierCode());
            ps.setString(2, s.getSupplierName());
            ps.setString(3, s.getSupplierAddress());
            ps.setString(4, s.isCollaborating()==true?"1":"0");   
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
    
    public static int updateSupplier(Supplier s) throws Exception
    {
        Connection c = null;
        PreparedStatement ps = null;
        try 
        {
            c = db_utils.DBUtils.MakeConnection();
            ps = c.prepareStatement("Update tblSuppliers Set supName = ?, address = ?, collaborating = ? Where supCode = ?");
            ps.setString(4, s.getSupplierCode());
            ps.setString(1, s.getSupplierName());
            ps.setString(3, s.isCollaborating()==true?"1":"0");
            ps.setString(2, s.getSupplierAddress());
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
    
    public static Supplier getSupplierByCode(String code) throws Exception
    {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try 
        {
            c = db_utils.DBUtils.MakeConnection();
            ps = c.prepareStatement("Select supCode, supName, address, collaborating From tblSuppliers Where supCode = ?");
            ps.setString(1, code);
            rs = ps.executeQuery();
            if(rs.next())
            {
                Supplier s = new Supplier(rs.getString("supCode"), rs.getString("supName"), rs.getString("address"), rs.getBoolean("collaborating"));
                return s;
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
    
    public static int deleteSupplier(String code) throws Exception
    {
        Connection c = null;
        PreparedStatement ps = null;
        try 
        {
            c = db_utils.DBUtils.MakeConnection();
            ps = c.prepareStatement("Delete From tblSuppliers Where supCode = ?");
            ps.setString(1, code);
            return ps.executeUpdate();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static boolean validateSupplierData(boolean addNew, String supCode, String supName, String address)
    {
        if(addNew == true)
        {
            if(supCode.length()==0 || supCode.length()>10)
            {
                JOptionPane.showMessageDialog(null, "Code must be not null or less than 10 character!");
                return false;
            }
            if(!supCode.matches("\\w{1,10}$"))
            {
                JOptionPane.showMessageDialog(null, "Code can not contain special character!");
                return false;
            }
        }
        if(supName.length()==0 || supName.length()>50)
        {
            JOptionPane.showMessageDialog(null, "Name must be not null or less than 50 character!");
                return false;
        }
        if(address.length()==0 || address.length()>50)
        {
            JOptionPane.showMessageDialog(null, "Address must be not null or less than 50 character!");
                return false;
        }
        return true;
    }
}
