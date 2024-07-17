package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRaiz(

        //Crear Lista Datos de los Libros
        @JsonAlias("results") List<DatosLibros> resultados
) {
}
