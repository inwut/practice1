import java.time.Duration;
import java.util.Objects;

public class Movie {
    private String name;
    private String genre;
    private Duration duration;

    public Movie(String name, String genre, Duration duration) {
        this.name = name;
        this.genre = genre;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        return true;
    }

    @Override
    public int hashCode() {
        return 5;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                '}';
    }
}
