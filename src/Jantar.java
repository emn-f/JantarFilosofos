public class Jantar {
    public static void main(String[] args) {
        Mesa mesa = new Mesa();
        for (int filosofo = 0; filosofo < 5; filosofo++) {
            new Filosofo("Filosofo_" + filosofo, mesa, filosofo).start();
        }
    }
}