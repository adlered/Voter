package pers.adlered.voter.analyzer;

import org.springframework.stereotype.Controller;
import pers.adlered.voter.configuration.MySQL;

import javax.annotation.PostConstruct;
import java.sql.*;

@Controller
public class DatabaseCheck {
    @PostConstruct
    public void DatabaseCheck() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String IP = "jdbc:mysql://" + MySQL.IP + ":" + MySQL.Port + "/Voter?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true";
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                connection = DriverManager.getConnection(IP, MySQL.Username, MySQL.Password);
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM Voter_Vote");
            } catch (SQLException SQLE) {
                System.out.println("[TIP] Did you configured the MySQL properties on CLASS \"pers.adlered.voter.configuration.MySQL\" ? If you didn't create database and tables for Voter before, check the CLASS.");
            }
            try {
                while (resultSet.next()) {
                    resultSet.getString("VID");
                }
            } catch (SQLException SQLE) {
                SQLE.printStackTrace();
            } catch (NullPointerException NPE) {
                System.out.println("[VOTER] AUTO-MAKING Tables on MySQL Database ..");
                try {
                    String SQL = "CREATE TABLE `Voter_Vote` (\n" +
                            "  `VID` int(11) NOT NULL AUTO_INCREMENT,\n" +
                            "  `Title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,\n" +
                            "  `Describe` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,\n" +
                            "  `Selection` varchar(10000) CHARACTER SET utf8mb4 DEFAULT NULL,\n" +
                            "  `Type` int(11) DEFAULT '0',\n" +
                            "  `Limit` int(11) DEFAULT '-1',\n" +
                            "  PRIMARY KEY (`VID`)\n" +
                            ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;";
                    statement.executeUpdate(SQL);
                    SQL = "INSERT INTO `Voter_Vote` VALUES (1, 'This is a vote title :)', 'And here is a describe of the vote XD', '<%1<%92<%Vote for Xiaoli<%2<%42<%Vote for Zhanghua<%3<%41<%Vote for Ergou<%4<%37<%Vote for Guawazi<%5<%48<%Vote for Benwei<%6<%50<%Vote for Adler', 0, 2);";
                    statement.executeUpdate(SQL);
                    System.out.println("[VOTER] It seems like successful to create default tables. You should check your Database and access the default vote on page \"/vote/1\"");
                } catch (NullPointerException NPE2) {
                    System.out.println("[VOTER] Create table failed! Please create a database named \"Voter\" first!");
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException SQLE) {
                System.out.println("[VOTER] Did you configured the MySQL properties on CLASS \"pers.adlered.voter.configuration.MySQL\" ? If you didn't create database and tables for Voter before, check the CLASS.");
            } catch (NullPointerException NPE) {
            }
        } catch (ClassNotFoundException CNFE) {
            CNFE.printStackTrace();
        }
    }
}
