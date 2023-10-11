package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database migration for creating necessary sequences at the initial state
 */
@SuppressWarnings({"java:S101", "unused"})
public class V2__CreateSequences extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        createUserSeq(context);
        createAthleteSeq(context);
        createCoachSeq(context);
        createDeviceSeq(context);
        createSessionSeq(context);
        createSessionDetailsSeq(context);
        createMotionDataSeq(context);
        createActivityTimeSeq(context);
    }

    private void createUserSeq(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create sequence if not exists user_seq")) {
            statement.execute();
        }
    }

    private void createAthleteSeq(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create sequence if not exists athlete_seq")) {
            statement.execute();
        }
    }

    private void createCoachSeq(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create sequence if not exists coach_seq")) {
            statement.execute();
        }
    }

    private void createDeviceSeq(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create sequence if not exists device_seq")) {
            statement.execute();
        }
    }

    private void createSessionSeq(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create sequence if not exists session_seq")) {
            statement.execute();
        }
    }

    private void createSessionDetailsSeq(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create sequence if not exists session_details_seq")) {
            statement.execute();
        }
    }
    private void createMotionDataSeq(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create sequence if not exists session_motion_seq")) {
            statement.execute();
        }
    }

    private void createActivityTimeSeq(Context context) throws SQLException {
        try (PreparedStatement statement =
                     context
                             .getConnection()
                             .prepareStatement("create sequence if not exists activity_time_seq")) {
            statement.execute();
        }
    }
}
