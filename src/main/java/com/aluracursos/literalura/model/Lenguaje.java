package com.aluracursos.literalura.model;

public enum Lenguaje {
    INGLES("[en]", "Inglés"),
    ESPANOL("[es]", "Español"),
    FRANCES("[fr]", "Francés");

    //Creamos Atributo para el ENUM
    private String lenguajeOmdb;
    private String categoriaEspanol;
    Lenguaje (String lenguajeOmdb, String categoriaEspanol){
        this.lenguajeOmdb = lenguajeOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    //Crear Metodo para Lenguajes
    public static Lenguaje fromString(String text){
       for (Lenguaje lenguaje : Lenguaje.values()){
           if (lenguaje.lenguajeOmdb.equalsIgnoreCase(text)){
               return lenguaje;
           }
       }
       throw new IllegalArgumentException("Lenguaje No Encontrado: " + text);
    }

    public static Lenguaje fromEspanol(String text){
        for (Lenguaje lenguaje : Lenguaje.values()){
            if (lenguaje.categoriaEspanol.equalsIgnoreCase(text)){
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Lenguaje No Encontrado: " + text);
    }
}
