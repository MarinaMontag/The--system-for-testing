package model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonPropertyOrder({"id", "questionId", "text", "correctness"})
public class Answer {
    private int id;
    private int questionId;
    private String text;
    private boolean correctness;

    public Answer(String text, boolean correctness) {
        this.text = text;
        this.correctness = correctness;
    }
}
