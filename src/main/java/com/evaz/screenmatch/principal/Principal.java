package com.evaz.screenmatch.principal;
import com.evaz.screenmatch.model.DatosEpisodio;
import com.evaz.screenmatch.model.DatosSerie;
import com.evaz.screenmatch.model.DatosTemporada;
import com.evaz.screenmatch.service.ConsumoApi;
import com.evaz.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY ="&apikey=88b4ce85";
    private final ConvierteDatos conversor = new ConvierteDatos();

    public void muestraMenu() {
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");

        //busca los datos generales de la serie deseada
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);
        //busca los datos de todas las temporadas
        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalTemporadas(); i++) {
            json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            var datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporada);
        }
        temporadas.forEach(System.out::println);

// mostrar solo el titulo de los episodios para las temporadas
        for (int i = 0; i < datos.totalTemporadas() ; i++) {
            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }
        //codigo anterior simplificado
//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
