package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Baseline database migration script for flyway
 */
public class V1__Baseline extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {

    }
}