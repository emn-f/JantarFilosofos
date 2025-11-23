public class Filosofo extends Thread {
    final static int TEMPO_MAXIMO = 2500;
    final static int TEMPO_MINIMO = 500;
    
    private Mesa mesa;
    private int idFilosofo;

    public Filosofo(String nome, Mesa mesa, int idFilosofo) {
        super(nome);
        this.mesa = mesa;
        this.idFilosofo = idFilosofo;
    }

    @Override
    public void run() {
        while (true) {
            pensar();
            mesa.pegarGarfos(idFilosofo);
            comer();
            mesa.devolverGarfos(idFilosofo);
        }
    }

    private void pensar() {
        try {
            int tempo = TEMPO_MINIMO + (int) (Math.random() * TEMPO_MAXIMO);
            System.out.println("<<< Filósofo " + idFilosofo + " está PENSANDO >>>");
            sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void comer() {
        try {
            int tempo = TEMPO_MINIMO + (int) (Math.random() * TEMPO_MAXIMO);
            System.out.println(">>> Filósofo " + idFilosofo + " está COMENDO <<<");
            sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}