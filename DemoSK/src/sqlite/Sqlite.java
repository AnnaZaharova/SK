
package sqlite;
import java.sql.*;

public class Sqlite {

    public static boolean getResultSet(String path,String request){
        Connection c = null;
        Statement stmt = null;
        int s = 0;
    try {
        Class.forName("org.sqlite.JDBC");

        c = DriverManager.getConnection("jdbc:sqlite:"+path);


      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery(request);
      
      while(rs.next()){
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
                System.out.println( rs.getMetaData().getColumnName(i)+" = "+rs.getString(rs.getMetaData().getColumnName(i)));
            }
            System.out.println();
            }
    
        rs.close();
        stmt.close();
        c.close();
        return true;
    }catch(Exception e){
        e.printStackTrace();
        return false;
    }
    }
}
