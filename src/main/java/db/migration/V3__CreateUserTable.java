package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class V3__CreateUserTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        createUserTable(context);
    }

    private void createUserTable(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create table if not exists _user(\n" +
                                     " id bigint not null primary key,\n" +
                                     " email varchar(255) constraint email_cons unique,\n" +
                                     " is_enabled boolean not null,\n" +
                                     " password varchar(255),\n" +
                                     " role varchar(255),\n" +
                                     " user_id uuid\n" +
                                     ");")) {
            statement.execute();
        }
    }
}
