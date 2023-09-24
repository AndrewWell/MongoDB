import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

class PostgreDB {
    private connectString
    private username
    private password
    private tableName
    private int index

    PostgreDB(connectString, username, password, dbName, tableName) {
        this.connectString = connectString + "/" + dbName
        this.username = username
        this.password = password
        this.tableName = tableName
        index = 0
    }

    void insert(ArrayList arrayList) {
        try (Connection connection = DriverManager.getConnection(connectString, username, password)) {
            Statement statement = connection.createStatement()
            arrayList.each {
                statement.executeUpdate(distributor(splitLine(it)))
            }
        }
        catch (SQLException e) {
            println("SQL State: %s\n%s", e.getSQLState(), e.getMessage())
        }
        catch (Exception e) {
            e.printStackTrace()
        }
    }


    void createTable() {
        try (Connection connection = DriverManager.getConnection(connectString, username, password)) {
            Statement statement = connection.createStatement()
            String table = "CREATE TABLE " + tableName + "(id INT NOT NULL, persAccount VARCHAR(30)," +
                    " fullName VARCHAR(100), address VARCHAR(150), acrualPeriod VARCHAR(10), amount VARCHAR(15))"
            statement.executeUpdate(table)
        }
        catch (SQLException e) {
            println("SQL State: %s\n%s", e.getSQLState(), e.getMessage())
        }
        catch (Exception e) {
            e.printStackTrace()
        }
    }

    private String distributor(String[] line) {
        short length = (short) line.length;
        this.index++
        return "INSERT INTO " + tableName + " (id, persAccount, fullName," +
                " address, acrualPeriod, amount) VALUES ('" + this.index + "', '" + line[0] + "', '" +
                line[1] + "', '" + line[2] + "', '" + line[3] + "', '" + line[4] + "')"
    }

    private String[] splitLine(String line) {
        return line.split(";");
    }

    void closeConnect() {
        connection.close()
    }

}

