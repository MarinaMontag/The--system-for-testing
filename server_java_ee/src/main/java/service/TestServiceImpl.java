package service;

import dao.TestDAO;
import exception.ServerException;
import model.CreatedTest;
import model.QuestionList;
import model.Test;
import model.TestList;

public class TestServiceImpl implements TestService {
    @Override
    public TestList getTestsBySubjectId(int id) throws ServerException {
        return TestDAO.selectTestsBySubjectId(id);
    }
    @Override
    public QuestionList getQuestionAndAnswersByTestId(int id)throws ServerException{
        return TestDAO.selectQuestionsAndAnswersByTestId(id);
    }

    @Override
    public void createTest(CreatedTest test) throws ServerException {
       TestDAO.insertTest(test);
    }

    @Override
    public Test getTestById(int id) throws ServerException {
        return TestDAO.selectTestById(id);
    }
}

