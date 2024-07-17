package com.aluracursos.literalura.model;


import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    //Crear Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Lenguaje idioma;
    private Double numeroDeDescargas;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    //Crea Constructor PreDeterminado
    public Libro(){}

    //Crear Constructor
    public Libro(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.idioma = Lenguaje.fromString(datosLibros.idiomas().toString());
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }


    //Crear Getter and Setter
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Lenguaje getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = Lenguaje.valueOf(idioma);
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return
                "*-*-*-*-*-*-*-*-*-* LIBRO *-*-*-*-*-*-*-*-*-*" + '\'' + "\n" +
                " TITULO= " + titulo + '\'' + "\n" +
                " AUTOR= " +autor + '\'' + "\n" +
                " IDIOMA= " + idioma + '\'' + "\n" +
                " NUMERO DE DESCARGAS= " + numeroDeDescargas + '\'' + "\n" +
                "*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-**-*-*-*-" + "\n" + "\n";

    }
}
