package sa.homework.logger.classes;

import sa.homework.resources.config.MyProperties;
import sa.homework.logger.interfaces.ICrud;

import java.sql.*;

public class Database implements ICrud {
    @Override
    public void readData(String tableName) {
        try (Connection conn = DriverManager.getConnection(MyProperties.getDatabaseUrl(),
                MyProperties.getDatabaseUser(), MyProperties.getDatabasePassword());
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM timesheet." + tableName)) {
            ResultSet resultSet = pstmt.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int numberOfColumns = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    System.out.print(resultSetMetaData.getColumnName(i) + ": " + resultSet.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Failed to show data from this table.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(String tableName, String columnName, int id, Object value) {
        try (Connection conn = DriverManager.getConnection(MyProperties.getDatabaseUrl(),
                MyProperties.getDatabaseUser(), MyProperties.getDatabasePassword());
             PreparedStatement pstmt = conn.prepareStatement("update " + tableName + " set " + columnName + "=? where id=?")) {

            pstmt.setInt(2, id);
            if (value instanceof String) {
                pstmt.setString(1, (String) value);
            } else if (value instanceof Integer) {
                pstmt.setInt(1, (int) value);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to update." + e.getMessage());
        }

    }

    @Override
    public void delete(String tableName, int id) {
        try(Connection conn = DriverManager.getConnection(MyProperties.getDatabaseUrl(),
                MyProperties.getDatabaseUser(), MyProperties.getDatabasePassword());
            PreparedStatement pstmt = conn.prepareStatement("delete from " + tableName + " where id=?")) {
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Failed to delete :" + e.getMessage());
        }

    }

    @Override
    public void create(String tableName, String content) {
        try(Connection conn = DriverManager.getConnection(MyProperties.getDatabaseUrl(),
                MyProperties.getDatabaseUser(), MyProperties.getDatabasePassword());
            Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(" create table "+ tableName + "(" + content + ")");
        }catch (SQLException e){
            System.out.println("Failed to create :" + e.getMessage());
        }
    }
}
