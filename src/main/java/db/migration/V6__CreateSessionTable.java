package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for creating Session table
 */
public class V6__CreateSessionTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        createSessionTable(context);
    }

    private void createSessionTable(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement(
                                     "create table if not exists session(" +
                                             " id bigint not null primary key," +
                                             " athlete_id uuid not null," +
                                             " create_date_time timestamp," +
                                             " device_id bigint not null," +
                                             " end_date_time timestamp," +
                                             " is_complete boolean default false," +
                                             " session_duration bigint," +
                                             " session_id uuid not null," +
                                             " start_date_time timestamp" +
                                             ");")) {
            statement.execute();
        }
    }
}
