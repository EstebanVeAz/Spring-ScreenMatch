package com.evaz.screenmatch;

import com.evaz.screenmatch.model.DatosEpisodio;
import com.evaz.screenmatch.model.DatosSerie;
import com.evaz.screenmatch.model.DatosTemporada;
import com.evaz.screenmatch.service.ConsumoApi;
import com.evaz.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//datos generales de la serie
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=Game%20of%20Thrones&apikey=88b4ce85");
		ConvierteDatos conversor = new ConvierteDatos();
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		System.out.println(datos);

		//datos de un episodio
		json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1&episode=1&apikey=88b4ce85");
		DatosEpisodio episodio = conversor.obtenerDatos(json, DatosEpisodio.class);
		System.out.println(episodio);

		// datos de una temporada
		List<DatosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i < datos.totalTemporadas(); i++) {
			json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=Game%20of%20Thrones&Season="+i+"&apikey=88b4ce85");
			var datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
			temporadas.add(datosTemporada);
		}
		temporadas.forEach(System.out::println);
	}
}