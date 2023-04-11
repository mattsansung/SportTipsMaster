package com.example.sporttips.Database;


import static com.example.sporttips.Database.DbOpenHelper.closeConnection;
import static com.example.sporttips.Database.DbOpenHelper.getConnection;
import static com.example.sporttips.Start.Login.ip;

import android.os.Message;
import android.util.Log;

import com.example.sporttips.Bean.LeibieBean;
import com.example.sporttips.Bean.RichengBean;
import com.example.sporttips.Bean.SportBean;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private String tableName = null;
    private int addId = 0;
    private static String username = null;
    private static String password = null;
    public static int userid = 0;
    private List<RichengBean> list;
    private List<String> list1;
    private List<SportBean> list2;

    public void Query(String tableName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection  conn =(Connection)getConnection(ip);
                String sql = "select * from "+tableName;
                Statement st;
                try {
                    if (conn ==null){
                        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        System.out.println(""+conn);
                    }else {
                        st = (Statement) conn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        while (rs.next()) {
                            Log.i("data1", rs.getString(1));
                            Log.i("data2", rs.getString(2));
                            Log.i("data3", rs.getString(3)); 
                            //因为查出来的数据试剂盒的形式，所以我们新建一个javabean存储
                            /*Test test = new Test();
                     test.setUser(rs.getString(1));
                     Message msg = new Message();
                     msg.what =TEST_USER_SELECT;
                     msg.obj = test;
                     handler.sendMessage(msg);*/
                        }
                        st.close();
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //closeConnection();
    }
    public int RegisterUser(String name,String password,String sex,int age,double height,double weight) throws InterruptedException {
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "insert into registeruser (name,password,age,sex,height,weight) values(?,?,?,?,?,?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    pst.setString(1,name);
                    pst.setString(2,password);
                    pst.setInt(3,age);
                    pst.setString(4,sex);
                    pst.setDouble(5,height);
                    pst.setDouble(6,weight);
                    addId = pst.executeUpdate();
                    //Log.i("id",String.valueOf(addId));
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public void QueryUser(String name) throws InterruptedException {
       Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "select * from registeruser where name = '"+name+"'";
                Statement st;
                try {
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        userid = rs.getInt(1);
                        username = rs.getString(2);
                        password = rs.getString(3);
                        Log.i("name",username.toString()+userid);
                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
       thread.start();
       thread.join();
    }
    public List<RichengBean> QueryPlan(String name,String date) throws InterruptedException {
        list = new ArrayList<>();
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "select * from plan where user = '"+name+"' and date = '"+date+"'";
                Statement st;
                try {
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String user,qiangdu,sport,jihua,date,done;
                        int time,year,month,day,id;
                        id = rs.getInt(1);
                        user= rs.getString(2);
                        qiangdu = rs.getString(3);
                        time = rs.getInt(4);
                        sport = rs.getString(5);
                        jihua = rs.getString(6);
                        year = rs.getInt(7);
                        month = rs.getInt(8);
                        day = rs.getInt(9);
                        date = rs.getString(10);
                        done = rs.getString(11);
                        RichengBean richengBean = new RichengBean(id,user,qiangdu,time,sport,jihua,year,month,day,date,done);
                        list.add(richengBean);
                        //Log.e("plan",sport);
                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return list;
    }
    public List<String> QueryLeibie(String name) throws InterruptedException {
        list1 = new ArrayList<>();
        list1.add("跑步");
        list1.add("游泳");
        list1.add("力量训练");
        list1.add("拉伸");
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "select * from leibie where user = '"+name+"'";
                Statement st;
                try {
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String leibie;
                        leibie= rs.getString(1);
                        list1.add(leibie);
                        //Log.e("plan",leibie);
                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return list1;
    }
    public  static String getLoginName(){
        return username;
    }
    public static String getLoginpassword(){
        return password;
    }
    // return addId;
    public int AddPlan(String name,String qiangdu,int sum,String sport,String jihua,int year,int month,int day,String date) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "insert into plan (user,qiangdu,sporttime,sport,jihua,year,month,day,date,done) values(?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    pst.setString(1,name);
                    pst.setString(2,qiangdu);
                    pst.setInt(3,sum);
                    pst.setString(4,sport);
                    pst.setString(5,jihua);
                    pst.setInt(6,year);
                    pst.setInt(7,month);
                    pst.setInt(8,day);
                    pst.setString(9,date);
                    pst.setString(10,"否");
                    addId = pst.executeUpdate();
                    //Log.i("id",String.valueOf(addId));
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public int AddHWeight(int id,double height,double weight) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "update registeruser set height = '"+height+"', weight = '"+weight+"' where userId =  '"+id+"'";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    addId = pst.executeUpdate();
                    //Log.i("id",String.valueOf(addId));
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public int AddLeibie(String leibie) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "insert into leibie (leibie,user) values(?,?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    pst.setString(1,leibie);
                    pst.setString(2,username);
                    addId = pst.executeUpdate();
                    //Log.i("id",String.valueOf(addId));
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public int UpdatePassword(String password,int id) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "update registeruser set password = '"+password+"' where userId =  '"+id+"'";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    addId = pst.executeUpdate();
                    //Log.i("id",String.valueOf(addId));
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public List<RichengBean> QueryMonthPlan(String name,int month) throws InterruptedException {
        list = new ArrayList<>();
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "select * from plan where user = '"+name+"' and month = '"+month+"' order by day";
                Statement st;
                try {
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String user,qiangdu,sport,jihua,date,done;
                        int time,year,month,day,id;
                        id = rs.getInt(1);
                        user= rs.getString(2);
                        qiangdu = rs.getString(3);
                        time = rs.getInt(4);
                        sport = rs.getString(5);
                        jihua = rs.getString(6);
                        year = rs.getInt(7);
                        month = rs.getInt(8);
                        day = rs.getInt(9);
                        date = rs.getString(10);
                        done = rs.getString(11);
                        RichengBean richengBean = new RichengBean(id,user,qiangdu,time,sport,jihua,year,month,day,date,done);
                        list.add(richengBean);
                        //Log.e("plan",sport);
                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return list;
    }
    public int UpdateDone(String password,int id) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "update plan set done = '"+password+"' where sportid =  '"+id+"'";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    addId = pst.executeUpdate();
                    //Log.i("id",String.valueOf(addId));
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public int AddSport(String user,int year,int month,int day,int hour,int min,String sport,int sum) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "insert into sport(user,year,month,day,hour,min,sport,sumtime) values(?,?,?,?,?,?,?,?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    pst.setString(1,user);
                    pst.setInt(2,year);
                    pst.setInt(3,month);
                    pst.setInt(4,day);
                    pst.setInt(5,hour);
                    pst.setInt(6,min);
                    pst.setString(7,sport);
                    pst.setInt(8,sum);
                    addId = pst.executeUpdate();
                    //Log.i("id",String.valueOf(addId));
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public List<SportBean> QuerySport(String name) throws InterruptedException {
        list2 = new ArrayList<>();
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "select * from sport where user = '"+name+"' order by day";
                Statement st;
                try {
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String user,qiangdu,sport,jihua,date,done;
                        int time,year,month,day,hour,min,id;
                        id = rs.getInt(1);
                        user= rs.getString(2);
                        year = rs.getInt(3);
                        month = rs.getInt(4);
                        day = rs.getInt(5);
                        hour = rs.getInt(6);
                        min = rs.getInt(7);
                        date = rs.getString(8);
                        time = rs.getInt(9);
                        SportBean sportBean = new SportBean(id,user,year,month,day,hour,min,date,time);
                        list2.add(sportBean);
                        //Log.e("plan",sport);
                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return list2;
    }
    public int UpdatePlan(String qiangdu, String sport, String jihua, int time,int id) throws InterruptedException {
        addId = 0;
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection(ip);
                String sql = "update plan set qiangdu = '"+qiangdu+"',sport ='"+sport+"',jihua = '"+jihua+"',sporttime ='"+time+"',done='否' where sportid='"+id+"'";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    //将输入的edit框的值获取并插入到数据库中
                    addId = pst.executeUpdate();
                    //Log.i("id",String.valueOf(addId));
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
}

