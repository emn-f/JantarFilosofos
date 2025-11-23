import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {
    final static int NR_FILOSOFOS = 5;

    private final Lock[] garfos = new ReentrantLock[NR_FILOSOFOS];

    public Mesa() {
        for (int i = 0; i < NR_FILOSOFOS; i++) {
            garfos[i] = new ReentrantLock(true);
        }
    }

    public void pegarGarfos(int idFilosofo) {

        int garfoEsquerdo = idFilosofo;
        int garfoDireito = (idFilosofo + 1) % NR_FILOSOFOS;

        Lock primeiroGarfo = garfos[Math.min(garfoEsquerdo, garfoDireito)];
        Lock segundoGarfo = garfos[Math.max(garfoEsquerdo, garfoDireito)];

        int idPrimeiroGarfo = Math.min(garfoEsquerdo, garfoDireito);
        int idSegundoGarfo = Math.max(garfoEsquerdo, garfoDireito);

        try {
            System.out.println("-> Filósofo " + idFilosofo + " tentou comer... Precisa dos garfos [" + garfoEsquerdo + " e " + garfoDireito + "]");

            primeiroGarfo.lock();
            System.out.println("   Filósofo " + idFilosofo + " pegou o primeiro garfo: " + idPrimeiroGarfo);

            segundoGarfo.lock();
            System.out.println("   Filósofo " + idFilosofo + " pegou o segundo garfo: " + idSegundoGarfo + ". Conseguiu comer!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void devolverGarfos(int idFilosofo) {
        int garfoEsquerdo = idFilosofo;
        int garfoDireito = (idFilosofo + 1) % NR_FILOSOFOS;
        System.out.println("<- Filósofo " + idFilosofo + " terminou de comer. Devolvendo os garfos [" + garfoEsquerdo
                + " e " + garfoDireito + "]");
        garfos[garfoDireito].unlock();
        garfos[garfoEsquerdo].unlock();

        System.out.println("-------------------------------------------------------------------------");
    }
}