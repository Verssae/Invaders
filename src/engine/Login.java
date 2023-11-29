package engine;

import java.sql.*;

public class Login {
    private  final String url = "jdbc:postgresql://localhost:5432/invaders";
    private  final String user = "postgres";
    private  final String password = "89456951asd!";
    public Connection connect () {
        Connection conn = null ;
        try {
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("aaaaaa");
            System.out.println(e.getMessage());
        }

        return conn;
    }
    public void displayPlayer(Connection conn){
        System.out.println("=======================");
        System.out.println("Player");
        System.out.println("=======================");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM player");
            while(rs.next()){
                System.out.println("player: "+rs.getString(3));
            }
            rs.close();
            st.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String loginCheck(Connection conn, String id , String password){
        System.out.println("=======================");
        System.out.println("Login");
        System.out.println("=======================");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM player");
            String id_data = new String();
            String password_data = new String();
            while(rs.next()){
                id_data= rs.getString(1);
                password_data = rs.getString(2);
                if(id.equals(id_data) && password.equals(password_data)){
                    return rs.getString(3);
                }
            }
            rs.close();
            st.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "fail";
    }

    public void scoreUpdate(Connection conn,String username , int score,int difficulty){
        System.out.println("=======================");
        System.out.println("Score update");
        System.out.println("=======================");
        PreparedStatement pSt;
        if(difficulty==0){
            try {
                pSt = conn.prepareStatement("select count(*) from easy_score where username = ?");
                pSt.setString(1,username);
                ResultSet rs = pSt.executeQuery();
                rs.next();
                if(rs.getInt(1)==0){
                    pSt = conn.prepareStatement("insert into easy_score values(?,?)");
                    pSt.setString(1,username);
                    pSt.setInt(2,score);
                    pSt.executeUpdate();
                }
                else{
                    pSt = conn.prepareStatement("update easy_score set score = ? where username=? and score<?");
                    pSt.setInt(1,score);
                    pSt.setString(2,username);
                    pSt.setInt(3,score);
                    pSt.executeUpdate();
                }
                pSt.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(difficulty==1){
            try {
                pSt = conn.prepareStatement("select count(*) from normal_score where username = ?");
                pSt.setString(1,username);
                ResultSet rs = pSt.executeQuery();
                rs.next();
                if(rs.getInt(1)==0){
                    pSt = conn.prepareStatement("insert into normal_score values(?,?)");
                    pSt.setString(1,username);
                    pSt.setInt(2,score);
                    pSt.executeUpdate();
                }
                else{
                    pSt = conn.prepareStatement("update normal_score set score = ? where username=? and score<?");
                    pSt.setInt(1,score);
                    pSt.setString(2,username);
                    pSt.setInt(3,score);
                    pSt.executeUpdate();
                }
                pSt.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(difficulty==2){
            try {
                pSt = conn.prepareStatement("select count(*) from hard_score where username = ?");
                pSt.setString(1,username);
                ResultSet rs = pSt.executeQuery();
                rs.next();
                if(rs.getInt(1)==0){
                    pSt = conn.prepareStatement("insert into hard_score values(?,?)");
                    pSt.setString(1,username);
                    pSt.setInt(2,score);
                    pSt.executeUpdate();
                }
                else{
                    pSt = conn.prepareStatement("update hard_score set score = ? where username=? and score<?");
                    pSt.setInt(1,score);
                    pSt.setString(2,username);
                    pSt.setInt(3,score);
                    pSt.executeUpdate();
                }
                pSt.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(difficulty==3){
            try {
                pSt = conn.prepareStatement("select count(*) from hardcore_score where username = ?");
                pSt.setString(1,username);
                ResultSet rs = pSt.executeQuery();
                rs.next();
                if(rs.getInt(1)==0){
                    pSt = conn.prepareStatement("insert into hardcore_score values(?,?)");
                    pSt.setString(1,username);
                    pSt.setInt(2,score);
                    pSt.executeUpdate();
                }
                else{
                    pSt = conn.prepareStatement("update hardcore_score set score = ? where username=? and score<?");
                    pSt.setInt(1,score);
                    pSt.setString(2,username);
                    pSt.setInt(3,score);
                    pSt.executeUpdate();
                }
                pSt.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

    }
    public void disconnect(Connection conn){
        try {
            conn.close();
            System.out.println("Connection is closed successfully.");
        } catch (SQLException e ){
            System.out.println(e.getMessage());
        }
    }


}
