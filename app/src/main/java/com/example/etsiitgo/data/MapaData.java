package com.example.etsiitgo.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Pair;


// Deprecated, use routeManager instead
public class MapaData {
    private Map<String, List<Pair<String, Coordenada>>> mapa;

    public MapaData() {
        generaMapa();
    }

    public void generaMapa() {
        mapa = new HashMap<>();
        // Creamos coordenadas de los keypoints
        Pair<String, Coordenada> salida_pasillo_fuera = new Pair<>("Gira a la derecha y continua por el pasillo hasta encontrar una puerta que sale a la calle", new Coordenada(1, 2, 0));
        Pair<String, Coordenada> entrada_edificio = new Pair<>("Gira a la izquierda y continua recto hasta llegar al edificio y entra", new Coordenada(1, 2, 0));
        Pair<String, Coordenada> escaleras_edificio = new Pair<>("Gira a la derecha hasta encontrar las escaleras", new Coordenada(1, 2, 0));
        Pair<String, Coordenada> escaleras_comedor = new Pair<>("Gira a la derecha y continua por el pasillo hasta encontrar unas escaleras que bajan", new Coordenada(1, 2, 0));
        Coordenada totem = new Coordenada(1, 2, 0);

        // Ubicación comedor
        Coordenada comedor = new Coordenada(1, 2, 0);
        List<Pair<String, Coordenada>> instrucciones_comedor= List.of(escaleras_comedor, new Pair<>("Gira a la derecha y pasa el ascensor para llegar al comedor", comedor));
        mapa.put("Comedor", instrucciones_comedor);

        // Ubicación 0.9
        Coordenada aula_09 = new Coordenada(3, 4, 0);
        List<Pair<String, Coordenada>> instrucciones_aula_09= List.of(salida_pasillo_fuera, entrada_edificio, new Pair<>("Gira a la derecha y hasta encontrar el aula 0.9 ", aula_09));
        mapa.put("Aula 0.9", instrucciones_aula_09);

        // Ubicación 1.3
        Coordenada aula_13 = new Coordenada(5, 6, 0);
        List<Pair<String, Coordenada>> instrucciones_aula_13= List.of(salida_pasillo_fuera, entrada_edificio, escaleras_edificio, new Pair<>("Gira a la derecha y hasta encontrar el aula 1.3 ", aula_13));
        mapa.put("Aula 1.3", instrucciones_aula_13);

        // Ubicación 2.7
        Coordenada aula_27 = new Coordenada(7, 8, 0);
        List<Pair<String, Coordenada>> instrucciones_aula_27= List.of(salida_pasillo_fuera, entrada_edificio, escaleras_edificio, new Pair<>("Gira a la izquierda y hasta encontrar el aula 2.7 ", aula_27));
        mapa.put("Aula 2.7", instrucciones_aula_27);

        // Ubicación 3.1
        Coordenada aula_31 = new Coordenada(9, 10, 0);
        List<Pair<String, Coordenada>> instrucciones_aula_31= List.of(salida_pasillo_fuera, entrada_edificio, escaleras_edificio, new Pair<>("Gira a la derecha y hasta encontrar el aula 3.1 ", aula_31));
        mapa.put("Aula 3.1", instrucciones_aula_31);
    }

    public Map<String, List<Pair<String, Coordenada>>> getMapa(){
        return mapa;
    }

    // Clase para representar las coordenadas
    private static class Coordenada {
        private double x;
        private double y;
        private double pressure;

        public Coordenada(double x, double y, double pressure) {
            this.x = x;
            this.y = y;
            this.pressure = pressure;
        }

        // Getters
        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getPressure() {
            return pressure;
        }
    }
}
