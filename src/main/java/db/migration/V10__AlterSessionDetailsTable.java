package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for altering data types in to SessionDetails table
 */
@SuppressWarnings({"java:S101", "unused"})
public class V10__AlterSessionDetailsTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        updateDataTypes(context);
    }

    private void updateDataTypes(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("ALTER TABLE session_details " +
                                     "ALTER COLUMN blood_pressure TYPE TEXT," +
                                     "ALTER COLUMN heart_rate TYPE TEXT," +
                                     "ALTER COLUMN respiration_rate TYPE TEXT;")) {
            statement.execute();

        }
    }
}
