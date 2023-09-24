import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

class Carta {
    private String palo;
    private String valor;

    public Carta(String palo, String valor) {
        this.palo = palo;
        this.valor = valor;
    }

    public double getValorNumerico() {
        if (valor.equals("J") || valor.equals("Q") || valor.equals("K")) {
            return 0.5;
        } else if (valor.equals("A")) {
            return 1;
        } else {
            return Integer.parseInt(valor);
        }
    }

    @Override
    public String toString() {
        return valor + " de " + palo;
    }
}

class PilaCartas {
    private List<Carta> elementos;
    private int capacidad;
    private Random rand = new Random();

    public PilaCartas(int capacidad) {
        this.capacidad = capacidad;
        this.elementos = new ArrayList<>(capacidad);
    }

    public void push(Carta carta) {
        if (elementos.size() < capacidad) {
            elementos.add(carta);
        } else {
            System.out.println("La pila de cartas está llena, no se puede agregar más elementos.");
        }
    }

    public Carta pop() {
        if (!elementos.isEmpty()) {
            int index = rand.nextInt(elementos.size());
            return elementos.remove(index);
        } else {
            System.out.println("La pila de cartas está vacía, no se puede realizar pop.");
            return null;
        }
    }

    public void mezclar() {
        int n = elementos.size();
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Carta temp = elementos.get(i);
            elementos.set(i, elementos.get(j));
            elementos.set(j, temp);
        }
    }

    public int contarElementos() {
        return elementos.size();
    }
}

class Baraja {
    private PilaCartas cartas;

    public Baraja() {
        cartas = new PilaCartas(52);
        String[] palos = {"Oros", "Copas", "Espadas", "Bastos"};
        String[] valores = {"1", "2", "3", "4", "5", "6", "7", "J", "Q", "K"};

        for (String palo : palos) {
            for (String valor : valores) {
                cartas.push(new Carta(palo, valor));
            }
        }
        cartas.mezclar();
    }

    public Carta sacarCarta() {
        return cartas.pop();
    }
}

class Juego75 {
    private Baraja baraja;
    private List<Carta> mano;
    private double puntos;
    private Baraja baraja2;
    private List<Carta> mano2;
    private double puntos2;
    public Juego75() {
        baraja = new Baraja();
        mano = new ArrayList<>();
        puntos = 0;
        mano2 = new ArrayList<>();
        puntos2 = 0;
    }

    public void jugar() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Carta carta = baraja.sacarCarta();
            if (carta == null) {
                System.out.println("No quedan cartas en la baraja. Fin del juego.");
                break;
            }
            mano.add(carta);
            puntos += carta.getValorNumerico();

            System.out.println("Carta: " + carta);
            System.out.println("Puntos: " + puntos);

            if (puntos > 7.5) {
                System.out.println("Has perdido. Fin del juego.");
                break;
            }

            System.out.print("¿Deseas tomar otra carta? (S/N): ");
            String respuesta = scanner.next().trim().toUpperCase();

            if (!respuesta.equals("S")&& puntos<=7.5) {

                while (true){
                    System.out.println("Turno del dealer.");
                    carta = baraja.sacarCarta();
                    if (carta == null) {
                        break;
                    }
                    mano2.add(carta);
                    puntos2 += carta.getValorNumerico();

                    System.out.println("Carta: " + carta);
                    System.out.println("Puntos: " + puntos2);
                    String respuestaDealer="";
                    if(puntos2<7.5){
                        System.out.print("¿Deseas tomar otra carta? (S/N): ");
                        respuestaDealer = scanner.next().trim().toUpperCase();
                    }
                    if (!respuestaDealer.equals("S")&& puntos2<=7.5){
                        break;
                    } else if (puntos2>7.5) {
                        break;
                    }
                }

                if (puntos2 > 7.5) {
                    System.out.println("Has Ganado. El dealer se ha pasado. Fin del juego.");
                    break;
                } else if (puntos>puntos2 && puntos<=7.5) {
                    System.out.println("Has Ganado. Fin del juego. Tienes:"+puntos+" y el dealer tiene:"+puntos2+".");
                    break;
                } else if (puntos2>puntos && puntos2<=7.5) {
                    System.out.println("Has Perdido. Fin del juego. Tienes:"+puntos+" y el dealer tiene:"+puntos2+".");
                    break;
                } else if (puntos2==puntos && puntos2<=7.5 && puntos<=7.5) {
                    System.out.println("Has empatado. Fin del juego. Tienes:"+puntos+" y el dealer tiene:"+puntos2+".");
                    break;
                }else if (carta==null){
                    System.out.println("No quedan cartas en la baraja. Fin del juego.");
                    break;
                }
            }
        }
        scanner.close();
    }
}

public class Main {
    public static void main(String[] args) {
        Juego75 juego = new Juego75();
        juego.jugar();
    }
}
