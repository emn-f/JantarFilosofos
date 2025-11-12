import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Mesa {
    final static int NR_FILOSOFOS = 5;

    private final Lock[] garfos = new ReentrantLock[NR_FILOSOFOS];
    private final Random aleatorio = new Random();

    public Mesa() {
        for (int i = 0; i < NR_FILOSOFOS; i++) {
            garfos[i] = new ReentrantLock(true);
        }
    }

    public boolean pegarGarfos(int idFilosofo, long tempoMaximoEsperaMs) {

        int garfoEsquerdoIdx = idFilosofo;
        int garfoDireitoIdx = (idFilosofo + 1) % NR_FILOSOFOS;

        Lock primeiroGarfo, segundoGarfo;
        String nomePrimeiro, nomeSegundo;

        if (aleatorio.nextBoolean()) {
            primeiroGarfo = garfos[garfoEsquerdoIdx];
            nomePrimeiro = "esquerdo";
            segundoGarfo = garfos[garfoDireitoIdx];
            nomeSegundo = "direito";
        } else {
            primeiroGarfo = garfos[garfoDireitoIdx];
            nomePrimeiro = "direito";
            segundoGarfo = garfos[garfoEsquerdoIdx];
            nomeSegundo = "esquerdo";
        }

        System.out.println("-> Filósofo " + idFilosofo + " tentou comer. Tentando primeiro o garfo: " + nomePrimeiro);

        try {
            primeiroGarfo.lock();
            System.out.println("   Filósofo " + idFilosofo + " PEGOU o garfo " + nomePrimeiro + ".");

            if (segundoGarfo.tryLock(tempoMaximoEsperaMs, TimeUnit.MILLISECONDS)) {
                System.out.println("   Filósofo " + idFilosofo + " PEGOU o garfo " + nomeSegundo + ". Conseguiu comer!");
                return true;
            } else {
                System.out.println("   Filósofo " + idFilosofo + " TIMEOUT. Não pegou o garfo " + nomeSegundo + ". Soltando o " + nomePrimeiro + ".");
                primeiroGarfo.unlock(); 
                return false;
            }
        } catch (InterruptedException e) {
            System.out.println("   Filósofo " + idFilosofo + " foi interrompido enquanto esperava.");
            if (((ReentrantLock)primeiroGarfo).isHeldByCurrentThread()) {
                primeiroGarfo.unlock();
            }
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void devolverGarfos(int idFilosofo, int tempoComendo, int ciclosComendo) {
        int garfoEsquerdo = idFilosofo;
        int garfoDireito = (idFilosofo + 1) % NR_FILOSOFOS;
        
        System.out.println("<- Filósofo " + idFilosofo + " terminou de comer (Ciclo: " + ciclosComendo + ", " + tempoComendo + "ms). Devolvendo os garfos.");

        garfos[garfoDireito].unlock();
        garfos[garfoEsquerdo].unlock();

        System.out.println("-------------------------------------------------------------------------");
    }
}