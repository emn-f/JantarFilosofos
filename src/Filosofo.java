public class Filosofo extends Thread {
    final static int TEMPO_MAXIMO = 2500;
    final static int TEMPO_MINIMO = 500;
    
    private Mesa mesa;
    private int idFilosofo;

<<<<<<< HEAD
=======
    private int ciclosComendo = 0;
    private int ciclosPensando = 0;

>>>>>>> 4e908ca448e85d0fdc5639328b0c5262cc9f7fc2
    public Filosofo(String nome, Mesa mesa, int idFilosofo) {
        super(nome);
        this.mesa = mesa;
        this.idFilosofo = idFilosofo;
    }

    @Override
    public void run() {
        while (true) {
            pensar();
<<<<<<< HEAD
            mesa.pegarGarfos(idFilosofo);
            comer();
            mesa.devolverGarfos(idFilosofo);
=======

            long inicioEspera = System.currentTimeMillis();
            boolean pegouGarfos = mesa.pegarGarfos(idFilosofo, TEMPO_MAXIMO_ESPERA_GARFO);

            if (pegouGarfos) {
                int tempoComendo = comer();
                
                ciclosComendo++;
                mesa.devolverGarfos(idFilosofo, tempoComendo, ciclosComendo); 
            } else {
                System.out.println("!!! Filósofo " + idFilosofo + " não conseguiu comer (timeout) e voltou a PENSAR.");
            }
>>>>>>> 4e908ca448e85d0fdc5639328b0c5262cc9f7fc2
        }
    }

    private void pensar() {
        try {
<<<<<<< HEAD
            int tempo = TEMPO_MINIMO + (int) (Math.random() * TEMPO_MAXIMO);
            System.out.println("<<< Filósofo " + idFilosofo + " está PENSANDO >>>");
=======
            ciclosPensando++;
            int tempo = TEMPO_MINIMO + (int) (Math.random() * TEMPO_MAXIMO);
            
            System.out.println("<<< Filósofo " + idFilosofo + " está PENSANDO (Ciclo: " + ciclosPensando + ", " + tempo + "ms) >>>");
>>>>>>> 4e908ca448e85d0fdc5639328b0c5262cc9f7fc2
            sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void comer() {
        try {
<<<<<<< HEAD
            int tempo = TEMPO_MINIMO + (int) (Math.random() * TEMPO_MAXIMO);
            System.out.println(">>> Filósofo " + idFilosofo + " está COMENDO <<<");
=======
            tempo = TEMPO_MINIMO + (int) (Math.random() * TEMPO_MAXIMO);

            System.out.println(">>> Filósofo " + idFilosofo + " está COMENDO (Ciclo: " + (ciclosComendo + 1) + ") <<<");
>>>>>>> 4e908ca448e85d0fdc5639328b0c5262cc9f7fc2
            sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
<<<<<<< HEAD
=======
    
>>>>>>> 4e908ca448e85d0fdc5639328b0c5262cc9f7fc2
}