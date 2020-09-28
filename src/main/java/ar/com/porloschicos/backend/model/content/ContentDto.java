package ar.com.porloschicos.backend.model.content;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ContentDto {

    @NonNull
    private long id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    @NonNull
    private String images;

    @NonNull
    private Integer type;

    @NonNull
    private Integer author;

    @NonNull
    private Boolean status;

    @NonNull
    private Timestamp created_at;

    @NonNull
    private Timestamp updated_at;


}
