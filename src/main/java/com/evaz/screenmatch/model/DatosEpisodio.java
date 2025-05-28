package com.evaz.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEpisodio(@JsonAlias("Title") String titulo,@JsonAlias("Episode") Integer episodio,
                           @JsonAlias("imdbRating") String evaluacionEp,@JsonAlias("Released") String fechaLanzamiento)
{

}
