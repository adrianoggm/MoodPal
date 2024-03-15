/**
 * @author Alejandro
 */

package com.example.etsiitgo.parsers;

import com.example.etsiitgo.data.Nota;
import com.example.etsiitgo.data.NotasData;

import java.util.ArrayList;

public class NotasParser implements PacketParser<NotasData> {
    private static int MIN_LENGTH = 6;
    @Override
    public NotasData parse(String packet) {
        String[] splitted = packet.split("\r");

        if (splitted.length < MIN_LENGTH) {
            return null;
        }

        if (!splitted[0].equals("notas")) {
            return null;
        }

        String nombre = splitted[1] + " " + splitted[2];
        String dni = splitted[3];
        String mencion = splitted[4];
        String curso = splitted[5];

        ArrayList<Nota> notas = new ArrayList<>();

        for (int i = 6; i < splitted.length; ++i) {
            Nota nota = new Nota(splitted[i], splitted[++i]);
            notas.add(nota);
        }

        return new NotasData(
                nombre,
                dni,
                mencion,
                curso,
                notas
        );
    }
}
