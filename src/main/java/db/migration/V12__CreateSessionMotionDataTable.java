package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for creating SessionMotionData table
 */
@SuppressWarnings({"java:S101", "unused"})
public class V12__CreateSessionMotionDataTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        createSessionMotionDataTable(context);
    }

    private void createSessionMotionDataTable(Context context) throws SQLException{
        try(PreparedStatement statement =
                context
                        .getConnection()
                        .prepareStatement("CREATE TABLE IF NOT EXISTS session_motion_data(" +
                                " id BIGINT NOT NULL PRIMARY KEY," +
                                " device_one_motion_data TEXT," +
                                " device_two_motion_data TEXT," +
                                " device_three_motion_data TEXT," +
                                " device_four_motion_data TEXT," +
                                " device_five_motion_data TEXT," +
                                " session BIGINT CONSTRAINT fk_session_id REFERENCES session)")){
            statement.execute();
        }
    }
}
