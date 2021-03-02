package io.pivotal.pal.tracker;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS
            );
            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long id) {
        return jdbcTemplate.query("SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?", extractor, id);
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query(
                "SELECT id, project_id, user_id, date, hours from time_entries",
                (rs, rowNum) -> new TimeEntry(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getDate(4).toLocalDate(), rs.getInt(5))
        );
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE time_entries SET project_id = ?, user_id = ?, date = ?, hours = ? WHERE id = ?"
            );

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());
            statement.setLong(5, id);

            return statement;
        });

        return find(id);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM time_entries WHERE id = ?");
            statement.setLong(1, id);
            return statement;
        });
    }

    private final ResultSetExtractor<TimeEntry> extractor = new ResultSetExtractor<TimeEntry>() {
        @Override
        public TimeEntry extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (rs.next() == false) {
                return null;
            }
            return new TimeEntry(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getDate(4).toLocalDate(), rs.getInt(5));
        }
    };
}
