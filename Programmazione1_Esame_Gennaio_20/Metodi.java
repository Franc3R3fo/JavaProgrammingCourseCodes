public class Metodi {
    public static int [] [] costruisciArray(char [] par) {
        // la    matrice    è    inizializzata
        // automaticamente da java con degli 0
        int [] [] Mtr = new int[4][4];
        // ciclo con variabili <i> e <j> in cui assegno ad
        // ogni  cella  della matrice il valore desiderato
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                if (par[i] == par[j]) {
                    Mtr[i][j] = 1;
                } else {
                    Mtr[i][j] = 0;
                };
            };
        };
        // ritorno     la      matrice
        // bidimensionale al chiamante
        return Mtr;
    };
    
    public static void trasforma(int [] [] par) {
        // definisco solo <i> come variabile per l'iterazione, siccome
        // tutte  le  occorrenze  cercate sono nella forma par[i][j=i]
        for(int i=0; i<4; i++) {
            // tutte  le  celle della matrice posizionate
            // lungo la diagonale principale della stessa
            // vengono       settate         a          0
            par[i][i] = 0;
        };
    };
    
    public static int funRic(int n) {
        // definisco fuori    dal    blocco condizionale
        // una variabile    che conterrà    il risultato
        // del  metodo restituito alla fine al chiamante
        int res=0;
        //                     
        //     / 1          n=0
        // f = | 3+f(n-1)   n>0
        //     \ 0          n<0
        //                     
        if (n==0) {
            res = 1;
        } else if (n>0) {
            // chiamata ricorsiva del
            // metodo        funRic()
            res = 3 + funRic(n-1);
        } else {
            res = 0;
        };
        // restituisco la  variabile
        // che contiene il risultato
        return res;
    };
};