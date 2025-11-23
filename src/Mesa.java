import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Classe que representa a Mesa de jantar.
 * Ela gerencia os garfos (recursos) que os filósofos disputam.
 */
public class Mesa {
    // Define o número de filósofos na mesa.
    final static int NR_FILOSOFOS = 5;

    // Array de Locks representando os garfos.
    // Usamos ReentrantLock para controlar o acesso exclusivo a cada garfo.
    private final Lock[] garfos = new ReentrantLock[NR_FILOSOFOS];

    /**
     * Construtor da Mesa.
     * Inicializa os garfos (Locks).
     */
    public Mesa() {
        for (int i = 0; i < NR_FILOSOFOS; i++) {
            // Cria um Lock para cada garfo.
            // O parâmetro 'true' indica que a política de justiça (fairness) está ativada,
            // favorecendo a thread que está esperando há mais tempo.
            garfos[i] = new ReentrantLock(true);
        }
    }

    /**
     * Método utilizado pelo filósofo para tentar pegar os garfos e comer.
     * Implementa uma estratégia para evitar Deadlock.
     * 
     * @param idFilosofo O ID do filósofo que quer comer.
     */
    public void pegarGarfos(int idFilosofo) {

        // Identifica os garfos adjacentes ao filósofo.
        int garfoEsquerdo = idFilosofo;
        int garfoDireito = (idFilosofo + 1) % NR_FILOSOFOS;

        // ESTRATÉGIA ANTI-DEADLOCK:
        // Sempre pegar primeiro o garfo com o MENOR índice.
        // Isso impõe uma ordem global na aquisição de recursos, prevenindo ciclos de
        // espera.
        Lock primeiroGarfo = garfos[Math.min(garfoEsquerdo, garfoDireito)];
        Lock segundoGarfo = garfos[Math.max(garfoEsquerdo, garfoDireito)];

        int idPrimeiroGarfo = Math.min(garfoEsquerdo, garfoDireito);
        int idSegundoGarfo = Math.max(garfoEsquerdo, garfoDireito);

        try {
            System.out.println("-> Filósofo " + idFilosofo + " tentou comer... Precisa dos garfos [" + garfoEsquerdo + " e " + garfoDireito + "]");

            // Tenta bloquear (pegar) o primeiro garfo.
            primeiroGarfo.lock();
            System.out.println("   Filósofo " + idFilosofo + " pegou o primeiro garfo: " + idPrimeiroGarfo);

            // Tenta bloquear (pegar) o segundo garfo.
            segundoGarfo.lock();
            System.out.println("   Filósofo " + idFilosofo + " pegou o segundo garfo: " + idSegundoGarfo + ". Conseguiu comer!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método utilizado pelo filósofo para devolver os garfos após comer.
     * 
     * @param idFilosofo O ID do filósofo que terminou de comer.
     */
    public void devolverGarfos(int idFilosofo) {
        int garfoEsquerdo = idFilosofo;
        int garfoDireito = (idFilosofo + 1) % NR_FILOSOFOS;

        System.out.println("<- Filósofo " + idFilosofo + " terminou de comer. Devolvendo os garfos [" + garfoEsquerdo
                + " e " + garfoDireito + "]");

        // Libera (devolve) os garfos. A ordem de liberação não é crítica para deadlock,
        // mas é boa prática liberar na ordem inversa ou simplesmente liberar ambos.
        garfos[garfoDireito].unlock();
        garfos[garfoEsquerdo].unlock();

        System.out.println("-------------------------------------------------------------------------");
    }
}