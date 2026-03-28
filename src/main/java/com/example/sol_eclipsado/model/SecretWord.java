package com.example.sol_eclipsado.model;

/**
 * Esta clase representa la palabra secreta del juego, su función es
 * almacenar la palabra secreta y contener la logica de validaciones
 * de la palabra en el juego.
 * */


/*
* Esta clase se rediseño para que solo pueda haber una palabra secreta, por eso sus métodos
* y atrivuto son estaticos.
* */

public class SecretWord {

    private static String secretWord;
    private static String secretWordAux;  // Esta variable guardara la palabra secretq tal y comose digito para mostrarla al final del juego.
    private static char[] secretWordArray;
    private static char[] lettersFound;
    private static SecretWord instansia;  // A travez de esta instancia pueden acceder a los demas metodos de la clase

    /*
    * El constructor es privado para que desde otras clases no puedan crear otros
    * objetos de esta clase, asegurando que solo halla una unica instancia.
    * Una variante del patron singleton.
    * */
    private SecretWord(){}

    public void setSecretWord(String secretWord) {
        SecretWord.secretWord = secretWord;
        SecretWord.lettersFound = new char[secretWord.length()];
        SecretWord.secretWordArray = secretWord.toLowerCase().toCharArray();  //Guarda las letras como minusculas para ignorar si el usuario ingresa letras minusculas o mayusculas
    }
    public String getSecretWord() {return secretWord;}
    public char[] getSecretWordArray() { return secretWordArray; }
    public static char[] getLettersFound() {return lettersFound; }

    public void setLettersFound(int index, char letter){
        lettersFound[index] = letter;
    }

    public static SecretWord getInstance(){
        if(instansia == null){
            instansia = new SecretWord();
        }
        return instansia;
    }

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
    public static boolean characterValidation(){
        return secretWord.matches("\\p{IsLatin}");
    }

    /*
    * Esta función valida si el tamaño de la palabra esta dentro del rango establecido
    * devuel true si lo esta y false si no lo esta.
    * */
    public static boolean sizeValidation(){
        return secretWord.length() < 6 || secretWord.length() > 12 ;
    }

    /*
    * Esta función comprueba si la palabra contiene algun espacio vacio con la función
    * contains devolviendo true si efectivamente los tiene y false si no los tiene.
    * */
    public static boolean spacingWordValidation(){
        return secretWord.contains(" ");
    }


}
