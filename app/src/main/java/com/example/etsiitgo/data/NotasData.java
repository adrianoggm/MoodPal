/**
 * @author Alejandro Torres Rodríguez
 */

package com.example.etsiitgo.data;

import java.util.ArrayList;

public class NotasData {
    private String nombre;
    private String dni;
    private String mencion;
    private String curso;
    private ArrayList<Nota> notas;

    public NotasData(String nombre, String dni, String mencion, String curso, ArrayList<Nota> notas) {
        this.nombre = nombre;
        this.dni = dni;
        this.mencion = mencion;
        this.curso = curso;
        this.notas = notas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getMencion() {
        return mencion;
    }

    public String getCurso() {
        return curso;
    }

    public ArrayList<Nota> getNotas() {
        return notas;
    }
}
