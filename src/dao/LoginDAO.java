/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.User;
import java.awt.CardLayout;
import java.awt.Container;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author PC
 */
public class LoginDAO implements Serializable{
    
    public LoginDAO()
    {
        try {
            getAllUsers();
        } catch (SQLException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<User> getAllUsers() throws SQLException 
    {
        List<User> userList = new ArrayList<>();
        Connection c = null;
        Statement sm = null;
        ResultSet rs = null;
        try
        {
            c = db_utils.DBUtils.MakeConnection(); 
            sm = c.createStatement();
            rs = sm.executeQuery("Select userID, password From tblUsers");
            while(rs.next())
            {
                String userID = rs.getString("userID");
                String password = rs.getString("password");
                User user = new User(userID, password);
                userList.add(user); 
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
        return userList;
    }
    
    public boolean checkLogin(String userName, String password)
    {
        try 
        {
            List<User> checkList = getAllUsers();
            for (int i = 0; i < checkList.size(); i++)
            {
                if(checkList.get(i).getUserID().equals(userName) && checkList.get(i).getPassword().equals(password))
                    return true;
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void changePanel(Container parent, String panel)
    {
        CardLayout c = (CardLayout) parent.getLayout();
        c.show(parent, panel);
    }
    
    
}
