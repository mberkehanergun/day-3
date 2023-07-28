package mainpackage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;
import mainpackage.company.CompanyConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

@Repository
public class NamedParamJdbcDaoImpl extends NamedParameterJdbcDaoSupport {

	private final DataSource dataSource;
    private final CompanyConfig companyConfig;

    @Autowired
    public NamedParamJdbcDaoImpl(DataSource dataSource, CompanyConfig companyConfig) {
        this.dataSource = dataSource;
        this.companyConfig = companyConfig;
    }

    public void printInternTable() {
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT NAMEANDSURNAME FROM INTERN";
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Printing table INTERN with field NAMEANDSURNAME:");
            while (resultSet.next()) {
                String nameAndSurname = resultSet.getString("NAMEANDSURNAME");
                System.out.println(nameAndSurname);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSelectedRows(String[] nameandsurnamesToRemove) {
        try (Connection connection = dataSource.getConnection()) {
            String deleteQuery = "DELETE FROM INTERN WHERE NAMEANDSURNAME = ?";
            PreparedStatement statement = connection.prepareStatement(deleteQuery);

            for (String nameandsurname : nameandsurnamesToRemove) {
                statement.setString(1, nameandsurname);
                int affectedRows = statement.executeUpdate();

                if (affectedRows > 0) {
                    int IANUM = companyConfig.getIANUM();
                    System.out.println("Intern " + nameandsurname + " is removed from table with internship application num " + IANUM);

                    companyConfig.incrementIANUM();
                    companyConfig.updateIANUMInDatabase();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}