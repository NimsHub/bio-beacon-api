package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for creating athlete table
 */
//@SuppressWarnings({"java:S101", "unused"})
public class V4__CreateAthleteTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        createAthleteTable(context);
    }

    private void createAthleteTable(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create table if not exists athlete(" +
                                     " id bigint not null primary key," +
                                     " address varchar(255)," +
                                     " athlete_id uuid," +
                                     " coach_id uuid," +
                                     " date_of_birth date," +
                                     " email varchar(255)," +
                                     " firstname varchar(255)," +
                                     " gender varchar(255)," +
                                     " height double precision," +
                                     " joined_date timestamp," +
                                     " lastname varchar(255)," +
                                     " mobile varchar(255)," +
                                     " occupation varchar(255)," +
                                     " user_id uuid," +
                                     " weight double precision" +
                                     ");")) {
            statement.execute();

        }
    }
}
