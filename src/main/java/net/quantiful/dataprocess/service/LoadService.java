package net.quantiful.dataprocess.service;

import net.quantiful.dataprocess.model.DataSetObj;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

/**
 * Created by rogersong on 18/08/17.
 */


public class LoadService {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";//jdbc驱动路径
    private static String url = "jdbc:hive2://ec2-54-252-224-98.ap-southeast-2.compute.amazonaws.com:10000/default";//hive库地址+库名
    private static String user = "hadoop";//用户名
    private static String sql = "";
    private static String password = "";
    private static ResultSet res;

    public static void runit(List<DataSetObj> dataSetObjList) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConn();
            System.out.println(conn);
            sql = "insert into dataset (status_id, status_message, link_name, status_type, status_link, status_published, num_reactions, num_comments, num_shares, num_likes, num_loves, num_wows, "
                    + " num_hahas, num_sads, num_angrys, impact_score, page_id, source_type, auckland, council, mayor, goff, councillor, localboard, tier1) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            String tableName="dataset";//hive表名
 //           sql = "select * from " + "t1";


            for (DataSetObj dataSetObj: dataSetObjList) {
                /*
                String query = "insert into dataset (status_id, status_message, link_name, status_type, status_link, status_published, num_reactions, num_comments, num_shares, num_likes, num_loves, num_wows, "
                        + " num_hahas, num_sads, num_angrys, impact_score, page_id, source_type, auckland, council, mayor, goff, councillor, localBoard, tier1) values('"
                        + dataSetObj.getStatusId() + "','" + dataSetObj.getStatusMessage() + "','" + dataSetObj.getLinkName() + "','" + dataSetObj.getStatusType() +  "','" + dataSetObj.getStatusLink()+ "','" + dataSetObj.getStatusPublished()
                        + "','" + dataSetObj.getNumReactions() + "','" + dataSetObj.getNumComments() +  "','" + dataSetObj.getNumShares() +  "','" + dataSetObj.getNumLikes() +   "','" + dataSetObj.getNumLoves()
                        + "','" + dataSetObj.getNumWows() + "','" + dataSetObj.getNumHahas() + "','" + dataSetObj.getNumSads() + "','" + dataSetObj.getNumAngrys()+ "','" + dataSetObj.getImpactScore() + "','" + dataSetObj.getPageId() + "','" + dataSetObj.getSourceType()
                        + "','" + dataSetObj.getAuckland() + "','" + dataSetObj.getCouncil() + "','" + dataSetObj.getMayor() + "','" + dataSetObj.getGoff() + "','" + dataSetObj.getCouncillor() + "','" + dataSetObj.getLocalBoard() + "','" + dataSetObj.getTier1()+  "')";
                */

                stmt.setString(1, dataSetObj.getStatusId());
                stmt.setString(2, dataSetObj.getStatusMessage());
                stmt.setString(3, dataSetObj.getLinkName());
                stmt.setString(4, dataSetObj.getStatusType());
                stmt.setString(5, dataSetObj.getStatusLink());
                stmt.setString(6, dataSetObj.getStatusPublished());
                stmt.setInt(7, dataSetObj.getNumReactions());
                stmt.setInt(8, dataSetObj.getNumComments());
                stmt.setInt(9, dataSetObj.getNumShares());
                stmt.setInt(10, dataSetObj.getNumLikes());
                stmt.setInt(11, dataSetObj.getNumLoves());
                stmt.setInt(12, dataSetObj.getNumWows());
                stmt.setInt(13, dataSetObj.getNumHahas());
                stmt.setInt(14, dataSetObj.getNumSads());
                stmt.setInt(15, dataSetObj.getNumAngrys());
                stmt.setInt(16, dataSetObj.getImpactScore());
                stmt.setString(17,dataSetObj.getPageId());
                stmt.setString(18,dataSetObj.getSourceType());
                stmt.setInt(19, dataSetObj.getAuckland());
                stmt.setInt(20, dataSetObj.getCouncil());
                stmt.setInt(21, dataSetObj.getMayor());
                stmt.setInt(22, dataSetObj.getGoff());
                stmt.setInt(23, dataSetObj.getCouncillor());
                stmt.setInt(24, dataSetObj.getLocalBoard());
                stmt.setInt(25, dataSetObj.getTier1());

//                stmt.addBatch();

                // single execute
                stmt.execute();
            }


//            System.out.println("Running:" + sql);

//            stmt.executeBatch();

            /*
            res = stmt.executeQuery(sql);
            System.out.println("执行 select * query 运行结果:");
            while (res.next()) {
                System.out.println(res.getInt(2) + "\t" + res.getString(1));
            }
            */
            stmt.close();
            stmt = null;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        }
    }

    private static Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);

        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
}
