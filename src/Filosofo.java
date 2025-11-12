public class Filosofo extends Thread {
    final static int TEMPO_MAXIMO = 2500;
    final static int TEMPO_MINIMO = 500;
    final static long TEMPO_MAXIMO_ESPERA_GARFO = 1000;

    private Mesa mesa;
    private int idFilosofo;

    private int ciclosComendo = 0;
    private int ciclosPensando = 0;

    public Filosofo(String nome, Mesa mesa, int idFilosofo) {
        super(nome);
        this.mesa = mesa;
        this.idFilosofo = idFilosofo;
    }

    @Override
    public void run() {
        while (true) {
            pensar();

            long inicioEspera = System.currentTimeMillis();
            boolean pegouGarfos = mesa.pegarGarfos(idFilosofo, TEMPO_MAXIMO_ESPERA_GARFO);

            if (pegouGarfos) {
                int tempoComendo = comer();
                
                ciclosComendo++;
                mesa.devolverGarfos(idFilosofo, tempoComendo, ciclosComendo); 
            } else {
                System.out.println("!!! Filósofo " + idFilosofo + " não conseguiu comer (timeout) e voltou a PENSAR.");
            }
        }
    }

    private void pensar() {
        try {
            ciclosPensando++;
            int tempo = TEMPO_MINIMO + (int) (Math.random() * TEMPO_MAXIMO);
            
            System.out.println("<<< Filósofo " + idFilosofo + " está PENSANDO (Ciclo: " + ciclosPensando + ", " + tempo + "ms) >>>");
            sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private int comer() {
        int tempo = 0;
        try {
            tempo = TEMPO_MINIMO + (int) (Math.random() * TEMPO_MAXIMO);

            System.out.println(">>> Filósofo " + idFilosofo + " está COMENDO (Ciclo: " + (ciclosComendo + 1) + ") <<<");
            sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return tempo; 
    }
    
}