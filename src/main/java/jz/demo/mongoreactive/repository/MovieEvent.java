package jz.demo.mongoreactive.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MovieEvent {
    private Movie movie;
    private Date date;
    private String user;
}
