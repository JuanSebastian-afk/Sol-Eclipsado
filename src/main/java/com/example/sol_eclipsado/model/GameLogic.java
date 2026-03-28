package com.example.sol_eclipsado.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {


    private int quantityFailure = 0;
    private int quantityAid = 3;
    private static GameLogic instance;
    private static boolean win = false;

    private GameLogic(){}

    public static GameLogic getIntance(){
        if(instance == null){
            instance = new GameLogic();
        }

        return instance;
    }

    public int getQuantityAid() { return quantityAid; }
    public int getQuantityFailure(){ return quantityFailure; }
    public void setQuantityAid(int quantityAid) { this.quantityAid = quantityAid; }
    public void setQuantityFailure(int quantityFailure) { this.quantityFailure = quantityFailure; }
    public void setWin(boolean win){ this.win = win; }
    public boolean getWin(){return win; }

    public boolean losed(){
        return quantityFailure >= 5;  //Devuelve true si aun no llega a los 5 fallos, false si ya llego a los 5 fallos
    }

    //Este método comprueba si hay se encontraron todas las letras de la palabra secreta.
    public boolean win(){

        for(char c : SecretWord.getLettersFound()){
            if(c == '\u0000'){  //Cuando en Java se crea un arreglo se inicializan en nulo '\n0000' por defecto.
                return false;
            }
        }
        return true;
    }

    //Este método me devuelve una lista con las posiciones vacias que
    public int posicionVaciaLetterFound(){

        List<Integer> posicionesVacias = new ArrayList<>() {};  //Creamos una lista para almacenar las posiciones vacias.
        Random random = new Random();

        for(int i = 0; i < SecretWord.getInstance().getSecretWord().length(); i++){
            if(SecretWord.getLettersFound()[i] == '\u0000'){
                posicionesVacias.add(i);
                System.out.println("vuelta numero " + i);
            }
        }

        //INVESTIGAR COMO FUNCIONAN LAS LISTAS Y LOS OBJETOS RAMDOM.

        //return posicionesVacias.get(random.nextInt(posicionesVacias.size()));
        return posicionesVacias.get((int) (Math.random() * posicionesVacias.size()));
    }

}
