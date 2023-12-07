package engine;

import java.sql.*;

public class FriendManager {

    public String insertFriend(Connection conn, String my_id , String friend_id) {

        PreparedStatement pSt;

        try {
            pSt = conn.prepareStatement("select count(*) from friend_list where user_id = ? and friend_id = ? ");
            pSt.setString(1, my_id);
            pSt.setString(2, friend_id);

            ResultSet rs = pSt.executeQuery();
            rs.next();
            if(rs.getInt(1)==0){
                pSt = conn.prepareStatement("select count(*) from client where id = ?");
                pSt.setString(1, friend_id);
                rs = pSt.executeQuery();
                rs.next();
                if(rs.getInt(1)==0){
                    return "존재하지 않는 id입니다.";
                }
                else{
                    pSt = conn.prepareStatement("insert into friend_list values(?,?)");
                    pSt.setString(1, my_id);
                    pSt.setString(2, friend_id);
                    pSt.executeUpdate();
                    return "친구 추가 완료.";
                }
            }
            else{
                return "이미 친구입니다.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "database 연결 실패";
    }

    public String deleteFriend(Connection conn, String my_id , String friend_id) {

        PreparedStatement pSt;

        try {
            pSt = conn.prepareStatement("select count(*) from friend_list where user_id = ? and friend_id = ? ");
            pSt.setString(1, my_id);
            pSt.setString(2, friend_id);

            ResultSet rs = pSt.executeQuery();
            rs.next();
            if(rs.getInt(1)==1){
                pSt = conn.prepareStatement("delete from friend_list where user_id = ? and friend_id = ?");
                pSt.setString(1, my_id);
                pSt.setString(2, friend_id);
                pSt.executeUpdate();
                pSt = conn.prepareStatement("delete from friend_list where user_id = ? and friend_id = ?");
                pSt.setString(1, friend_id);
                pSt.setString(2, my_id);
                pSt.executeUpdate();
                return "친구 삭제 완료.";
            }
            else{
                return "친구 목록에 없는 id 입니다.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "database 연결 실패";

    }

}
