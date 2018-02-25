package jz.demo.mongoreactive.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Movie implements Serializable{
    @Id
    private String id;
    private String name;
}
