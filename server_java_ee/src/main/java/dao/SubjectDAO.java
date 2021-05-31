package dao;

import exception.ServerException;
import model.Subject;
import model.SubjectList;
import util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private static final String selectAllQuery =
            "SELECT * FROM subj";
    private static final String selectMaxId =
            "SELECT max(s_id) FROM subj";
    private static final String selectMinId =
            "SELECT min(s_id) FROM subj";

    public static SubjectList getAllSubjects() throws ServerException {
        List<Subject>subjects;
        try(Connection conn = JdbcConnection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(selectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            subjects=new ArrayList<>();
            while (resultSet.next()){
                subjects.add(new Subject(resultSet.getInt(1), resultSet.getString(2)));
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new ServerException("can not select subjects");
        }
        return new SubjectList(subjects);
    }

    private static int getId(String command, String exception) throws ServerException {
        try(Connection conn = JdbcConnection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
            else
                throw new ServerException("No subjects in table subj");
        }catch (SQLException | ClassNotFoundException e){
            throw new ServerException(exception);
        }
    }
    public static int getMaxSubjId() throws ServerException {
        return getId(selectMaxId, "can not select maximum subject id");
    }
    public static int getMinSubjId() throws ServerException {
        return getId(selectMinId, "can not select minimum subject id");
    }
}
