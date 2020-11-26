package pers;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Main {

    private static String localURL = "jdbc:mysql://localhost:3306/cse103?user=root";
    private static String csseURL = "jdbc:mysql://csse-mysql:3306/jingtianzhang18?user=JingtianZhang18&password=123";
    private static String csseAltURL = "jdbc:mysql://csse-mysql.xjtlu.edu.cn:3306/jingtianzhang18?user=JingtianZhang18&password=123";

    public static Connection conn = null;
    public static PreparedStatement pstmt = null;
    public static ResultSet rs = null;

    public static Guest guest = null;
    public static Staff staff = null;
    public static ArrayList<Chef> chefs = new ArrayList<>();

    public static SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    public static Calendar cal = Calendar.getInstance();
    public static Date now = new Date(cal.getTime().getTime());

    static Scanner input = new Scanner(System.in);

    public static void get_connection(){
        try{
            conn = DriverManager.getConnection(csseAltURL);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void close_all(){
        if(rs != null)
            try{
                rs.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        if(pstmt != null)
            try{
                pstmt.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        if(conn != null)
            try{
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
    }

    public static void print_line(){
        System.out.println("--------------------------");
    }

    public static void press_to_continue(){
        System.out.println("Please type anything else to continue. ");
        input.nextLine();
    }

    public static void welcome() {
        System.out.println("Welcome to Sunny Isle's hotel management system! ");
    }

    public static String choose_identity(){
        System.out.println("Please choose your identity: ");
        System.out.println("1. Guest. ");
        System.out.println("2. Staff. ");
        System.out.println("If you want to quit, please type anything else. ");
        String temp = input.nextLine();
        if(temp.contains("1")) return "guest";
        else if(temp.contains("2")) return "staff";
        else return "quit";

    }

    public static String choose_operation(){
        System.out.println("Please choose your operation: ");
        System.out.println("1. Log in. ");
        System.out.println("2. Register. ");
        System.out.println("3. Log out. ");
        do{
            String temp = input.nextLine();
            if(temp.contains("1")) return "login";
            else if(temp.contains("2")) return "register";
            else if(temp.contains("3")) return "logout";
            else System.out.println("Illegal number input, please type again. ");
        }while(true);
    }

    public static void login_guest(){
        try{
            String username = null;
            String login = null;
            int guest_id = 0;
            String realname = null;
            String passport_id = null;
            String tele = null;
            String email = null;

            do{
                System.out.println("Username: ");
                username = input.nextLine();
                if(username.equals("0")){
                    logout(); break;
                }
                String sql = "select guest_id, realname, passport_id, tele, email, login from guest where username = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    rs.beforeFirst(); break;
                }
                System.out.println("The username not found, please type again. ");
                System.out.println("If you want to quit, please type 0. ");

            }while(true);

            do{
                if(username.equals("0")) break;
                System.out.println("Password: ");
                String password = input.nextLine();
                while(rs.next()) {
                    login = rs.getString("login");
                    guest_id = rs.getInt("guest_id");
                    realname = rs.getString("realname");
                    passport_id = rs.getString("passport_id");
                    tele = rs.getString("tele");
                    email = rs.getString("email");
                }
                if(password.equals("0")){
                    System.out.println("Please type your email to retrieve the password: ");
                    String email_temp = input.nextLine();
                    if(email_temp.equals(email)){
                        System.out.println("Retrieve the password successfully. ");
                        System.out.println("Your password: " + login + ", please type again. ");
                    }
                }
                else if(password.equals(login)){
                    System.out.println("Log in successfully. ");
                    guest = new Guest(guest_id, username, realname, passport_id, tele, email, login);
                    guest_menu();
                    break;
                }
                else{
                    System.out.println("Incorrect password, please type again. ");
                    System.out.println("If you forget your password, please type 0. ");
                }

            }while(true);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void update_info_guest(){
        System.out.println("Please type items you want to update: ");
        System.out.println("1. Real name. ");
        System.out.println("2. Passport id. ");
        System.out.println("3. Telephone number. ");
        System.out.println("4. Email. ");
        System.out.println("5. Password. ");
        System.out.println("If you do not want to update anything, type anything that do not contain these numbers. ");
        String items = input.nextLine();
        int flag = 0;
        if(items.contains("1")){
            System.out.println("New real name: ");
            String newone = input.nextLine();
            guest.update_info("realname", newone);
            System.out.println("Update real name successfully. "); flag++;
        }
        if(items.contains("2")){
            System.out.println("New passport id: ");
            String newone = input.nextLine();
            guest.update_info("passport_id", newone);
            System.out.println("Update passport id successfully. "); flag++;
        }
        if(items.contains("3")){
            System.out.println("New telephone number: ");
            String newone = input.nextLine();
            guest.update_info("tele", newone);
            System.out.println("Update telephone number successfully. "); flag++;
        }
        if(items.contains("4")){
            System.out.println("New email: ");
            String newone = input.nextLine();
            guest.update_info("email", newone);
            System.out.println("Update email successfully. "); flag++;
        }
        if(items.contains("5")){
            System.out.println("New password: ");
            String newone = input.nextLine();
            guest.update_info("login", newone);
            System.out.println("Update password successfully. "); flag++;
        }
        if(flag == 0){
            System.out.println("Nothing updated. ");
        }
    }

    public static Date convert_string_to_sqldate(String date){
        try{
            Date sqldate = new Date(sdf.parse(date).getTime());
            return sqldate;
        }catch (ParseException e){
            System.out.println("Illegal date input, please type again. ");
        }
        return null;
    }

    public static void check_room_guest(){
        String[] records;

        System.out.println("There are your room records: ");
        String result = Room.get_room_record(guest.guest_id);
        if(result == ""){
            System.out.println("Room record not found. ");
            System.out.println("Check booked rooms successfully. ");
            return;
        }
        records = result.split("#");
        int length = records.length;
        for(int i=0; i<length; i++){
            System.out.println((i+1) + ". " + records[i]);
        }
        System.out.println("Check booked rooms successfully. ");
    }

    public static void book_room_guest(){
        Date checkin = null;
        Date checkout = null;
        int type = 0;
        String choose_room = null;

        do{
            System.out.println("Please type your check-in time (yyyy-MM-dd): ");
            String in = input.nextLine();

            checkin = convert_string_to_sqldate(in);
            int compare = checkin.compareTo(now);

            if(compare == -1){ //past
                System.out.println("The check-in time is in the past, please type again. ");
            }
            else{ //now and future
                break;
            }
        }while(true);

        do{
            System.out.println("Please type your check-out time (yyyy-MM-dd): ");
            String out = input.nextLine();

            checkout = convert_string_to_sqldate(out);
            int compare = checkout.compareTo(checkin);

            if(compare == -1 || compare == 0){ //past or now
                System.out.println("The check-out time is in the past, please type again. ");
            }
            else{ //future
                break;
            }

        }while(true);

        String[] type_rooms_state = new String [5];
        for(int i=1; i<=4; i++){
            type_rooms_state[i] = Room.get_room_state(i, checkin, checkout);
        }

        do{
            System.out.println("Please choose a room type you want to book: ");
            System.out.println("1. Large room with double beds: " + type_rooms_state[1]);
            System.out.println("2. Large room with a large single bed: " + type_rooms_state[2]);
            System.out.println("3. Small room with a single bed: " + type_rooms_state[3]);
            System.out.println("4. VIP room: " + type_rooms_state[4]);
            type = input.nextInt();
            input.nextLine();
            if(type_rooms_state[type].contains("X")) break;
            else System.out.println("Illegal type input, please type again. ");
        }while(true);

        do{
            System.out.println("Please select the number you want according to the room location below: ");
            String[] type_rooms = type_rooms_state[type].split(", ");
            int length = type_rooms.length;
            for(int i = 0; i < length; i++){
                System.out.println((i+1) + ". " + Room.get_room_location(type_rooms[i]));
            }
            int choose = input.nextInt();
            input.nextLine();
            if(choose >= 1 && choose <= length){
                choose_room = type_rooms[choose - 1];
                break;
            }
            else System.out.println("Illegal number input, please type again. ");
        }while(true);

        Room.insert_room_record(guest.guest_id, choose_room, type, checkin, checkout);

        System.out.println("Book this empty room successfully. ");
    }

    public static void cancel_room_guest(){
        String[] records;
        int choose = 0;

        do {
            System.out.println("Please choose the record number you want to cancel: ");
            String result = Room.get_room_record(guest.guest_id);
            if(result == ""){
                System.out.println("Room record that can be canceled not found. ");
                return;
            }
            records = result.split("#");
            int length = records.length;
            for(int i=0; i<length; i++){
                System.out.println((i+1) + ". " + records[i]);
            }
            choose = input.nextInt();
            input.nextLine();
            if(choose >= 1 && choose <= length) break;
            else System.out.println("Illegal number input, please type again. ");
        }while(true);

        Room.delete_room_record(records[choose - 1]);

        System.out.println("Cancel this booked room successfully. ");
    }

    public static String date_to_week(Date datetime) {
        String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        java.util.Date datet = null;
        datet = (java.util.Date) datetime;
        cal.setTime(datet);

        int week = cal.get(Calendar.DAY_OF_WEEK) - 1; // someday of a week
        if (week < 0) week = 0;
        return weekDays[week];
    }

    public static String date_to_week(String datetime){
        String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        java.util.Date datet = null;
        try {
            datet = sdf.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (week < 0)
            week = 0;
        return weekDays[week];

    }

    public static int get_day_differ(Date startday, Date endday){
        try{
            long start = sdf.parse(sdf.format(startday)).getTime();
            long end = sdf.parse(sdf.format(endday)).getTime();
            return (int) ((end - start) / (1000 * 3600 * 24));
        }catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void check_meal_guest(){
        String[] records;

        System.out.println("Please choose the record number you want to cancel: ");
        String result = Chef.get_food_record(guest.guest_id);
        if(result == ""){
            System.out.println("Meal record that can be canceled not found. ");
            System.out.println("Check booked meals successfully. ");
            return;
        }
        records = result.split("#");
        int length = records.length;
        for(int i=0; i<length; i++){
            System.out.println((i+1) + ". " + records[i]);
        }
        System.out.println("Check booked meals successfully. ");
    }

    public static void book_meal_guest(){
        Date date;
        String workday;
        String result_meal = "";
        String result_chef = "";

        do {
            System.out.println("Please type the date you want to make a meal (yyyy-MM-dd): ");
            String d = input.nextLine();

            date = convert_string_to_sqldate(d);
            int differ = get_day_differ(now, date);

            if(differ >= 2){
                break;
            }
            else{
                System.out.println("The booked time should be at least two days in advance, please type again. ");
            }
        }while(true);

        workday = date_to_week(date);
        System.out.println("workday: "+workday);
        String meals = Chef.get_meals(workday);
        String[] dishes = meals.split(", ");
        int flag = 0;
        System.out.println("The dishes you can book are: ");
        for(String dish: dishes){
            System.out.println((flag+1) + ". " + dish);
            flag++;
        }


        do {
            System.out.println("Please type the dish numbers you want to book (Separate the numbers with spaces): ");
            String result = " ";
            result += input.nextLine();
            result += " ";
            int total = 0;
            for(int i=0; i < flag; i++){
                String number = " " + (i+1) + " ";
                if(result.contains(number)){
                    result_meal += dishes[i] + ", ";
                    total++;
                }
            }
            if(total == 0){
                System.out.println("You should at least contain one legal number, please type again. ");
            }
            else break;
        }while(true);

        result_meal = result_meal.substring(0, result_meal.length() - 2);
        result_chef = Chef.get_chefs(result_meal);
        Chef.insert_food_record(guest.guest_id, result_chef, result_meal, workday);

        System.out.println("Book this meal successfully. ");
    }

    public static void cancel_meal_guest(){

        String[] records;
        int choose = 0;

        do {
            System.out.println("Please choose the record number you want to cancel: ");
            String result = Chef.get_food_record(guest.guest_id);
            if(result == ""){
                System.out.println("Room record not found. ");
                System.out.println("Please type anything to continue. ");
                input.nextLine();
                return;
            }
            records = result.split("#");
            int length = records.length;
            for(int i=0; i<length; i++){
                System.out.println((i+1) + ". " + records[i]);
            }
            choose = input.nextInt();
            input.nextLine();
            if(choose >= 1 && choose <= length) break;
            else System.out.println("Illegal number input, please type again. ");
        }while(true);

        Chef.delete_food_record(records[choose - 1]);

        System.out.println("Cancel this booked meal successfully. ");
    }

    public static void guest_menu(){

        print_line();
        System.out.println("Welcome to guest menu. ");
        do {
            print_line();
            System.out.println("Please type your operation: ");
            System.out.println("1. Update self information. ");
            System.out.println("2. Check self room records. ");
            System.out.println("3. Book a room. ");
            System.out.println("4. Cancel a room. ");
            System.out.println("5. Check self meal records. ");
            System.out.println("6. Book a meal. ");
            System.out.println("7. Cancel a meal. ");
            System.out.println("If you want to quit, please type anything else. ");

            String opr = input.nextLine();
            print_line();

            if(opr.contains("1")){
                update_info_guest();
                press_to_continue();
            }

            else if(opr.contains("2")){
                check_room_guest();
                press_to_continue();
            }

            else if(opr.contains("3")) {
                book_room_guest();
                press_to_continue();
            }

            else if(opr.contains("4")) {
                cancel_room_guest();
                press_to_continue();
            }

            else if(opr.contains("5")) {
                check_meal_guest();
                press_to_continue();
            }

            else if(opr.contains("6")) {
                book_meal_guest();
                press_to_continue();
            }

            else if(opr.contains("7")) {
                cancel_meal_guest();
                press_to_continue();
            }

            else{
                logout();
                break;
            }

        }while (true);
    }

    public static void login_staff(){
        try{
            String username = null;
            String login = null;
            int staff_id = 0;
            String tele = null;

            do{
                System.out.println("Username: ");
                username = input.nextLine();
                if(username.equals("0")){
                    logout(); break;
                }
                String sql = "select staff_id, tele, login from staff where username = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    rs.beforeFirst(); break;
                }
                System.out.println("The username not found, please type again. ");
                System.out.println("If you want to quit, please type 0. ");

            }while(true);

            do{
                if(username.equals("0")) break;
                System.out.println("Password: ");
                String password = input.nextLine();
                while(rs.next()) {
                    login = rs.getString("login");
                    staff_id = rs.getInt("staff_id");
                    tele = rs.getString("tele");
                }
                if(password.equals("0")){
                    System.out.println("Please type your telephone number to retrieve the password: ");
                    String tele_temp = input.nextLine();
                    if(tele_temp.equals(tele)){
                        System.out.println("Retrieve the password successfully. ");
                        System.out.println("Your password: " + login + ", please type again. ");
                    }
                }
                else if(password.equals(login)){
                    System.out.println("Log in successfully. ");
                    staff = new Staff(staff_id, username, tele, login);
                    staff_menu();
                    //System.out.println("Staff object has been created: "+staff);
                    break;
                }
                else{
                    System.out.println("Incorrect password, please type again. ");
                    System.out.println("If you forget your password, please type 0. ");
                }

            }while(true);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void update_info_staff(){
        System.out.println("Please type items you want to update: ");
        System.out.println("1. Telephone number. ");
        System.out.println("2. Password. ");
        System.out.println("If you do not want to update anything, type anything that do not contain these numbers. ");
        String items = input.nextLine();
        int flag = 0;
        if(items.contains("1")){
            System.out.println("New telephone number: ");
            String newone = input.nextLine();
            staff.update_info("tele", newone);
            System.out.println("Update telephone number successfully. "); flag++;
        }
        if(items.contains("2")){
            System.out.println("New password: ");
            String newone = input.nextLine();
            staff.update_info("login", newone);
            System.out.println("Update password successfully. "); flag++;
        }
        if(flag == 0){
            System.out.println("Nothing updated. ");
        }
    }

    public static void check_room_staff(){
        String[] records;
        String[] room_ids = {"X01", "X02", "X03", "X04", "X05", "X06", "X07", "X08", "X09", "X10", "X11", "X12", "X13"};

        System.out.println("There are room booked times for each room: ");
        for(String room_id: room_ids){
            System.out.println(room_id + ": ");
            String result = Room.get_room_record(room_id);
            if(result == ""){
                System.out.println("Room record not found. ");
                continue;
            }
            records = result.split("#");
            int length = records.length;
            for(int i=0; i<length; i++){
                System.out.println((i+1) + ". " + records[i]);
            }
        }
        System.out.println("Check all rooms state successfully.  ");
    }

    public static void staff_menu(){
        print_line();
        System.out.println("Welcome to staff menu. ");
        do {
            print_line();
            System.out.println("Please type your operation: ");
            System.out.println("1. Update self information. ");
            System.out.println("2. Check room states for now and future. ");
            System.out.println("If you want to quit, please type anything else. ");

            String opr = input.nextLine();
            print_line();

            if(opr.contains("1")) {
                update_info_staff();
                press_to_continue();
            }

            else if(opr.contains("2")) {
                check_room_staff();
                press_to_continue();
            }

            else{
                logout();
                break;
            }

        }while (true);
    }

    public static void login(String identity){
        System.out.println("Welcome to login interface. ");
        if(identity.equals("guest")) login_guest();
        if(identity.equals("staff")) login_staff();
    }

    public static void register_guest(){
        try{
            String username = null;
            String login = null;
            int guest_id = 0;
            String realname = null;
            String passport_id = null;
            String tele = null;
            String email = null;

            do{
                System.out.println("Username: ");
                username = input.nextLine();
                String sql = "select login from guest where username = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    System.out.println("This username is already used, please type again. ");
                    rs.beforeFirst();
                }
                else break;
            }while(true);

            System.out.println("Password: ");
            login = input.nextLine();
            System.out.println("Real name: ");
            realname = input.nextLine();
            System.out.println("Passport id: ");
            passport_id = input.nextLine();
            System.out.println("Telephone number: ");
            tele = input.nextLine();
            System.out.println("Email: ");
            email = input.nextLine();

            String sql = "insert into guest (username, realname, passport_id, tele, email, login) values (?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, realname);
            pstmt.setString(3, passport_id);
            pstmt.setString(4, tele);
            pstmt.setString(5, email);
            pstmt.setString(6, login);
            int result = pstmt.executeUpdate();
            System.out.println("The changed row: " + result);

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public static void register_staff(){
        try{
            String username = null;
            String login = null;
            int staff_id = 0;
            String tele = null;

            do{
                System.out.println("Username: ");
                username = input.nextLine();
                String sql = "select login from staff where username = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    System.out.println("This username is already used, please type again. ");
                    rs.beforeFirst();
                }
                else break;
            }while(true);

            System.out.println("Password: ");
            login = input.nextLine();
            System.out.println("Telephone number: ");
            tele = input.nextLine();

            String sql = "insert into staff (username, tele, login) values (?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, tele);
            pstmt.setString(3, login);
            int result = pstmt.executeUpdate();
            System.out.println("The changed row: " + result);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void register(String identity){
        System.out.println("Welcome to register interface. ");
        if(identity.equals("guest")) register_guest();
        else if(identity.equals("staff")) register_staff();
    }

    public static void logout(){
        System.out.println("Thanks for your use. ");
        System.out.println("Your operations are all recorded. ");
        System.out.println("Goodbye. ");
    }

    public static void main(String[] args) {

        get_connection();
        Room.all_room_record_state_initialize();
        Chef.all_chef_initialize();

        do{
            welcome();
            print_line();
            String identity = choose_identity();
            print_line();
            if(identity.equals("guest")) {
                do {
                    String operation = choose_operation();
                    print_line();
                    if (operation.equals("login")) {
                        login("guest");
                        break;
                    } else if (operation.equals("register")) {
                        register("guest");
                        break;
                    } else if (operation.equals("logout")) {
                        logout();
                        break;
                    } else {
                        System.out.println("Illegal number, please type again. ");
                    }
                }while(true);
            }
            else if(identity.equals("staff")) {
                do {
                    String operation = choose_operation();
                    print_line();
                    if (operation.equals("login")) {
                        login("staff");
                        break;
                    } else if (operation.equals("register")) {
                        register("staff");
                        break;
                    } else if (operation.equals("logout")) {
                        logout();
                        break;
                    } else {
                        System.out.println("Illegal number, please type again. ");
                    }
                }while(true);
            }
            else if(identity.equals("quit")){
                System.out.println("Goodbye. ");
            }
            break;
        }while(true);

        close_all();
    }
}
