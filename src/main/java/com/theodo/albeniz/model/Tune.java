package com.theodo.albeniz.model;

import com.theodo.albeniz.constraints.NotChildrenSong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tune {
    private Integer id;
    @NotBlank(message = "title is mandatory")
    private String title;
    @NotChildrenSong
    private String author;
    @NotNull(groups = {MandatoryDate.class})
    private String releaseDate;

    public Tune(int id, @NotBlank(message = "title is mandatory") String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Tune(@NotBlank(message = "title is mandatory") String title, String author) {
        this.title = title;
        this.author = author;
    }

    public interface IgnoreDate
    {

    }

    public interface MandatoryDate
    {

    }

}


