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
    private int id;
    @NotBlank(message = "title is mandatory")
    private String title;
    @NotChildrenSong
    private String author;
    @NotNull
    private String dateAppearence;



}
