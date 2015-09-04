package eu.thinking_aloud.portfolio.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Quicksort.
 */
@Document(collection = "QUICKSORT")
public class Quicksort implements Serializable {

    @Id
    private String id;


    @NotNull
    @Size(min = 1)
    @Pattern(regexp = "^([0-9]+(\\-[0-9]+)*,*)+$")
    @Field("unsorted")
    private String unsorted;

    @Field("sorted")
    private String sorted;

    @Field("execution_time")
    private Long executionTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnsorted() {
        return unsorted;
    }

    public void setUnsorted(String unsorted) {
        this.unsorted = unsorted;
    }

    public String getSorted() {
        return sorted;
    }

    public void setSorted(String sorted) {
        this.sorted = sorted;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Quicksort quicksort = (Quicksort) o;

        if ( ! Objects.equals(id, quicksort.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Quicksort{" +
                "id=" + id +
                ", unsorted='" + unsorted + "'" +
                ", sorted='" + sorted + "'" +
                ", executionTime='" + executionTime + "'" +
                '}';
    }
}
