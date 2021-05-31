package service;

import exception.ServerException;
import model.CreatedTest;
import model.QuestionList;
import model.Test;
import model.TestList;

public interface TestService {
    TestList getTestsBySubjectId(int id) throws ServerException;
    QuestionList getQuestionAndAnswersByTestId(int id)throws ServerException;
    void createTest(CreatedTest test)throws ServerException;
    Test getTestById(int id) throws ServerException;
}
