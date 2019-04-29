/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChatBot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Akshay Kumar Dubey
 */
public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocket ss=new ServerSocket(22306);
        while(true)
        {
            Socket s=null;
            try
            {
                s=ss.accept();
                InputStream is=s.getInputStream();
                OutputStream os=s.getOutputStream();
                DataInputStream dis=new DataInputStream(is);
                DataOutputStream dos=new DataOutputStream(os);
                Thread t=new ABC(s,dis,dos);
                t.start();
            }
            catch(Exception e)
            {
                
            }
        }
    }
}
class ABC extends Thread
{
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    public ABC(Socket s1,DataInputStream dis1,DataOutputStream dos1) throws Exception
    {
        s=s1;
        dis=dis1;
        dos=dos1;
    }

    @Override
    public void run() {
        String inp,out = null;
        Connection con=null;
        Statement st=null;
        ResultSet rs = null;
        
        while(true)
        {
            try
            {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String user = "system";
            String p="karansinha";
            try
            {
                con=DriverManager.getConnection(url,user,p);
            }
            catch(SQLException e)
            {
                System.out.println("Class not Found");
            }
            st = con.createStatement();
            rs=st.executeQuery("select * from networking");
            inp=dis.readUTF();
                if(inp.equalsIgnoreCase("exit"))
                {
                    s.close();
                    break;
                }
                int flag=0;
                while(rs.next())
                {
                    if(inp.equalsIgnoreCase(rs.getString(1)))
                    {
                        out=rs.getString(2);
                        flag=1;
                        break;
                    }
                }
                if(flag==0)
                    dos.writeUTF("Sorry! I am in development phase\nAfter Sometime I will surely answer this");
                else
                    dos.writeUTF(out);
                
            }
            catch(Exception e)
            {
               e.printStackTrace();
            }
        }
        try {
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
