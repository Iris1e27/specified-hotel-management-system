package pers;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Room {

    public static final Connection conn = Main.conn;
    public static PreparedStatement pstmt = Main.pstmt;
    public static ResultSet rs = Main.rs;

    public static SimpleDateFormat sdf= Main.sdf;
    public static Calendar cal = Main.cal;
    public static Date now = Main.now;

    public static void all_room_record_state_initialize(){
        try{
            String sql = "select room_record_id, guest_id, room_id, checkin, checkout from room_record where state = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            rs = pstmt.executeQuery();
            while(rs.next()){
                int row = rs.getInt("room_record_id");
                Date checkout = rs.getDate("checkout");
                int compare = checkout.compareTo(now);
                if(compare == -1){ //check out < now
                    sql = "update room_record set state = ? where room_record_id = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, 0); // set not visible
                    pstmt.setInt(2, row);
                    int result = pstmt.executeUpdate();
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String get_room_location(String room_id){
        try{
            String result;
            String sql = "select location from room where room_id = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, room_id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                String location = rs.getString("location");
                result = "The " + room_id + " room's location is " + location;
                return result;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String get_room_state(int type_int, Date checkin, Date checkout){ // return empty rooms
        try{
            String empty_rooms = "";
            String booked_rooms = "";
            if(type_int == 1) empty_rooms = "X01, X02, X11, X12, ";
            if(type_int == 2) empty_rooms = "X03, X04, X09, X10, ";
            if(type_int == 3) empty_rooms = "X05, X06, X07, X08, ";
            if(type_int == 4) empty_rooms = "X13, ";
            String sql = "select guest_id, checkin, checkout, room_id from room_record where state = ? and room_type_int = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            pstmt.setInt(2, type_int);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Date past_checkin = rs.getDate("checkin");
                Date past_checkout = rs.getDate("checkout");
                int compare1 = checkin.compareTo(past_checkout); // >
                int compare2 = checkout.compareTo(past_checkin); // <
                String room_id = rs.getString("room_id");

                if(!(compare1 == 1 || compare2 == -1)){
                    if(empty_rooms.contains(room_id)){
                        int index = empty_rooms.indexOf(room_id + ", ");
                        String str1 = empty_rooms.substring(0, index);
                        String str2 = empty_rooms.substring(index + 5);
                        empty_rooms = str1 + str2;
                    }
                    booked_rooms += room_id + ", ";
                }
            }
            if(! empty_rooms.contains(", ")){
                return "no empty rooms in this period. ";
            }
            else{
                empty_rooms = empty_rooms.substring(0, empty_rooms.length() - 2);
                return empty_rooms;
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String get_room_type(int room_type_int){
        if(room_type_int == 1) return "Large room with double beds";
        else if(room_type_int == 2) return "Large room with a large single bed";
        else if(room_type_int == 3) return "Small room with a single bed";
        else return "VIP room";
    }

    public static String get_room_record(int guest_id){
        try{
            String checkin;
            String checkout;
            int room_type_int;
            String room_type;
            String room_id;
            String result = "";

            String sql = "select checkin, checkout, room_type_int, room_id from room_record where state = ? and guest_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            pstmt.setInt(2, guest_id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                checkin = rs.getString("checkin");
                checkout = rs.getString("checkout");
                room_type_int = rs.getInt("room_type_int");
                room_id = rs.getString("room_id");
                room_type = get_room_type(room_type_int);
                result += "room id: " + room_id +", room type: " + room_type + ", check-in time: " + checkin + ", check-out time: " + checkout + "#";
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String get_room_record(String room_id){
        try{
            String checkin;
            String checkout;
            String result = "";

            String sql = "select checkin, checkout from room_record where state = ? and room_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            pstmt.setString(2, room_id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                checkin = rs.getString("checkin");
                checkout = rs.getString("checkout");
                result += "check-in time: " + checkin + ", check-out time: " + checkout + "#";
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void insert_room_record(int guest_id, String room_id, int room_type_int, Date checkin, Date checkout){
        try{
            String sql = "insert into room_record (guest_id, room_id, room_type_int, checkin, checkout) values (?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, guest_id);
            pstmt.setString(2, room_id);
            pstmt.setInt(3, room_type_int);
            pstmt.setDate(4, checkin);
            pstmt.setDate(5, checkout);
            int result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void delete_room_record(int guest_id, String room_id, Date checkin, Date checkout){
        try{
            String sql = "delete from room_record where guest_id = ? and room_id = ? and checkin = ? and checkout = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, guest_id);
            pstmt.setString(2, room_id);
            pstmt.setDate(3, checkin);
            pstmt.setDate(4, checkout);
            int result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void delete_room_record(String record) {

        String room_id = record.substring(9, 9 + 3);
        int index1 = record.indexOf("check-in");
        int index2 = record.indexOf("check-out");
        Date checkin = Main.convert_string_to_sqldate(record.substring(index1 + 15, index2 - 2));
        Date checkout = Main.convert_string_to_sqldate(record.substring(index2 + 16));
        delete_room_record(Main.guest.guest_id, room_id, checkin, checkout);
    }
}
