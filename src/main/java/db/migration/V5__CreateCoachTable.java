package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for creating Coach Table
 */
public class V5__CreateCoachTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        createCoachTable(context);
    }

    private void createCoachTable(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement(
                                     "create table if not exists coach(" +
                                             " id bigint not null primary key," +
                                             " address varchar(255)," +
                                             " coach_id uuid," +
                                             " date_of_birth date," +
                                             " email varchar(255) constraint email_cons unique," +
                                             " firstname varchar(255)," +
                                             " gender varchar(255)," +
                                             " joined_date timestamp," +
                                             " lastname varchar(255)," +
                                             " mobile varchar(255)," +
                                             " user_id uuid not null" +
                                             ");")) {
            statement.execute();
        }
    }
}
