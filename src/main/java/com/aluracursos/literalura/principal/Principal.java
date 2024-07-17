package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.*;

public class Principal {

    //Crear Constante para URL
    private static final String URL = "https://gutendex.com/books/";
    //Instanciar Clases
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    //Crear Atributos
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository repositorio;
    private AutorRepository autorRepository;
    private String tituloLibro;
    private String nombreAutor;
    private String nacimientoAutor;
    private String fallecimientoAutor;
    private String resultado;



    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepository = autorRepository;
    }

    //Crear Metodo Mostrar Menu
    public void muestraMenu(){

        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    ************ LITERALURA ************
                    Elija la Opción a través del número:
                                        
                    1- Buscar libro por titulo
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Listar libros por idioma
                    0- Salir
                    ************************************
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    System.out.println("Ingrese el Nombre del Libro que desea buscar:");
                    tituloLibro = teclado.nextLine();
                    getDatosLibro();

                    if (resultado.equals("Libro Encontrado")) { // Verifica si el libro fue encontrado
                        getDatosAutor();
                        guardarDatosLibro();// Llama al método guardar libro encontrado
                    }
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    ListarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción No Valida..!");
            }
        }
    }

        //Crea Metodo Obtener Datos Libro
    private DatosLibros getDatosLibro() {
        var json = consumoAPI.obtenerDatos(URL + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, DatosRaiz.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()){
            resultado = "Libro Encontrado";
        }else {
            System.out.println("Libro No Encontrado");
            muestraMenu();
        }return libroBuscado.get();
    }

        //Crea Metodo Obtener Datos Autor
    private void getDatosAutor(){

        var json = consumoAPI.obtenerDatos(URL + "?search=" + tituloLibro .replace(" ", "+"));
        var datosDeAutor = conversor.obtenerDatos(json, DatosRaiz.class);
        Optional<DatosLibros> autorBuscado = datosDeAutor.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (autorBuscado.isPresent()) {
            DatosLibros libro = autorBuscado.get();
            nombreAutor = libro.autor().stream()
                    .map(DatosAutor::nombre)
                    .findFirst()
                    .orElse("");

            nacimientoAutor = libro.autor().stream()
                    .map(DatosAutor::fechaDeNacimiento)
                    .findFirst()
                    .orElse("");

            fallecimientoAutor = libro.autor().stream()
                    .map(DatosAutor::fechaFallecimiento)
                    .findFirst()
                    .orElse("");
        }
    }

    // Crea Metodo Imprimir Datos del Autor
    private void imprimirInformacionAutor (Autor autor){
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("Autor: " + autor.getNombre());
        System.out.println("  Fecha de Nacimiento: " + autor.getFechaDeNacimiento());
        System.out.println("  Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
        System.out.println("  Libros:");
        for (Libro libro : autor.getLibros()) {
            System.out.println("    - Título: " + libro.getTitulo());
            System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
            System.out.println("\n");
        }
    }

    //Opcion 1 Menu
    public void guardarDatosLibro() {

        List<DatosLibros> datosLibros = Collections.singletonList(getDatosLibro());

        for (DatosLibros datosLibro : datosLibros) {
            // Crea un objeto Libro
            Libro libro = new Libro(datosLibro);

            //Verificar si el Libro Existe en la Base de Datos
            Optional<Libro> libroExistente = repositorio.findByTitulo(libro.getTitulo());
            if (libroExistente.isPresent()) {
                // Si el libro ya existe, muestra un mensaje de error
                System.out.println("\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
                System.out.println("El libro con el título '" + libro.getTitulo() + "' Ya existe en la base de datos.");
                System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
            } else {

                // Crea un objeto Autor
                Autor autor = new Autor();
                autor.setNombre(nombreAutor);
                autor.setFechaDeNacimiento(Integer.valueOf(nacimientoAutor));
                autor.setFechaFallecimiento(Integer.valueOf(fallecimientoAutor));

                // Establece la relación entre Libro y Autor
                libro.setAutor(autor);
                autor.getLibros().add(libro); // Agrega el libro a la lista de libros del autor

                // Guarda el autor en la base de datos
                autorRepository.save(autor);
                System.out.println(libro.toString());
            }
        }
    }


    //Opcion 2 Menu
    private void listarLibrosRegistrados(){
        List<Libro> libros = repositorio.findAll();
        //libros = repositorio.findAll();
        System.out.println(libros.toString());
    }

    //Opcion 3 Menu
    private void listarAutoresRegistrados(){

        List<Autor> autoresConLibros = autorRepository.librosPorAutor();
        for (Autor autor : autoresConLibros) {
            imprimirInformacionAutor(autor);
        }
    }

    //Opcion 4 Menu
    private void ListarAutoresVivos(){
        System.out.println("Ingrese el año que desea buscar");
        var year = teclado.nextInt();
        List<Autor> autoresVivosPorFecha = autorRepository.librosPorFecha(year);
        for (Autor autor : autoresVivosPorFecha){
            imprimirInformacionAutor(autor);
        }
    }

    //Opcion 5 Menu
    private void listarLibrosPorIdioma(){
        System.out.println("""
                Inglés - [en]
                Español - [es]
                Francés - [fr]
                """);
        System.out.println("Escriba el Lenguaje por el cual desea hacer la busqueda");
        var idioma = teclado.nextLine();
        var lenguaje = Lenguaje.fromEspanol(idioma);
        List<Libro> librosPorIdioma = repositorio.findByIdioma(lenguaje);
        librosPorIdioma.forEach(System.out::println);
    }

}
