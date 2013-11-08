
package demosk;
import java.util.Scanner;
import CreateXML.*;
import parser.*;
import sqlite.*;
import java.io.*;
import java.sql.*;

class DemoSK{
    public static void main(String[] args){
        Parser parser;
        String dbPath = "";
        String request = "";
        ResultSet rs = null;
        String currentPath = new String(new File("").getAbsolutePath());
                
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while(true){
        System.out.println("\t\t\t ***Menu*** \n1.Выбрать xml\n2.Создать xml\n3.Показать результат\n4.Выход");
        choice = sc.nextInt();
        switch(choice){
            case 1:
                System.out.println("1.Apple\n2.Store\n3.Time");
                choice = sc.nextInt();
                String path;
                switch(choice){
                    case 1:
                        
                        path = currentPath+"\\Apple.xml";
                        break;
                    case 2:
                        path = currentPath+"\\Store.xml";
                        break;
                    case 3:
                        path = currentPath+"\\Time.x1ml";
                        break;
                    default:
                        path = currentPath+"\\Apple.xml";
                        break;
                }
                parser = new Parser();
                parser.createDomParser(new File(path));
                dbPath = parser.dbName;
                request = parser.request;
                break;
            case 2:
                        CreateXml cr = new CreateXml();
                        cr.buildXml();
                break;
            case 3:
                Sqlite.getResultSet(dbPath, request);
                break;
            case 4:
                return ;
        }
        }
    }
    
}
