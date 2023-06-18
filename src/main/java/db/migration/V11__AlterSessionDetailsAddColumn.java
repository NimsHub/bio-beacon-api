package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for adding ECG column to SessionDetails table
 */
@SuppressWarnings({"java:S101", "unused"})
public class V11__AlterSessionDetailsAddColumn extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        addEcgColumn(context);
    }

    private void addEcgColumn(Context context) throws SQLException {
        try(PreparedStatement statement =
                context
                        .getConnection()
                        .prepareStatement("ALTER TABLE session_details " +
                                "ADD COLUMN ecg TEXT, " +
                                "ADD COLUMN blood_oxygen TEXT, " +
                                "DROP COLUMN blood_pressure")){
            statement.execute();
        }
    }
}
