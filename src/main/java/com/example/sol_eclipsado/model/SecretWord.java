package com.example.sol_eclipsado.model;

/**
 * Esta clase representa la palabra secreta del juego, su función es
 * almacenar la palabra secreta y contener la logica de validaciones
 * de la palabra en el juego.
 * */

public class SecretWord {

    private String secretWord;


    /*
    * Se le pasa la palabra al atrivuto, la idea es que en el controller se
    * valide la pabra antes de cambiar la vista con los métodos de
    * validaciones definidos en esta clase.
    * */
    public SecretWord(String secretWord){
        this.secretWord = secretWord;
    }

    public String getSecretWord() {return secretWord;}

    /*
    * Las validaciones se dividiran por tipos, mostrara un mensaje en la
    * vista correspondiente al tipo o tipos de validaciones que inflingio,
    * los tipos seran:
    * 1. Palabra solo con caracteres del alfabeto latino (incluyendo acentos).
    * 2. Palabra de entre 6 y 12 letras.
    * 3. Palabra sin espacio sinales, intermedios o iniciales.
    * opcional si queda tiempo: Palabra perteneciente al diccionario español.
    * */

    /*
    * Este metodo vallida si la palabra ingresada tiene unicamente caracteres latinos
    * incluyendo caracteres y espacios. Utiliza propiedades unicode y regex.
    * */
    public boolean characterValidation(){
        return secretWord.matches("\\p{IsLatin}");
    }

    /*
    * Esta función valida si el tamaño de la palabra esta dentro del rango establecido
    * devuel true si lo esta y false si no lo esta.
    * */
    public boolean sizeValidation(){
        return secretWord.length() < 6 || secretWord.length() > 12 ;
    }

    /*
    * Esta función comprueba si la palabra contiene algun espacio vacio con la función
    * contains devolviendo true si efectivamente los tiene y false si no los tiene.
    * */
    public boolean spacingWordValidation(){
        return secretWord.contains(" ");
    }


}
