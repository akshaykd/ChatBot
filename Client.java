/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Karan;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author Akshay Kumar Dubey
 */
public class Client extends Thread implements ActionListener{
    Scanner sc=new Scanner(System.in);
    JFrame fr;
    JButton b;
    JTextArea area;
    JTextField text;
    JScrollPane scroll;
    JLabel label;
    JPanel p1,p2;
    Socket clientsocket;
    DataInputStream dis;
    DataOutputStream dos;
    String str;
     public void Gui()
    {
        fr=new JFrame("Client Program");
        fr.setSize(400,400);
        fr.setLayout(null);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p1=new JPanel();
        p1.setBounds(0,0,400,300);
        p1.setLayout(new FlowLayout());
        p2=new JPanel();
        p2.setBounds(0,300,450,150);
        p2.setLayout(new FlowLayout());
        fr.add(p1);
        fr.add(p2);
        area=new JTextArea(18,32);
        area.setEditable(false);
        p1.add(area);
        scroll=new JScrollPane(area);
	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        p1.add(scroll);
        label=new JLabel("Client");
        p2.add(label);
        text=new JTextField("",20);
        p2.add(text);
        b=new JButton("Submit");
        b.addActionListener(this);
        p2.add(b);
    }
     public static void main(String[] args)
    {
	Client ec=new Client();
	ec.Gui();
	ec.start();
    }

    @Override
    public void run() {
        try {
            clientsocket=new Socket("localhost",22306);
            dis=new DataInputStream(clientsocket.getInputStream());
            dos=new DataOutputStream(clientsocket.getOutputStream()); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
     
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==b)
        {
            try {
               str=text.getText();
               String str1=str;
               dos.writeUTF(str);
               str=dis.readUTF();
               area.setText(area.getText()+"Lanny >>"+str1+"\n"+"Client >>"+str+"\n");
               text.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            
        }

    }
}
