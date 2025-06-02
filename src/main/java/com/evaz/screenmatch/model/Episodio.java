package com.evaz.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episodio {
    private Integer Temporada;
    private String Titulo;
    private Integer numEpisodio;
    private Double evaluacion;
    private LocalDate fechaLanzamiento;

    public Integer getTemporada() {
        return Temporada;
    }

    public void setTemporada(Integer temporada) {
        Temporada = temporada;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public Integer getNumEpisodio() {
        return numEpisodio;
    }

    public void setNumEpisodio(Integer numEpisodio) {
        this.numEpisodio = numEpisodio;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Episodio(Integer numero,DatosEpisodio d){
        this.Temporada = numero;
        this.Titulo = d.titulo();
        this.numEpisodio = d.episodio();

        try {
            this.evaluacion = Double.valueOf(d.evaluacionEp());
        }catch (NumberFormatException e){
            this.evaluacion = 0.0;
        }

    try {

        this.fechaLanzamiento = LocalDate.parse(d.fechaLanzamiento());

    } catch (DateTimeException e){

    this.fechaLanzamiento = null;

    }}

    @Override
    public String toString() {
        return
                "Temporada=" + Temporada +
                ", Titulo='" + Titulo + '\'' +
                ", numEpisodio=" + numEpisodio +
                ", evaluacion=" + evaluacion +
                ", fechaLanzamiento=" + fechaLanzamiento
                ;
    }
}
