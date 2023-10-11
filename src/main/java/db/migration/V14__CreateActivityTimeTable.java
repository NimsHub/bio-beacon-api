package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for creating SessionMotionData table
 */
@SuppressWarnings({"java:S101", "unused"})
public class V14__CreateActivityTimeTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        createSessionMotionDataTable(context);
    }

    private void createSessionMotionDataTable(Context context) throws SQLException{
        try(PreparedStatement statement =
                context
                        .getConnection()
                        .prepareStatement("CREATE TABLE IF NOT EXISTS activity_time(" +
                                "id BIGINT NOT NULL PRIMARY KEY," +
                                " cycling int," +
                                " push_up int," +
                                " running int," +
                                " squat int," +
                                " table_tennis int," +
                                " walking int," +
                                " session BIGINT CONSTRAINT fk_session_id REFERENCES session)")){
            statement.execute();
        }
    }
}
