package service;

import exception.ServerException;
import model.Subject;
import model.SubjectList;

import java.util.List;

public interface SubjectService {
    SubjectList getSubjects() throws ServerException;
    int getMinId() throws ServerException;
    int getMaxId() throws ServerException;
}
