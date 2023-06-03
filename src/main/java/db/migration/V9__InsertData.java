package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@SuppressWarnings({"java:S101", "unused"})
public class V9__InsertData extends BaseJavaMigration {

BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
    @Override
    public void migrate(Context context) throws Exception {
        insertAdmin(context);
    }

    private void insertAdmin(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement(
                                     "insert into _user (" +
                                             "id,email,is_enabled,password,role,user_id) " +
                                             "values (nextval('user_seq'),?,?,?,?,?);")) {
            statement.setString(1, "admin@gmail.com");
            statement.setBoolean(2, true);
            statement.setString(3, passwordEncoder.encode("admin"));
            statement.setString(4, "ADMIN");
            statement.setString(5, UUID.randomUUID().toString());

            statement.execute();
        }
    }
}
