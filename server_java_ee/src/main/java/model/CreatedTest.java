package model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonPropertyOrder({"testInfo", "questions"})
public class CreatedTest {
    private Test testInfo;
    private List<Question>questions;
}
