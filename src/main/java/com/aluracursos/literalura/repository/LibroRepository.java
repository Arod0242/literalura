package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Lenguaje;
import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    List<Libro> findByIdioma(Lenguaje lenguaje);

    Optional<Libro> findByTitulo(String titulo);
}
