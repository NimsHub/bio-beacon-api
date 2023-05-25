package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for creating SessionDetails table
 */
@SuppressWarnings({"java:S101", "unused"})
public class V7__CreateSessionDetailsTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        createSessionDetailsTable(context);
    }

    private void createSessionDetailsTable(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement(
                                     "create table if not exists session_details(" +
                                             " id bigint not null" +
                                             " primary key," +
                                             " blood_pressure varchar(255)," +
                                             " heart_rate varchar(255)," +
                                             " respiration_rate varchar(255)," +
                                             " session bigint" +
                                             " constraint fk_session_id" +
                                             " references session" +
                                             ");")) {
            statement.execute();
        }
    }
}
