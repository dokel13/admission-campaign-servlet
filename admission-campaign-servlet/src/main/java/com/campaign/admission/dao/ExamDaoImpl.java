package com.campaign.admission.dao;

import com.campaign.admission.dao.mapper.ExamMapper;
import com.campaign.admission.dao.mapper.Mapper;
import com.campaign.admission.domain.Exam;
import com.campaign.admission.exception.DatabaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ExamDaoImpl extends AbstractDao<Exam> implements ExamDao {

    @Override
    protected Mapper<Exam> getMapper() {
        return new ExamMapper();
    }

    @Override
    public List<String> findUserFreeSubjects(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.user.free.subjects"))) {

            statement.setString(1, email);

            return constructMultivaluedStrResult(statement.executeQuery(), "subject");
        } catch (SQLException exception) {
            String exceptionMessage = "Finding user free subjects operation exception!";
            LOGGER.error(exceptionMessage);
            throw new DatabaseRuntimeException(exception, exceptionMessage);
        }
    }

    @Override
    public List<String> findAllSubjects() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.all.subjects"))) {

            return constructMultivaluedStrResult(statement.executeQuery(), "subject");
        } catch (SQLException exception) {
            String exceptionMessage = "Finding all subjects operation exception!";
            LOGGER.error(exceptionMessage);
            throw new DatabaseRuntimeException(exception, exceptionMessage);
        }
    }

    @Override
    public void saveExams(List<Exam> exams) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("insert.exams"))) {

            for (Exam exam : exams) {
                statement.setString(1, exam.getUser().getEmail());
                statement.setString(2, exam.getSubject());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException exception) {
            String exceptionMessage = "Saving exams operation exception!";
            LOGGER.error(exceptionMessage);
            throw new DatabaseRuntimeException(exception, exceptionMessage);
        }
    }

    @Override
    public List<Exam> findExamsByEmail(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.exams.by.email"))) {

            statement.setString(1, email);

            return constructMultivaluedResult(statement.executeQuery());
        } catch (SQLException exception) {
            String exceptionMessage = "Finding exams by email operation exception!";
            LOGGER.error(exceptionMessage);
            throw new DatabaseRuntimeException(exception, exceptionMessage);
        }
    }


    @Override
    public List<Exam> findExamsPaginated(String subject, Integer page, Integer pageSize) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.exams.paginated"))) {

            statement.setString(1, subject);
            statement.setInt(2, (page * pageSize));
            statement.setInt(3, pageSize);

            return constructMultivaluedResult(statement.executeQuery());
        } catch (SQLException exception) {
            String exceptionMessage = "Finding exams paginated operation exception";
            LOGGER.error(exceptionMessage);
            throw new DatabaseRuntimeException(exception, exceptionMessage);
        }
    }

    @Override
    public Integer findExamsCountBySubject(String subject) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("select.exams.count.by.subject"))) {

            statement.setString(1, subject);

            return getIntResult(statement.executeQuery(), "count");
        } catch (SQLException exception) {
            String exceptionMessage = "Finding exams count by subject operation exception!";
            LOGGER.error(exceptionMessage);
            throw new DatabaseRuntimeException(exception, exceptionMessage);
        }
    }

    @Override
    public void setMarks(String subject, List<Exam> exams) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getSql("update.exams.marks.by.email"))) {

            for (Exam exam : exams) {
                statement.setInt(1, exam.getMark());
                statement.setString(2, exam.getUser().getEmail());
                statement.setString(3, subject);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException exception) {
            String exceptionMessage = "Setting marks operation exception!";
            LOGGER.error(exceptionMessage);
            throw new DatabaseRuntimeException(exception, exceptionMessage);
        }
    }
}
