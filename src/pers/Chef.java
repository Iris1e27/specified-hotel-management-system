package pers;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Chef {

    int chef_id;
    String realname;
    String dishes;
    String workday;

    public static final Connection conn = Main.conn;
    public static PreparedStatement pstmt = Main.pstmt;
    public static ResultSet rs = Main.rs;

    public static SimpleDateFormat sdf= Main.sdf;
    public static Calendar cal = Main.cal;
    public static Date now = Main.now;

    public Chef(){
        chef_id = 0;
        realname = "";
        dishes = "";
        workday = "";
    }

    public Chef(int chef_id, String realname, String dishes, String workday){
        this.chef_id = chef_id;
        this.realname = realname;
        this.dishes = dishes;
        this.workday = workday;
    }

    public static void all_chef_initialize(){
        try{
            Main.chefs.add(new Chef());
            String sql = "select * from chef";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                int chef_id = rs.getInt("chef_id");
                String realname = rs.getString("realname");
                String dishes = rs.getString("dishes");
                String workday = rs.getString("workday");
                Chef new_chef = new Chef(chef_id, realname, dishes, workday);
                Main.chefs.add(new_chef);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String get_meals(String weekday){
        String meals = "";
        for (Chef chef: Main.chefs) {
            if(chef.workday.contains(weekday)){
                String[] dishes = chef.dishes.split(", ");
                for(String dish: dishes){
                    if(! meals.contains(dish)){
                        meals += dish + ", ";
                    }
                }
            }
        }
        meals = meals.substring(0, meals.length() - 2);
        return meals;
    }

    public static String get_chefs(String meals){
        String chefs = "";
        String[] dishes = meals.split(", ");
        for(String dish: dishes){
            for(Chef cooker: Main.chefs){
                if(cooker.dishes.contains(dish)){
                    if(! chefs.contains(cooker.chef_id + "")){
                        chefs += cooker.chef_id + ", ";
                        break;
                    }
                }
            }
        }
        chefs = chefs.substring(0, chefs.length() - 2);
        return chefs;
    }

    public static String get_food_record(int guest_id){
        try{
            String dishes;
            String workday;
            String result = "";

            String sql = "select chef_id_list, dishes, workday from food_record where guest_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, guest_id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                dishes = rs.getString("dishes");
                workday = rs.getString("workday");
                result += "dishes: " + dishes +", workday: " + workday + "#";
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void insert_food_record(int guest_id, String chef_id_list, String dishes, String workday){
        try{
            String sql = "insert into food_record (guest_id, chef_id_list, dishes, workday) values (?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, guest_id);
            pstmt.setString(2, chef_id_list);
            pstmt.setString(3, dishes);
            pstmt.setString(4, workday);
            int result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void delete_food_record(int guest_id, String dishes, String workday){
        try{
            String sql = "delete from food_record where guest_id = ? and dishes = ? and workday = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, guest_id);
            pstmt.setString(2, dishes);
            pstmt.setString(3, workday);
            int result = pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void delete_food_record(String record) {

        int index = record.indexOf(", workday: ");
        String dishes = record.substring(8, index);
        String workday = record.substring(index + 11);
        delete_food_record(Main.guest.guest_id, dishes, workday);
    }
}
