package com.example.etsiitgo.data;

public class HorarioData {

    private String[][] horas = new String[6][7];

    private String[][] GeneraHorario(){
        String [][]horas=new String[6][7];
        //Inicializa valores a " " cadena vacía
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                horas[i][j]=" ";
            }
        }
        //Generamos el horario por defecto
        horas[1][0] = "SS\n3.8"; horas[2][0] = "SS\n3.8";
        horas[1][1] = "SS\n1.1"; horas[2][1] = "SS\n1.1"; horas[3][1] = "TIC\n2.2"; horas[4][1] = "TIC\n2.2";
        horas[1][2] = "TIC\n1.5"; horas[2][2] = "TIC\n1.5";
        horas[0][3] = "PL\n2.1"; horas[1][3] = "PL\n2.1"; horas[2][3] = "NPI\n1.1"; horas[3][3] = "NPI\n1.1"; horas[4][3] = "VC\n2.1"; horas[5][3] = "VC\n2.1";
        horas[0][4] = "VC\n1.1"; horas[1][4] = "VC\n1.1"; horas[2][4] = "NPI\n3.1"; horas[3][4] = "NPI\n3.1"; horas[4][4] = "PL\n1.1"; horas[5][4] = "PL\n1.1";

        return horas;
    };
    public HorarioData(String [][] horar) {
        this.horas = horar;
    }

    //CONSTRUCTOR CON HORARIO POR DEFECTO
    public HorarioData() {
        this.horas = GeneraHorario();
    }

    public String[][] gethoras() {

        return horas;
    }

}
