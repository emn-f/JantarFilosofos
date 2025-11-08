public class Filosofo extends Thread {
    final static int TEMPO_MAXIMO = 2500;
    final static int TEMPO_MINIMO = 500;
    final static long TEMPO_MAXIMO_ESPERA_GARFO = 1000;

    private Mesa mesa;
    private int idFilosofo;

    // Req: Contadores de ciclo para cada ação
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
            pensar(); // O log do contador de "pensar" está dentro deste método

            long inicioEspera = System.currentTimeMillis();
            boolean pegouGarfos = mesa.pegarGarfos(idFilosofo, TEMPO_MAXIMO_ESPERA_GARFO);
            // (Não precisamos mais acumular o tempo de espera)

            if (pegouGarfos) {
                int tempoComendo = comer(); // O log "está comendo" está aqui
                
                ciclosComendo++; // Incrementa o contador
                
                // Passa o tempo e o novo contador para o método devolverGarfos
                mesa.devolverGarfos(idFilosofo, tempoComendo, ciclosComendo); 
            } else {
                System.out.println("!!! Filósofo " + idFilosofo + " não conseguiu comer (timeout) e voltou a PENSAR.");
            }
        }
    }

    private void pensar() {
        try {
            ciclosPensando++; // Req: Incrementa o contador de "pensar"
            int tempo = TEMPO_MINIMO + (int) (Math.random() * TEMPO_MAXIMO);
            
            // Req: Exibe o contador de "pensar"
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
            
            // Req: Exibe o contador de "comer" (usamos +1 pq ele só é incrementado depois)
            System.out.println(">>> Filósofo " + idFilosofo + " está COMENDO (Ciclo: " + (ciclosComendo + 1) + ") <<<");
            sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return tempo; 
    }
    
    // O método exibirStats() foi removido
}