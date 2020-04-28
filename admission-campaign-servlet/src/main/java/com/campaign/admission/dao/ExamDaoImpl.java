package com.campaign.admission.dao;

import com.campaign.admission.dao.datasource.ConnectionPool;
import com.campaign.admission.dao.mapper.ExamMapper;
import com.campaign.admission.dao.mapper.Mapper;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.exception.DatabaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

public class ExamDaoImpl implements ExamDao {

    private final ConnectionPool connectionPool;

    private final ResourceBundle resourceBundle;

    public ExamDaoImpl() {
        connectionPool = new ConnectionPool("database");
        resourceBundle = getBundle("queries");
    }

    private Mapper<Exam> getMapper() {
        return new ExamMapper();
    }

    @Override
    public List<String> findUserFreeSubjects(String email) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.user.free.subjects"))) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            List<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString("subject"));
            }

            return result;
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding user free subjects operation exception!");
        }
    }

    @Override
    public List<String> findAllSubjects() {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.all.subjects"))) {

            ResultSet resultSet = statement.executeQuery();
            List<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString("subject"));
            }

            return result;
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding all subjects operation exception!");
        }
    }

    @Override
    public void saveExams(List<Exam> exams) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("insert.exams"))) {

            for (Exam exam : exams) {
                statement.setString(1, exam.getUser().getEmail());
                statement.setString(2, exam.getSubject());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Saving exams operation exception!");
        }
    }

    @Override
    public List<Exam> findExamsByEmail(String email) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.exams.by.email"))) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            List<Exam> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(getMapper().map(resultSet));
            }

            return result;
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding exams by email operation exception!");
        }
    }


    @Override
    public List<Exam> findExamsPaginated(String subject, Integer page, Integer pageSize) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.exams.paginated"))) {

            statement.setString(1, subject);
            statement.setInt(2, (page * pageSize));
            statement.setInt(3, pageSize);
            ResultSet resultSet = statement.executeQuery();
            List<Exam> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(getMapper().map(resultSet));
            }

            return result;
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding exams paginated operation exception");
        }
    }

    @Override
    public Integer findExamsCountBySubject(String subject) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("select.exams.count.by.subject"))) {

            statement.setString(1, subject);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                return resultSet.getInt("count");
            }

            return 0;
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Finding exams count by subject operation exception!");
        }
    }

    @Override
    public void setMarks(List<Exam> exams) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("update.exams.marks.by.email"))) {

            for (Exam exam : exams) {
                statement.setInt(1, exam.getMark());
                statement.setString(2, exam.getUser().getEmail());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e, "Setting marks operation exception!");
        }
    }
}
