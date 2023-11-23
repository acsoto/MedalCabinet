package com.mcatk.medalcabinet.sql;

import com.mcatk.medalcabinet.MedalCabinet;
import com.mcatk.medalcabinet.medal.Medal;

import java.sql.*;
import java.util.ArrayList;

public class SQLManager {
    private Connection connection;

    private static SQLManager instance = null;

    public static SQLManager getInstance() {
        return instance == null ? instance = new SQLManager() : instance;
    }

    private SQLManager() {
        connectMySQL();
    }

    private void connectMySQL() {
        String ip = MedalCabinet.getPlugin().getConfig().getString("mysql.ip");
        String databaseName = MedalCabinet.getPlugin().getConfig().getString("mysql.databasename");
        String userName = MedalCabinet.getPlugin().getConfig().getString("mysql.username");
        String userPassword = MedalCabinet.getPlugin().getConfig().getString("mysql.password");
        int port = MedalCabinet.getPlugin().getConfig().getInt("mysql.port");
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":" + port + "/" + databaseName + "?autoReconnect=true&useSSL=false",
                    userName, userPassword
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createMedal(String medalID, String medalName, String medalMat, String medalDes) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO `medal` " +
                        "(`medal_id`, `medal_name`, `medal_material`,`medal_description`)" +
                        "VALUES " +
                        "(?,?,?,?)"
        )) {
            ps.setString(1, medalID);
            ps.setString(2, medalName);
            ps.setString(3, medalMat);
            ps.setString(4, medalDes);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean doesPlayerHaveMedal(String playerID, String medalID) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM player_medal WHERE player_id = ? and medal_id = ?"
        )) {
            ps.setString(1, playerID);
            ps.setString(2, medalID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addPlayerMedal(String playerID, String medalID) {
        try {
            if (doesPlayerHaveMedal(playerID, medalID)) {
                return;
            }
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO `player_medal` " +
                            "(`player_id`, `medal_id`)" +
                            "VALUES " +
                            "(?,?)"
            );
            ps.setString(1, playerID);
            ps.setString(2, medalID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Medal> getAllMedals() {
        ArrayList<Medal> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM medal"
        )) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Medal(
                        rs.getString("medal_id"),
                        rs.getString("medal_name"),
                        rs.getString("medal_material"),
                        rs.getString("medal_description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Medal getMedal(String medalID) {
        Medal medal = null;
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM medal WHERE medal_id = ?"
        )) {
            ps.setString(1, medalID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                medal = new Medal(
                        rs.getString("medal_id"),
                        rs.getString("medal_name"),
                        rs.getString("medal_material"),
                        rs.getString("medal_description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medal;
    }

    public ArrayList<Medal> getPlayerMedals(String playerID) {
        ArrayList<Medal> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT DISTINCT m.* FROM medal m INNER JOIN player_medal pm ON m.medal_id = pm.medal_id WHERE pm.player_id = ?"
        )) {
            ps.setString(1, playerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Medal medal = new Medal(
                        rs.getString("medal_id"),
                        rs.getString("medal_name"),
                        rs.getString("medal_material"),
                        rs.getString("medal_description")
                );
                list.add(medal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean setMainMedal(String playerID, String medalID) {
        if (doesPlayerHaveMedal(playerID, medalID)) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO `player_main_medal` (player_id, medal_id) VALUES (?,?) ON DUPLICATE KEY UPDATE medal_id = ?"
            )) {
                ps.setString(1, playerID);
                ps.setString(2, medalID);
                ps.setString(3, medalID);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Medal getMainMedal(String playerID) {
        Medal medal = null;
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT medal_id FROM `player_main_medal` WHERE player_id = ?"
        )) {
            ps.setString(1, playerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                medal = getMedal(rs.getString("medal_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medal;
    }

}
