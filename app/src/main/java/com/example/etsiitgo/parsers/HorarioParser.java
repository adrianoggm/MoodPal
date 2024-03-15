package com.example.etsiitgo.parsers;
import com.example.etsiitgo.data.HorarioData;

public class HorarioParser implements PacketParser<HorarioData> {
    @Override
    public HorarioData parse(String packet){
        String[] splitted = packet.split("\r");

        if (!splitted[0].equals("horario")) {
            return null;
        }

        int dias = 6;
        int horas = 6;
        String [][] horario = new String[dias][horas];
        int i = 0;
        int j = 0;
        int k = 1;

        while(k < splitted.length){
            horario[i][j] = splitted[k];

            k += 1;
            j += 1;
            if (j >= horas){
                j = 0;
                i += 1;
            }
        }

        return new HorarioData(horario);
    }
}



