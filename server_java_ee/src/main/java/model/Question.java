package model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@ToString
@JsonPropertyOrder({"id", "testId", "text", "answerList"})
public class Question {
    private int id;
    private int testId;
    private String text;
    private List<Answer> answerList;

    public Question(int id, int testId, String text) {
        this.id = id;
        this.testId = testId;
        this.text = text;
        answerList=new ArrayList<>();
    }
}
