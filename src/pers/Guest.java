package pers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Guest {

    public int guest_id;
    public String username;
    public String realname;
    public String passport_id;
    public String tele;
    public String email;
    public String login;

    public static final Connection conn = Main.conn;
    public static PreparedStatement pstmt = Main.pstmt;
    public static ResultSet rs = Main.rs;

    Guest(int guest_id, String username, String realname, String passport_id, String tele, String email, String login){

        this.guest_id = guest_id;
        this.username = username;
        this.realname = realname;
        this.passport_id = passport_id;
        this.tele = tele;
        this.email = email;
        this.login = login;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "guest_id=" + guest_id +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", passport_id='" + passport_id + '\'' +
                ", tele='" + tele + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                '}';
    }

    public void update_info(String item, String content){
        try{
            String sql = "update guest set " + item + " = ? where guest_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, content);
            pstmt.setInt(2, guest_id);
            int result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
