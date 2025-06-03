package com.evaz.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(@JsonAlias("Title") String titulo, @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String evaluacion,@JsonAlias("Genre") String genero,
                         @JsonAlias("Awards")String premios)
{

}
