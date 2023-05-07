package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

/**
 * Baseline database migration script for flyway
 */
//@SuppressWarnings({"java:S101", "unused"})
public class V1__Baseline extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        // baseline migration
    }
}