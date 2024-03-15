package com.example.etsiitgo.data;

import com.example.etsiitgo.R;

public class ComedorData {

    private String[][]menu;
    private String[][]imagenes;
    private String[][] GeneraImagenes(){
        String [][]ima=new String[5][3];
        for(int i=0;i<5;i++){
            for(int j=0;j<3;j++){
                ima[i][j]= String.valueOf(R.drawable.fastfood_icon);
            }
        }
        ima[0][0] = String.valueOf(R.drawable.primero1);
        ima[0][1] = String.valueOf(R.drawable.segundo1);
        ima[0][2] = String.valueOf(R.drawable.postre1);
        ima[1][0] = String.valueOf(R.drawable.primero2);
        ima[1][1] = String.valueOf(R.drawable.segundo2);
        ima[1][2] = String.valueOf(R.drawable.postre2);
        ima[2][0] = String.valueOf(R.drawable.primero3);
        ima[2][1] = String.valueOf(R.drawable.segundo3);
        ima[2][2] = String.valueOf(R.drawable.postre3);
        ima[3][0] = String.valueOf(R.drawable.primero4);
        ima[3][1] = String.valueOf(R.drawable.segundo4);
        ima[3][2] = String.valueOf(R.drawable.postre4);
        ima[4][0] = String.valueOf(R.drawable.primero5);
        ima[4][1] = String.valueOf(R.drawable.segundo5);
        ima[4][2] = String.valueOf(R.drawable.postre5);

        return ima;
    };
    private String[][] GeneraMenu(){
        String [][]menus=new String[5][3];
        for(int i=0;i<5;i++){
            for(int j=0;j<3;j++){
                menus[i][j]=" ";
            }
        }
        menus[0][0] = "Primero: Ensalada de quinoa y aguacate ";
        menus[0][1] = "Segundo: Salmón a la parrilla con salsa de limón con Puré de papas con ajo ";
        menus[0][2]=  "Postre: Tarta de frutas frescas";
        //menus[1] = "Primero: Crema de champiñones \nSegundo: Pollo al curry con arroz basmati \nAcompañamiento: Brócoli al vapor con almendras \nPostre: Mousse de chocolate blanco \n";
        // menus[2] = "Primero: Gazpacho andaluz \nSegundo: Lasaña de espinacas y ricotta \nAcompañamiento: Espárragos a la parrilla \nPostre: Sorbete de limón \n";
        // menus[3] = "Primero: Sopa de tomate asado \nSegundo: Risotto de champiñones \nAcompañamiento: Zanahorias glaseadas \nPostre: Cheesecake de fresas \n";
        // menus[4] = "Primero: Ceviche de camarones \nSegundo: Tacos de pescado con salsa de mango \nAcompañamiento: Arroz con frijoles negros \nPostre: Flan de coco \n";
        // menus[5] = "Primero: Espárragos gratinados \nSegundo: Solomillo de ternera con salsa de vino tinto \nAcompañamiento: Puré de batatas \nPostre: Tiramisú \n";

        return menus;
    };

    public ComedorData(String [][] menu,String [][] imagenes) {
        this.menu = menu;
        this.imagenes=imagenes;
    }

    //CONSTRUCTOR CON HORARIO POR DEFECTO
    public ComedorData() {
        this.menu = GeneraMenu();
        this.imagenes=GeneraImagenes();
    }

    public String[][] getImagenes() {

        return imagenes;
    }
    public String[][] getMenu() {

        return menu;
    }

    public void setImagenes(String[][] imagenes) {
        this.imagenes = imagenes;
    }
}
