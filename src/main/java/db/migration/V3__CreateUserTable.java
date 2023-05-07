package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for creating User table
 */
@SuppressWarnings({"java:S101", "unused"})
public class V3__CreateUserTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        createUserTable(context);
    }

    private void createUserTable(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create table if not exists _user(" +
                                     " id bigint not null primary key," +
                                     " email varchar(255) constraint email_cons unique," +
                                     " is_enabled boolean not null," +
                                     " password varchar(255)," +
                                     " role varchar(255)," +
                                     " user_id uuid" +
                                     ");")) {
            statement.execute();
        }
    }
}
