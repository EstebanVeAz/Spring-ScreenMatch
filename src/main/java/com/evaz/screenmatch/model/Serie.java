package com.evaz.screenmatch.model;

import java.util.OptionalDouble;

public class Serie{

private String titulo;
private Integer totalTemporadas;
private Double evaluacion;
private Categoria genero;
private String premios;
private String sinopsis;

public Serie(DatosSerie datosSerie){
    this.titulo = datosSerie.titulo();
    this.totalTemporadas = datosSerie.totalTemporadas();
    this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0);
    this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());
    this.premios = datosSerie.premios();
    this.sinopsis = datosSerie.sinopsis();

}

    @Override
    public String toString() {
        return
                "genero=" + genero +
                ",titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", evaluacion=" + evaluacion +
                ", sinopsis= "+ sinopsis +
                ", premios='" + premios + '\'';
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getPremios() {
        return premios;
    }

    public void setPremios(String premios) {
        this.premios = premios;
    }
}
