package jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Integer id;
    private String title;
    private String genre;
    private Integer yearOfRelease;

    public Movie(final String title, final String genre, final Integer yearOfRelease) {
        this.title = title;
        this.genre = genre;
        this.yearOfRelease = yearOfRelease;
    }
}