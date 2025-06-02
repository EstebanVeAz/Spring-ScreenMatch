package com.evaz.screenmatch.principal;
import com.evaz.screenmatch.model.DatosEpisodio;
import com.evaz.screenmatch.model.DatosSerie;
import com.evaz.screenmatch.model.DatosTemporada;
import com.evaz.screenmatch.model.Episodio;
import com.evaz.screenmatch.service.ConsumoApi;
import com.evaz.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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


        //mostrar solo el titulo de los episodios para las temporadas
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

         //convertir la informacion a una lista de tipo DatosEpisodio
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t-> t.episodios().stream())
                .collect(Collectors.toList());

        //top 5 episodios

        System.out.println("mejores 5 episodios ");
        datosEpisodios.stream()
                .filter(e ->!e.evaluacionEp().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacionEp).reversed())
                .limit(5)
                .forEach(System.out::println);



        //convirtiendo los datos a lista tipo Episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t ->
                        t.episodios().stream()
                                .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());
        episodios.forEach(System.out::println);

        // busqueda de episodio a partir del año
        System.out.println("Por favor indica el año a partir del cual deseas ver los episodios:");
        var fecha = teclado.nextInt();
        teclado.nextLine();

        LocalDate fechaBusqueda = LocalDate.of(fecha, 1,1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getFechaLanzamiento() != null && e.getFechaLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println(
                "Temporada = " + e.getTemporada() +" " +
                        "episodio = "+   e.getTitulo() +" " +
                        "Fecha de lanzamiento = " + e.getFechaLanzamiento().format(dtf)
        ));

         // busqueda episodios por pedazo del titulo
        System.out.println("Por favor escriba el titulo del episodio que desea ver :");
        var pedazoTitulo = teclado.nextLine();

        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
                .findFirst();
        if (episodioBuscado.isPresent()){
            System.out.println(" Episodio encontrado");
            System.out.println(" "+ episodioBuscado.get());
        }else {
            System.out.println("Episodio no encontrado");
        }

        Map<Integer,Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion()> 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion()> 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("Media de evaluaciones: " + est.getAverage());
        System.out.println("Espisodio mejor evaluado: " + est.getMax());
        System.out.println("Peor episodio: "+ est.getMin());
    }
}
