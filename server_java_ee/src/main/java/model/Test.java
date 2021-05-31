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
@JsonPropertyOrder({"id", "subjectId", "name", "description"})
public class Test {
    private int id;
    private int subjectId;
    private String name;
    private String description;
}
