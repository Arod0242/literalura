package com.aluracursos.literalura.service;

public interface IConvierteDatos {
    //Crear Metodo Generico
    <T> T obtenerDatos(String json, Class<T> clase);
}
