package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings({"java:S101", "unused"})
public class V13__AlterSessionTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        addAnalysisStatusColumn(context);
    }
    private void addAnalysisStatusColumn(Context context) throws SQLException {
        try(PreparedStatement statement =
                    context
                            .getConnection()
                            .prepareStatement("ALTER TABLE session " +
                                    "ADD COLUMN analysis_status boolean default false")){
            statement.execute();
        }
    }
}
