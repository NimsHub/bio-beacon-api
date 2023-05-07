package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for creating athlete table
 */
public class V4__CreateAthleteTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        createAthleteTable(context);
    }

    private void createAthleteTable(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create table if not exists athlete" +
                                     "(\n" +
                                     " id bigint not null primary key,\n" +
                                     " address varchar(255),\n" +
                                     " athlete_id uuid,\n" +
                                     " coach_id uuid,\n" +
                                     " date_of_birth date,\n" +
                                     " email varchar(255),\n" +
                                     " firstname varchar(255),\n" +
                                     " gender varchar(255),\n" +
                                     " height double precision,\n" +
                                     " joined_date timestamp,\n" +
                                     " lastname varchar(255),\n" +
                                     " mobile varchar(255),\n" +
                                     " occupation varchar(255),\n" +
                                     " user_id uuid,\n" +
                                     " weight double precision\n" +
                                     ");")) {
            statement.execute();

        }
    }
}
