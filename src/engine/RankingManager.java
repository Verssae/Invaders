package engine;
import java.sql.*;
import java.util.ArrayList;

public class RankingManager {

    public ArrayList<ArrayList<String>> getRankingData(Connection conn,int difficulty){
        ArrayList<ArrayList<String>> rankingData = new ArrayList<ArrayList<String>>();
        try{
            PreparedStatement pSt = conn.prepareStatement("select * from ranking(?)");
            pSt.setInt(1,difficulty);
            ResultSet rs =pSt.executeQuery();
            while(rs.next()){
                ArrayList<String> data = new ArrayList<String>();
                data.add(String.valueOf(rs.getInt(1)));
                data.add(rs.getString(2));
                data.add(rs.getString(3));
                data.add(String.valueOf(rs.getInt(4)));
                rankingData.add(data);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rankingData;
    }

    public static void main(String[] args) {
        DatabaseConnect database = new DatabaseConnect();
        Connection conn = database.connect();
        RankingManager manager = new RankingManager();
        ArrayList<ArrayList<String>> test = manager.getRankingData(conn,0);
        for(int i=0 ; i<test.size() ; i++){
            for(int j=0 ; j<4 ; j++){
                System.out.print(test.get(i).get(j));
            }
            System.out.println();
        }
    }
}
