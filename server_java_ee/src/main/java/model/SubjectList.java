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
@JsonPropertyOrder({"subjectList"})
public class SubjectList {
    private List<Subject>subjectList;
}
