package dao;

import exception.ServerException;
import model.*;
import util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {
    private static final String selectTestsBySubjectIdQuery =
            "SELECT * FROM tests WHERE s_id = ?";
    private static final String selectMaxTestIdQuery =
            "SELECT MAX(t_id) FROM tests";
    private static final String selectMaxQuestionIdQuery =
            "SELECT MAX(q_id) FROM ques";
    private static final String selectQuestionsByTestIdQuery =
            "SELECT * FROM ques WHERE t_id = ?";
    private static final String selectAnswersByQuestionIdQuery =
            "SELECT * FROM answ WHERE q_id = ?";
    private static final String selectTestByIdQuery =
            "SELECT * FROM tests WHERE t_id = ?";
    private static final String insertTestQuery =
            "INSERT INTO tests VALUES (DEFAULT, ?, ?, ?)";
    private static final String insertQuestionQuery =
            "INSERT INTO ques VALUES (DEFAULT, ?, ?)";
    private static final String insertAnswerQuery =
            "INSERT INTO answ VALUES (DEFAULT, ?, ?, ?)";
    public static TestList selectTestsBySubjectId(int id) throws ServerException {
        List<Test> tests;
        try(Connection conn = JdbcConnection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(selectTestsBySubjectIdQuery);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            tests=new ArrayList<>();
            while (resultSet.next()){
                tests.add(new Test(resultSet.getInt(1),
                                    resultSet.getInt(2),
                                    resultSet.getString(3),
                                    resultSet.getString(4)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServerException("can not select tests");
        }
        return new TestList(tests);
    }
    public static QuestionList selectQuestionsAndAnswersByTestId(int id) throws ServerException {
        List<Question>questions;
        try(Connection conn = JdbcConnection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuestionsByTestIdQuery);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            questions=new ArrayList<>();
            while (resultSet.next()){
                questions.add(new Question(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3)));
                List<Answer> answerList = selectAnswersByQuestionId(resultSet.getInt(1));
                questions.get(questions.size()-1).setAnswerList(answerList);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServerException("can not select tests");
        }
        return new QuestionList(questions);
    }

    public static void insertTest(CreatedTest test) throws ServerException {
        try(Connection conn = JdbcConnection.getConnection()){
            conn.setAutoCommit(false);
            PreparedStatement tps = conn.prepareStatement(insertTestQuery);
            PreparedStatement qps = conn.prepareStatement(insertQuestionQuery);
            PreparedStatement aps = conn.prepareStatement(insertAnswerQuery);
            PreparedStatement getMaxTestIdPS = conn.prepareStatement(selectMaxTestIdQuery);
            PreparedStatement getMaxQuestionIdPS = conn.prepareStatement(selectMaxQuestionIdQuery);
            Integer testId = insertTestInfo(test.getTestInfo(), tps, getMaxTestIdPS);
            Integer quesId;
            Integer answerResult;
            if(testId!=null) {
                for (Question question : test.getQuestions()) {
                    question.setTestId(testId);
                    quesId = insertQuestion(question, qps, getMaxQuestionIdPS);
                    if (quesId == null){
                        conn.rollback();
                        throw new ServerException("can not insert question");
                    }
                    for (Answer answer : question.getAnswerList()) {
                        answer.setQuestionId(quesId);
                        answerResult = insertAnswer(answer, aps);
                        if (answerResult == null) {
                            conn.rollback();
                            throw new ServerException("can not insert answer");
                        }
                    }
                }
            }else {
                conn.rollback();
                throw new ServerException("can not insert test");
            }
            conn.commit();
        } catch (SQLException | ClassNotFoundException throwables) {
            throw new ServerException("can not insert test");
        }
    }

    private static Integer insertTestInfo(Test test, PreparedStatement ps,
                                      PreparedStatement getMaxTestIdPS) throws SQLException
    {
        ResultSet rs = getMaxTestIdPS.executeQuery();
        Integer prevId = null;
        if(rs.next())
            prevId = rs.getInt(1);
        ps.setInt(1, test.getSubjectId());
        ps.setString(2, test.getName());
        ps.setString(3, test.getDescription());
        ps.executeUpdate();
        rs = getMaxTestIdPS.executeQuery();
        if(rs.next()){
            if( rs.getInt(1) != prevId)
                return rs.getInt(1);
        }
        return null;
    }

    private static Integer insertQuestion(Question question, PreparedStatement ps,
                                          PreparedStatement getMaxQuestionIdPS)
                                            throws SQLException
    {
        ResultSet rs = getMaxQuestionIdPS.executeQuery();
        Integer prevId = null;
        if(rs.next())
            prevId = rs.getInt(1);
        ps.setInt(1, question.getTestId());
        ps.setString(2, question.getText());
        ps.executeUpdate();
        rs = getMaxQuestionIdPS.executeQuery();
        if(rs.next()){
            if(rs.getInt(1) != prevId)
                return rs.getInt(1);
        }
        return null;
    }

    private static Integer insertAnswer(Answer answer, PreparedStatement ps)
            throws SQLException {
        ps.setInt(1, answer.getQuestionId());
        ps.setString(2, answer.getText());
        if(answer.isCorrectness())
            ps.setInt(3, 1);
        else
            ps.setInt(3, 0);
        int row = ps.executeUpdate();
        if(row<=0)
            return null;
        else return row;
    }

    private static List<Answer> selectAnswersByQuestionId(int id) throws ServerException {
        List<Answer>answers;
        try(Connection conn = JdbcConnection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(selectAnswersByQuestionIdQuery);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            answers=new ArrayList<>();
            while (resultSet.next()){
                answers.add(new Answer(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        getCorrectnessOfAnswer(resultSet.getInt(4))));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServerException("can not select answers");
        }
        return answers;
    }

    public static Test selectTestById(int id) throws ServerException {
        Test test = null;
        try(Connection conn = JdbcConnection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(selectTestByIdQuery);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                test = new Test(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
            }
            if(test == null)
                throw new ServerException("can not select test");
        } catch (SQLException | ClassNotFoundException | ServerException e) {
            throw new ServerException("can not select answers");
        }
        return test;
    }
    private static boolean getCorrectnessOfAnswer(int corr){
        return corr==1;
    }
}
