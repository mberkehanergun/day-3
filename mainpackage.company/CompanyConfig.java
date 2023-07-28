package mainpackage.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CompanyConfig {

    private final DataSource dataSource;
    private int IANUM;

    @Autowired
    public CompanyConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int getIANUM() {
        return IANUM;
    }

    public void setIANUM(int IANUM) {
        this.IANUM = IANUM;
    }

    public void incrementIANUM() {
        this.IANUM++;
    }

    public void updateIANUMInDatabase() {
        try (Connection connection = dataSource.getConnection()) {
            String updateQuery = "UPDATE COMPANYCONFIG SET IANUM = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, this.IANUM);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void initializeIANUMFromDatabase() {
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT IANUM FROM COMPANYCONFIG";
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int initialIANUM = resultSet.getInt("IANUM");
                this.IANUM = initialIANUM;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}