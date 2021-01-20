public class Programma {
    public static void main(String [] argomenti) {
        // controllo  che  l'utente  abbia  inserito
        // esattamente 4 stringhe da riga di comando
        if (argomenti.length != 4) {
            System.out.println("Devi inserire esattamente 4 stringhe da un carattere separate da spazi");
            System.exit(1);
        };
        char [] arrayA = new char[4];
        for(int i=0; i<4; i++) {
            // controllo se la stringa inserita
            // è composta da un solo  carattere
            if (argomenti[i].length() != 1) {
                System.out.println("Ogni stringa inserita deve essere composta da un solo carattere");
                System.exit(1);
            } else {
                arrayA[i] = argomenti[i].charAt(0);
            };
        };
        // invoco il metodo per costruire la matrice
        int [] [] matrice = Metodi.costruisciArray(arrayA);
        // invoco il metodo per trasformarla
        Metodi.trasforma(matrice);
        // la stampo su scermo
        System.out.println("Stampo la matrice");
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                System.out.print(""+matrice[i][j]+" ");
            };
            System.out.println("");
        };
        // conto il numero di 1 nella matrice
        // e  memorizzo   il risultato  nella
        // variabile                     <nz>
        int nz=0;
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                if (matrice[i][j] == 1)
                    nz++;
            };
        };
        // stampo a schermo il valore di nz
        System.out.println("nz vale : "+nz);
        // stampo     a    schermo il valore del
        // metodo funRic con come argomento <nz>
        System.out.println("funRic(nz) vale : "+Metodi.funRic(nz));
    };
    
};
