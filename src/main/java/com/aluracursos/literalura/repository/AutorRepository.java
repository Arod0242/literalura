package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a JOIN Libro l ON a.id = l.autor.id")
    List<Autor> librosPorAutor();

    @Query("SELECT a, l.titulo FROM Autor a JOIN Libro l ON a.id = l.autor.id WHERE :year BETWEEN a.fechaDeNacimiento AND a.fechaFallecimiento")
    List<Autor> librosPorFecha(Integer year);

}
