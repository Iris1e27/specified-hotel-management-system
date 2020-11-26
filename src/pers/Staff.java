package pers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Staff {

    public int staff_id;
    public String username;
    public String tele;
    public String login;

    public static final Connection conn = Main.conn;
    public static PreparedStatement pstmt = Main.pstmt;
    public static ResultSet rs = Main.rs;

    Staff(int staff_id, String username, String tele, String login){
        this.staff_id = staff_id;
        this.username = username;
        this.tele = tele;
        this.login = login;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staff_id=" + staff_id +
                ", username='" + username + '\'' +
                ", tele='" + tele + '\'' +
                ", login='" + login + '\'' +
                '}';
    }

    public void update_info(String item, String content){
        try{
            String sql = "update staff set " + item + " = ? where staff_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, content);
            pstmt.setInt(2, staff_id);
            int result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
