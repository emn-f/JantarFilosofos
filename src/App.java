/**
 * Classe principal da aplicação.
 * Responsável por iniciar a simulação do Jantar dos Filósofos.
 */
public class App {

    /**
     * Método principal que é executado ao iniciar o programa.
     * 
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        // Cria a mesa onde os filósofos irão jantar.
        // A mesa gerencia os garfos (recursos compartilhados).
        Mesa mesa = new Mesa();

        // Cria e inicia 5 threads de filósofos.
        // Cada filósofo recebe um nome, a referência da mesa e um ID único.
        for (int filosofo = 0; filosofo < 5; filosofo++) {
            new Filosofo("Filosofo_" + filosofo, mesa, filosofo).start();
        }
    }
}