/**
 * @author Alejandro Torres Rodríguez
 */
package com.example.etsiitgo.data;

public class Nota {
    private String asignatura;
    private String nota;

    public Nota(String asignatura, String nota) {
        this.asignatura = asignatura;
        this.nota = nota;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public String getNota() {
        return nota;
    }
}
