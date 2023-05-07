package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for creating Device table
 */
@SuppressWarnings({"java:S101", "unused"})
public class V8__CreateDeviceTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        createDeviceTable(context);
    }

    private void createDeviceTable(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement(
                                     "create table if not exists device(" +
                                             " id bigint not null" +
                                             " primary key," +
                                             " api_key varchar(255)," +
                                             " created_at timestamp" +
                                             ");")) {
            statement.execute();
        }
    }
}
