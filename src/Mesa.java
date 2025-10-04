import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {
    final static int NR_FILOSOFOS = 5;
    
    // Cada garfo agora é um Lock individual, permitindo concorrência mais fina.
    private final Lock[] garfos = new ReentrantLock[NR_FILOSOFOS];

    public Mesa() {
        for (int i = 0; i < NR_FILOSOFOS; i++) {
            garfos[i] = new ReentrantLock(true); // 'true' para ser justo (fair)
        }
    }

    public void pegarGarfos(int idFilosofo) {
        // Determina os garfos da esquerda e direita
        int garfoEsquerdo = idFilosofo;
        int garfoDireito = (idFilosofo + 1) % NR_FILOSOFOS;

        // A MÁGICA ACONTECE AQUI: Hierarquia de Recursos para evitar deadlock
        // Identifica qual garfo tem o menor índice para pegá-lo primeiro.
        Lock primeiroGarfo = garfos[Math.min(garfoEsquerdo, garfoDireito)];
        Lock segundoGarfo = garfos[Math.max(garfoEsquerdo, garfoDireito)];

        int idPrimeiroGarfo = Math.min(garfoEsquerdo, garfoDireito);
        int idSegundoGarfo = Math.max(garfoEsquerdo, garfoDireito);

        try {
            System.out.println("-> Filósofo " + idFilosofo + " tentou comer... Precisa dos garfos [" + garfoEsquerdo + " e " + garfoDireito + "]");

            // 1. Tenta pegar o primeiro garfo (o de menor índice)
            primeiroGarfo.lock();
            System.out.println("   Filósofo " + idFilosofo + " pegou o primeiro garfo: " + idPrimeiroGarfo);

            // 2. Tenta pegar o segundo garfo (o de maior índice)
            segundoGarfo.lock();
            System.out.println("   Filósofo " + idFilosofo + " pegou o segundo garfo: " + idSegundoGarfo + ". Conseguiu comer!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void devolverGarfos(int idFilosofo) {
        int garfoEsquerdo = idFilosofo;
        int garfoDireito = (idFilosofo + 1) % NR_FILOSOFOS;

        // A ordem de devolução não importa para evitar deadlock, mas é boa prática
        // devolver na ordem inversa em que pegou.
        System.out.println("<- Filósofo " + idFilosofo + " terminou de comer. Devolvendo os garfos [" + garfoEsquerdo + " e " + garfoDireito + "]");
        garfos[garfoDireito].unlock();
        garfos[garfoEsquerdo].unlock();
        
        System.out.println("-------------------------------------------------------------------------");
    }
}