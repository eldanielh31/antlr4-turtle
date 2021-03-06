package com.tec.turtle.ui;

import com.tec.turtle.TurtlePainter;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

import static com.tec.turtle.ui.TurtleFXUIController.boolError;

/**
 * Clase que se encarga de todas las acciones de la tortuga
 */
public class TurtleFXCanvasPainter implements TurtlePainter {

    /**
     * Variable que se encarga de la region de la ventana
     */
    private final Region region;
    /**
     * Variable que se encarga del canvas de dibujo
     */
    private final Group canvas;
    /**
     * Variable que se encarga de la anchura general
     */
    private final double anchura;
    /**
     * Variable que se encarga de la altura general
     */
    private final double altura;

    /**
     * Variable que se encarga de la direccion de la tortuga
     */
    private double direccion = 0;
    /**
     * Variable en donde se gurada la tortuga
     */
    private final Circle tortuga;
    /**
     * Variable que se encarga del color general
     */
    private Color color = Color.BLACK;

    /**
     * Variable que se encarga de indicar si se esta dibujando o no
     */
    private boolean seEstaDibujando = true;

    /**
     * Variable que se encarga de la animacion
     */
    private final SequentialTransition animacion = new SequentialTransition();
    /**
     * Variable que se encarga de la duracion de la animacion
     */
    private long duracionAnimacion = 100;

    /**
     * Constructor de la clase TurtleFXCanvasPainter
     * @param canvas Canvas de dibujo de la tortuga
     */
    public TurtleFXCanvasPainter(Group canvas) {
        this.canvas = canvas;
        this.region = (Region) this.canvas.getParent();
        this.anchura = this.region.getMaxWidth();
        this.altura = this.region.getMaxHeight();

        this.tortuga = new Circle(500, 340, 20, Color.RED);

        Image im = new Image("http://clipart-library.com/new_gallery/356-3560956_shadow-silhouette-turtle-black-freetoedit-sea-turtle-silhouette.png",false);
        tortuga.setFill(new ImagePattern(im));

        this.paintTurtle(this.tortuga.getCenterX(), this.tortuga.getCenterY());

        this.animacion.setCycleCount(1);
    }

    /**
     * Retorna el n elemento de una lista
     *
     * @param lista lista a buscar el elemento
     * @param n     posicion en la que se encuentra el elemento
     * @return numero encontrado
     */
    @Override
    public double elemento(List<Double> lista, int n) {
        return lista.get(n);
    }

    /**
     * Retorna el ultimo elemento de la lista
     *
     * @param lista lista a buscar el ultimo elemento
     * @return numero que se encuentra en la ultima posicion
     */
    @Override
    public double ultimo(List<Double> lista) {
        int ultimoelemento = lista.size();

        return lista.get(ultimoelemento - 1);
    }

    /**
     * Cuenta la cantidad de elementos de una lista
     *
     * @param lista lista a contar
     * @return entero respectivo a la cantidad de elementos
     */
    @Override
    public int cuenta(List<Double> lista) {
        return lista.size();
    }

    /**
     * Elige un numero al azar de una lista
     *
     * @param lista lista de la cual se elige
     * @return numero elegido
     */
    @Override
    public double elegir(List<Double> lista) {
        Random rand = new Random();
        return lista.get(rand.nextInt(lista.size()));
    }

    /**
     * Metodo que retorna el primer elemento de una lista
     *
     * @param lista lista a obtener el primer elemento
     * @return numero como primero elemento de la lista
     */
    @Override
    public double primero(List<Double> lista) {
        return lista.get(0);
    }

    /**
     * Metodo que se encarga del comando aparecer
     */
    @Override
    public void aparecer() {
        this.tortuga.setVisible(true);
    }

    /**
     * Metodo que se encarga del comando ocultar
     */
    @Override
    public void ocultar() {
        this.tortuga.setVisible(false);
    }

    /**
     * Metodo que se encarga del comando ponRumbo
     * @param rumbo Rumbo deseado del painter
     */
    @Override
    public void ponRumbo(int rumbo) {
        this.direccion = rumbo;
    }

    /**
     * Metodo que se encarga del comando Rumbo
     * @return Direccion de la tortuga
     */
    @Override
    public double rumbo() {
        return this.direccion;
    }

    /**
     * Metodo que se encarga del comando centro
     */
    @Override
    public void centro() {
        JavaFXThreadHelper.ejecutarOPosponer(() -> {
            final boolean seEstabaDibujando = this.seEstaDibujando;
            if (this.seEstaDibujando) {
                this.subelapiz();
            }
            this.moveTurtle(500, 340);
            if (seEstabaDibujando) {
                this.bajalapiz();
            }
        });

    }

    /**
     * Metodo que se encarga del comando espera
     * @param n Valor a esperar
     */
    @Override
    public void espera(final int n) {
        try {
            long segundos = (n * 1000L) / 60;
            Thread.sleep(segundos);
        } catch (InterruptedException e) {
            TurtleFXUIController.error(e.getMessage(), "excepcion");
        }
    }

    /**
     * Metodo que se encarga del comando ponx
     * @param point Valor a poner en x
     */
    @Override
    public void ponx(int point) {
        JavaFXThreadHelper.ejecutarOPosponer(() -> {
            final boolean wasPenDown = this.seEstaDibujando;
            if (this.seEstaDibujando) {
                this.subelapiz();
            }
            this.validarPosicionTortuga(point, this.tortuga.getCenterY());
            this.moveTurtle(point, this.tortuga.getCenterY());
            if (wasPenDown) {
                this.bajalapiz();
            }
        });
    }

    /**
     * Metodo que se encarga del comando pony
     * @param point Valor a poner en y
     */
    @Override
    public void pony(int point) {
        JavaFXThreadHelper.ejecutarOPosponer(() -> {
            final boolean wasPenDown = this.seEstaDibujando;
            if (this.seEstaDibujando) {
                this.subelapiz();
            }
            this.validarPosicionTortuga(this.tortuga.getCenterX(), point);
            this.moveTurtle(this.tortuga.getCenterX(), point);
            if (wasPenDown) {
                this.bajalapiz();
            }
        });
    }

    /**
     * Metodo que se encarga del comando poncolorlapiz
     * @param color Valor del color del lapiz usado
     */
    @Override
    public void poncolorlapiz(String color) {
        if (color.equals("amarillo")){
            this.color = Color.YELLOW;
        }
        if (color.equals("azul")){
            this.color = Color.BLUE;
        }
        if (color.equals("marron")){
            this.color = Color.BROWN;
        }
        if (color.equals("cian")){
            this.color = Color.CYAN;
        }
        if (color.equals("gris")){
            this.color = Color.GRAY;
        }
        if (color.equals("negro")){
            this.color = Color.BLACK;
        }
        if (color.equals("verde")){
            this.color = Color.GREEN;
        }
        if (color.equals("rojo")){
            this.color = Color.RED;
        }

    }

    /**
     * Metodo que se encarga del comando goma
     */
    @Override
    public void goma() {
        this.color = Color.WHITE;
    }

    /**
     * Metodo que se encarga del comando azar
     * @param n Valor a utilizar
     * @return Valor escogido
     */
    @Override
    public int azar(int n) {
        return (int) (Math.random() * (n - 1)) + 1;
    }

    /**
     * Metodo que se encarga del comando inc
     * @param x Cantidad a incrementar
     * @param n Valor de incremento
     * @return Valor incrementado
     */
    @Override
    public int inc(Integer x, Integer n) {

        if (n == null){
            n = 1;
        }
        int result = x;
        while (n > 0){
            result += 1;
            n--;
        }
        return result;
    }

    /**
     * Metodo que se encarga del comando suma
     * @param numeros Valores a utilizar
     * @return Resultado de la suma
     */
    @Override
    public int suma(List<Integer> numeros) {
        int respuesta = 0;
        for(Integer x : numeros){
            respuesta += x;
        }
        return respuesta;
    }

    /**
     * Metodo que se encarga del comando diferencia
     * @param numeros Valores a utilizar
     * @return Resultado de la diferencia
     */
    @Override
    public int diferencia(List<Integer> numeros) {
        int respuesta = 0;
        for(Integer x : numeros){
            respuesta -= x;
        }
        return respuesta;
    }

    /**
     * Metodo que se encarga del comando menos
     * @param x Valor a utilizar
     * @return Valor restado
     */
    @Override
    public int menos(int x) {
        return x - (2 * x);
    }

    /**
     * Metodo que se encarga del comando resto
     * @param x Valor a restar
     * @param y Valor restado
     * @return Resultado de resta
     */
    @Override
    public int resto(int x, int y) {
        return x - y;
    }

    /**
     * Metodo que se encarga del comando potencia
     * @param n Valor a incrementar
     * @param p Valor de potencia
     * @return Valor incrementado en la potencia dada
     */
    @Override
    public double potencia(int n, int p) {
        return Math.pow(n, p);
    }

    /**
     * Metodo que se encarga del comando producto
     * @param numeros Valores a utilizar
     * @return Resultado de la multiplicacion
     */
    @Override
    public int producto(List<Integer> numeros) {
        int respuesta = 1;
        for(Integer x : numeros){
            respuesta *= x;
        }
        return respuesta;
    }

    /**
     * Metodo que se encarga del comando division
     * @param x Valor a dividir
     * @param y Valor usado para dividir
     * @return Resultado de la division
     */
    @Override
    public int division(int x, int y) {
        return x / y;
    }

    /**
     * Metodo que se encarga del comando redondea
     * @param x Valor a redondear
     * @return Valor redondeado
     */
    @Override
    public Long redondea(double x) {
        return Math.round(x);
    }

    /**
     * Metodo que se encarga del comando o logico
     * @param condicion Condicion dada
     * @param condicion2 Condicion dada
     * @return Resultado de la evaluacion de las condiciones dadas
     */
    @Override
    public boolean o(boolean condicion, boolean condicion2) {
        return condicion || condicion2;
    }

    /**
     * Metodo que se encarga del comando y logico
     * @param condicion Condicion dada
     * @param condicion2 Condicion dada
     * @return Resultado de la evaluacion de las condiciones dadas
     */
    @Override
    public boolean y(boolean condicion, boolean condicion2) {
        return condicion && condicion2;
    }

    /**
     * Metodo que se encarga del comando iguales
     * @param x Valor dado
     * @param y Valor dado
     * @return Resultado de la comparacion de los valores dados
     */
    @Override
    public boolean iguales(int x, int y) {
        return x == y;
    }

    /**
     * Metodo que se encarga del comando menorque
     * @param x Valor dado
     * @param y Valor dado
     * @return Resultado de la evaluacion de los valores dados
     */
    @Override
    public boolean menorque(int x, int y) {
        return x < y;
    }

    /**
     * Metodo que se encarga del comando mayorque
     * @param x Valor dado
     * @param y Valor dado
     * @return Resultado de la evaluacion de los valores dados
     */
    @Override
    public boolean mayorque(int x, int y) {
        return x > y;
    }

    /**
     * Metodo que se encarga del comando avanza
     * @param x Cantidad de puntos a moverse hacia adelante
     */
    @Override
    public void avanza(int x) {
        JavaFXThreadHelper.ejecutarOPosponer(() -> {
            final double radian = this.toRadian(this.direccion);
            final double x1 = this.tortuga.getCenterX() + x * Math.cos(radian);
            final double y = this.tortuga.getCenterY() - x * Math.sin(radian);

            this.validarPosicionTortuga(x1, y);

            this.moveTurtle(x1, y);
        });
    }

    /**
     * Metodo que se encarga del comando retrocede
     * @param x Cantidad de puntos a moverse hacia atras
     */
    @Override
    public void retrocede(final int x) {
        Platform.runLater(() -> {
            final double radian = this.toRadian(this.direccion);
            final double x1 = this.tortuga.getCenterX() - x * Math.cos(radian);
            final double y = this.tortuga.getCenterY() + x * Math.sin(radian);

            this.validarPosicionTortuga(x1, y);

            this.moveTurtle(x1, y);
        });
    }

    /**
     * Metodo que se encarga del comando giraderecha
     * @param angulo Cantidad de grados a moverse hacia la derecha
     */
    @Override
    public void giraderecha(final int angulo) {
        Platform.runLater(() -> this.direccion = (this.direccion - angulo) % 360);
    }

    /**
     * Metodo que se encarga del comando giraizquierda
     * @param angulo Cantidad de grados a moverse hacia la izquierda
     */
    @Override
    public void giraizquierda(final int angulo) {
        Platform.runLater(() -> this.direccion = (this.direccion + angulo) % 360);
    }

    /**
     * Metodo que se encarga del comando ponxy
     * @param x Valor a establecer
     * @param y Valor a establecer
     */
    @Override
    public void ponxy(final int x, final int y) {
        try {
            validarPosicionTortuga(x, y);

            JavaFXThreadHelper.ejecutarOPosponer(() -> {
                final boolean wasPenDown = this.seEstaDibujando;
                if (this.seEstaDibujando) {
                    this.subelapiz();
                }
                this.validarPosicionTortuga(x,y);
                this.moveTurtle(x, y);
                if (wasPenDown) {
                    this.bajalapiz();
                }
            });

        } catch (Exception e){
            TurtleFXUIController.error(e.getMessage(), "excepcion");
        }
    }

    /**
     * Metodo que se encarga del comando subelapiz
     */
    @Override
    public void subelapiz() {
        JavaFXThreadHelper.ejecutarOPosponer(() -> this.seEstaDibujando = false);
    }

    /**
     * Metodo que se encarga del comando bajalapiz
     */
    @Override
    public void bajalapiz() {
        JavaFXThreadHelper.ejecutarOPosponer(() -> this.seEstaDibujando = true);
    }

    /**
     * Metodo que se encarga del comando borrapantalla
     */
    @Override
    public void borrapantalla() {
        JavaFXThreadHelper.ejecutarOPosponer(() -> {
            this.animacion.getChildren().clear();
            this.canvas.getChildren().clear();
            this.paintTurtle(this.region.getWidth() / 2, this.region.getHeight() / 2);
        });
    }

    /**
     * Metodo que se encarga del comando resetAngle
     */
    @Override
    public void resetAngle() {
        JavaFXThreadHelper.ejecutarOPosponer(()-> this.direccion = 0);
    }

    /**
     * Metodo que se encarga del comando finish
     */
    @Override
    public void finish() {
        JavaFXThreadHelper.ejecutarOPosponer(this.animacion::play);
    }

    /**
     * Metodo que se encarga del comando moveTurtle
     * @param x Posicion en eje x
     * @param y Posicion en eje y
     */
    private void moveTurtle(final double x, final double y) {
        JavaFXThreadHelper.ejecutarOPosponer(() -> {

            final Path path = new Path();
            path.getElements().add(new MoveTo(this.tortuga.getCenterX(), this.tortuga.getCenterY()));
            path.getElements().add(new LineTo(x, y));

            final PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(this.duracionAnimacion));
            pathTransition.setPath(path);
            pathTransition.setNode(this.tortuga);

            if (this.seEstaDibujando) {
                final Line line = new Line(this.tortuga.getCenterX(), this.tortuga.getCenterY(), x, y);
                line.setStroke(color);
                pathTransition.setOnFinished(onFinished -> this.canvas.getChildren().add(line));
            }

            animacion.getChildren().add(pathTransition);
            this.validarPosicionTortuga(x,y);
            this.paintTurtle(x, y);
        });
    }

    /**
     * Metodo que valida la posicion de la tortuga en le canvas de dibujo
     * @param newX Nueva posicion en eje x
     * @param newY Nueva posicion en eje Y
     */
    private void validarPosicionTortuga(final double newX, final double newY) {
        JavaFXThreadHelper.ejecutarOPosponer(() -> {
            if ((newX < 0 || newX > this.anchura) || (newY < 0 || newY > this.altura)) {
                if (!boolError) {
                    try {
                        throw new Exception(String.format("-> La tortuga se salio del canvas! " +
                                        "\n\n    - Dimensiones del canvas = (%f, %f), " +
                                        "\n    - Posicion actual = (%f, %f), " +
                                        "\n    - Nueva posicion = (%f, %f)",
                                        this.anchura, this.altura,
                                        this.tortuga.getCenterX(), this.tortuga.getCenterY(),
                                        newX, newY));
                    } catch (Exception e){
                        TurtleFXUIController.error(e.getMessage(),"excepcion");
                    }
                }
            }
        });
    }

    /**
     * Metodo que se encarga del comando paintTurtle
     * @param x Posicion en eje x
     * @param y Posicion en eje y
     */
    private void paintTurtle(final double x, final double y) {
        JavaFXThreadHelper.ejecutarOPosponer(() -> {
            this.validarPosicionTortuga(x,y);
            this.tortuga.setCenterX(x);
            this.tortuga.setCenterY(y);

            this.canvas.getChildren().remove(this.tortuga);
            this.canvas.getChildren().add(this.tortuga);
        });
    }

    /**
     * Metodo que se encarga del comando toRadian
     * @param direction Posicion a cambiar
     * @return Posicion en radianes
     */
    private double toRadian(final double direction) {
        return direction * Math.PI / 180;
    }

    /**
     * Metodo que se encarga del comando setDuracionAnimacion
     * @param duracionAnimacion Duracion de la animacion
     */
    public void setDuracionAnimacion(final long duracionAnimacion) {
        this.duracionAnimacion = duracionAnimacion;
    }
}
