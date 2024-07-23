import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        Tabuleiro[][] tabuleiro = new Tabuleiro[3][3];
        char simboloAtual = 'X';
        Boolean game = true;
        String vitoria = "";
        Scanner scan = new Scanner(System.in);

        iniciarJogo(tabuleiro);

        System.out.println("-- ----    JOGO DA VELHA  ----- ");

        while (game) {
            desenhaJogo(tabuleiro);
            vitoria = verificaVitoria(tabuleiro);

            if (!vitoria.equals("")) {
                limparTela();
                System.out.printf("--- PARABÉNS JOGADOR %s VENCEU !! ---", vitoria);
                game = false;

            } else if (verificarEmpate(tabuleiro)) {
                System.out.println(" --- O JOGO EMPATOU --- ");
                game = false;

            } else {
                try {
                    int[] jogada = jogar(scan, simboloAtual);
                    if (verificarJogada(tabuleiro, jogada, simboloAtual)) {
                        simboloAtual = (simboloAtual == 'X') ? 'O' : 'X';
                    } else {
                        System.out.println("Posição já está OCUPADA, tente novamente! ");
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }

        System.out.println("Fim do jogo ");
    }

    public static boolean verificarEmpate(Tabuleiro[][] tabuleiro) {
        for (int l = 0; l < tabuleiro.length; l++) {
            for (int c = 0; c < tabuleiro[l].length; c++) {
                if (tabuleiro[l][c].getSimbolo() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean verificarJogada(Tabuleiro[][] tabuleiro, int[] jogada, char simboloAtual) {
        int linha = jogada[0];
        int coluna = jogada[1];
        if (linha < 0 || linha >= tabuleiro.length || coluna < 0 || coluna >= tabuleiro[0].length) {
            return false;
        }
        if (tabuleiro[linha][coluna].getSimbolo() == ' ') {
            tabuleiro[linha][coluna].setSimbolo(simboloAtual);
            return true;
        } else {
            return false;
        }
    }

    public static void desenhaJogo(Tabuleiro[][] tabuleiro) {

        System.out.print("   COLUNAS\n");
        for (int i = 0; i < tabuleiro.length; i++) {
            System.out.printf("  %d  ", i);
        }
        System.out.println();
        for (int i = 0; i < tabuleiro.length; i++) {

            System.out.printf("%d ", i);
            for (int j = 0; j < tabuleiro[i].length; j++) {

                System.out.printf(" %c |", tabuleiro[i][j].getSimbolo());
            }
            System.out.println();

            if (i < tabuleiro.length - 1) {
                System.out.print("  ");
                for (int k = 0; k < tabuleiro[i].length; k++) {
                    System.out.print("---");
                    if (k < tabuleiro[i].length) {
                        System.out.print("|");
                    }
                }
                System.out.println();

            }
        }

    }

    @SuppressWarnings("deprecation")
    public static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception ex) {
            // Lidar com exceções, se necessário
        }
    }

    public static int[] jogar(Scanner scan, char sa) {
        int[] jogada = new int[2];
        System.out.printf("%nJOGADOR: %c%n", sa);
        System.out.print("Informe a linha: ");
        jogada[0] = scan.nextInt();
        System.out.print("Informe a coluna: ");
        jogada[1] = scan.nextInt();
        return jogada;
    }

    public static void iniciarJogo(Tabuleiro[][] tabuleiro) {
        for (int l = 0; l < 3; l++) {
            for (int c = 0; c < 3; c++) {
                tabuleiro[l][c] = new Tabuleiro();
            }
        }
    }

    public static String verificaVitoria(Tabuleiro[][] tabuleiro) {
        // Verificar linhas
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0].getSimbolo() != ' ' &&
                    tabuleiro[i][0].getSimbolo() == tabuleiro[i][1].getSimbolo() &&
                    tabuleiro[i][0].getSimbolo() == tabuleiro[i][2].getSimbolo()) {
                return String.valueOf(tabuleiro[i][0].getSimbolo());
            }
        }

        // Verificar colunas
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[0][i].getSimbolo() != ' ' &&
                    tabuleiro[0][i].getSimbolo() == tabuleiro[1][i].getSimbolo() &&
                    tabuleiro[0][i].getSimbolo() == tabuleiro[2][i].getSimbolo()) {
                return String.valueOf(tabuleiro[0][i].getSimbolo());
            }
        }

        // Verificar diagonais
        if (tabuleiro[0][0].getSimbolo() != ' ' &&
                tabuleiro[0][0].getSimbolo() == tabuleiro[1][1].getSimbolo() &&
                tabuleiro[0][0].getSimbolo() == tabuleiro[2][2].getSimbolo()) {
            return String.valueOf(tabuleiro[0][0].getSimbolo());
        }

        if (tabuleiro[0][2].getSimbolo() != ' ' &&
                tabuleiro[0][2].getSimbolo() == tabuleiro[1][1].getSimbolo() &&
                tabuleiro[0][2].getSimbolo() == tabuleiro[2][0].getSimbolo()) {
            return String.valueOf(tabuleiro[0][2].getSimbolo());
        }

        // Nenhuma vitória encontrada
        return "";
    }

}

class Tabuleiro {
    private char simbolo;

    public Tabuleiro() {
        this.simbolo = ' ';
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }
}

