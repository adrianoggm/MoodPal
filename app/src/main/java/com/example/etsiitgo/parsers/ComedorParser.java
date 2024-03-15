package com.example.etsiitgo.parsers;

import com.example.etsiitgo.data.ComedorData;

public class ComedorParser implements PacketParser<ComedorData> {
    @Override
    public ComedorData parse(String packet){
        String[] splitted = packet.split("\r");

        if (!splitted[0].equals("comedor")) {
            return null;
        }

        int dias = 6;
        int platos = 3;
        String [][] menus = new String[dias][platos];
        String [][] fotos = new String[dias][platos];
        int i = 0;
        int j = 0;
        int k = 1;

        while(k < splitted.length){
            menus[i][j] = splitted[k];
            fotos[i][j] = splitted[k+1];

            k += 2;
            j += 1;
            if (j >= platos){
                j = 0;
                i += 1;
            }
        }

        return new ComedorData(menus,fotos);
    }
}
