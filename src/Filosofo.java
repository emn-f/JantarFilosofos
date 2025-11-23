/**
 * Classe que representa um Filósofo.
 * Estende Thread, pois cada filósofo roda independentemente.
 */
public class Filosofo extends Thread {
    // Tempos máximo e mínimo em milissegundos para as ações de pensar e comer.
    final static int TEMPO_MAXIMO = 2500;
    final static int TEMPO_MINIMO = 500;
    
    private Mesa mesa;
    private int idFilosofo;

    /**
     * Construtor do Filósofo.
     * 
     * @param nome       Nome da thread.
     * @param mesa       Referência para a mesa compartilhada.
     * @param idFilosofo ID único do filósofo.
     */
    public Filosofo(String nome, Mesa mesa, int idFilosofo) {
        super(nome);
        this.mesa = mesa;
        this.idFilosofo = idFilosofo;
    }

    /**
     * O método run contém o ciclo de vida do filósofo.
     * Ele fica num loop infinito alternando entre pensar e comer.
     */
    @Override
    public void run() {
        while (true) {
            pensar();
            // Tenta pegar os garfos para comer.
            // Se não conseguir, ficará bloqueado dentro deste método até conseguir.
            mesa.pegarGarfos(idFilosofo);
            comer();
            // Devolve os garfos para que outros possam usar.
            mesa.devolverGarfos(idFilosofo);
        }
    }

    /**
     * Simula o ato de pensar.
     * O filósofo fica parado por um tempo aleatório.
     */
    private void pensar() {
        try {
            // Calcula um tempo aleatório entre TEMPO_MINIMO e TEMPO_MAXIMO.
            int tempo = TEMPO_MINIMO + (int) (Math.random() * TEMPO_MAXIMO);
            System.out.println("<<< Filósofo " + idFilosofo + " está PENSANDO >>>");
            // Pausa a thread pelo tempo calculado.
            sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Simula o ato de comer.
     * O filósofo fica parado por um tempo aleatório (enquanto segura os garfos).
     */
    private void comer() {
        try {
            // Calcula um tempo aleatório entre TEMPO_MINIMO e TEMPO_MAXIMO.
            int tempo = TEMPO_MINIMO + (int) (Math.random() * TEMPO_MAXIMO);
            System.out.println(">>> Filósofo " + idFilosofo + " está COMENDO <<<");
            // Pausa a thread pelo tempo calculado.
            sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}