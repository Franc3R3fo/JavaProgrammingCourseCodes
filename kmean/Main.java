/*
    SCRITTO DA  : Francesco R.
    DATA        : 14/01/2021
    F. CONSEGNA : k-means.pdf
*/
import java.util.Scanner;
public class Main {
    
    /*
    NOTES:
        la funzione AggiornaCentri() ogni qual volta che incontra un centro a cui non è stato assegnato nessun punto, lo mantiene uguale a com'era prima della chiamata della funzione
        la precisione è calcolata come la differenza ASSOLUTA dei valori della funzione obiettivo tra due iterazioni ==> |O[i] - O[i-1]|
        per ragioni di debug è chiamata la funzione StampaCentri() all'interno di main, anche se non specificatamente richiesto dalla consegna
    */
    
    // variabili-I : PRINCIPALI
    public static double [] [] dati;                // matrice dati dei punti di grandezza MxN
    public static int       [] cluster;             // contiene il numero del cluster di appartenenza
    public static double [] [] centri;              // matrice centri del cluster, dimensione KxN
    public static double       obiettivoOld;        // valore funzione obiettivo della iterazione precedente
    public static double       obiettivo;           // valore funzione obiettivo
    public static double       precisione;          // differenza del valore di obiettivo nel tempo
    public static int          M;                   // numero dei punti
    public static int          N;                   // numero delle caratteristiche per punto
    public static int          K=5;                 // numero dei cluster
    public static double       alfa=0.1;            // soglia di ottimizzazione
    public static double       iter=1000;           // numero di iterazioni massime
    public static double       numero_iterazioni=0; // numero delle iterazioni eseguite
    
    // variabili-II : IOSTREAM
    public static Scanner keyboard = new Scanner(System.in);
    
    // main
    public static void main(String [] args) {
        RiceviMeN();                // Chiede all'utente di inserire valori per N e M
        InizializzaDati();          // Inizializza la matrice dati
        StampaDati();               // Stampa a schermo la matrice dati
        obiettivo=0;                // Inizializza obiettivo a 0
        obiettivoOld=0;             // Inizializza obiettivoOld a 0
        InizializzaCluster();       // Inizializza la matrice centri
        StampaCentri();             // Stampa a schermo la matrice centri
        do {
            CalcolaCluster();       // Calcola per ogni punto il centro più vicino
            AggiornaCentri();       // Aggiorna il valore dei centri
            CalcolaObiettivo();     // Calcola il valore della funzione obiettivo e aggiorna precisione, obiettivoOld e numero_iterazioni
            StampaCentri();         
        } while( (precisione>alfa) && (numero_iterazioni<iter) );
        StampaCluster();            // Stampa a schermo i centri assegnati ad ogni punto (indice rispetto alla matrice centri)
        StampaNumeroIterazioni();   // Stampa a schermo il numero di iterazioni eseguite
        StampaObiettivi();          // Stampa a schermo il valore della funzione obiettivo nelle ultime due iterazioni
        StampaPrecisione();         // Stampa a schermo il valore della precisione raggiunta
        StampaMotivoTerminazione(); // Stampa a schermo il motivo della terminazione del programma
    };
    
    // metodi-I : FRONTEND
    public static void InizializzaDati() {
        dati = new double[M][N];
        for(int i=0; i<M; i++) {
            for(int j=0; j<N; j++) {
                dati[i][j] = Math.random();
            };
        };
    };
    
    public static void InizializzaCluster() {
        centri = new double[K][N];
        int [] numeriGiaAssegnati = new int[K]; int numeroAssegnato=0;
        for(int i=0; i<K; i++) {
            do {
                numeroAssegnato = (int)( (double)(Math.random()*M)%M );
            } while( (i!=0) && (!(trovaNumeroNellArray(numeriGiaAssegnati,numeroAssegnato,i))) );
            copiaArray(dati[numeroAssegnato], centri[i]);
        };
    };
    
    public static void AggiornaCentri() {
        double [] [] sommaValoriCentri = new double[K][N];
        int    []    numeroDiPuntiPerCentro = new int[K];
        for(int i=0; i<M; i++) {
            int k = cluster[i];
            for(int j=0; j<N; j++) {
                sommaValoriCentri[k][j] += dati[i][j];
            };
            numeroDiPuntiPerCentro[k]+=1;
        };
        for(int i=0; i<K; i++) {
            if (numeroDiPuntiPerCentro[i]!=0) {
                for(int j=0; j<N; j++) {
                    centri[i][j] = (sommaValoriCentri[i][j]) / (numeroDiPuntiPerCentro[i]);
                };
            };
        };
    };
    
    public static void CalcolaCluster() {
        cluster = new int[M];
        for(int i=0; i<M; i++) {
            cluster[i] = trovaClusterPiuVicino(dati[i]);
        };
    };
    
    public static void CalcolaObiettivo() {
        double sommaDistanzeCentri = 0;
        for(int i=0; i<M; i++) {
            int k = cluster[i];
            sommaDistanzeCentri += distanza(centri[k],dati[i]);
        };
        
        obiettivoOld = obiettivo;
        obiettivo = sommaDistanzeCentri;
        precisione = Math.abs(obiettivoOld-obiettivo);
        // già che c'è, aumenta anche la variabile numero_iterazioni
        numero_iterazioni+=1;
    };
    
    // metodi-II : BACKEND
    private static void copiaArray(double [] input, double [] output) {
        for(int i=0; i<input.length; i++) {
            output[i] = input[i];
        };
    };
    
    private static boolean trovaNumeroNellArray(int [] input, int numeroDaCercare, int numeroDiElementi) {
        for(int i=0; i<numeroDiElementi; i++) {
            if (input[i] == numeroDaCercare)
                return true;
        };
        return false;
    };
    
    private static double distanza(double [] puntoA, double [] puntoB) {
        double summa=0;
        for(int i=0; i<puntoA.length; i++) {
            summa += Math.pow((puntoA[i]-puntoB[i]),2);
        };
        return Math.sqrt(summa);
    };
    
    private static int trovaClusterPiuVicino(double [] punto) {
        int numeroDelMinimo=0;
        double distanzaMinima=distanza(punto,centri[0]);
        for(int i=1; i<K; i++) {
            double temp_dist = distanza(punto,centri[i]);
            if (temp_dist<distanzaMinima) {
                numeroDelMinimo = i;
                distanzaMinima = temp_dist;
            };
        };
        return numeroDelMinimo;
    };
    
    // metodi-III : IOSTREAM
    private static void StampaCentri() {
        System.out.println("Ecco i centri:");
        for(int i=0; i<K; i++) {
            System.out.print(""+i+" : {");
            for(int j=0; j<N; j++) {
                System.out.print(" "+centri[i][j]);
            };
            System.out.println(" }");
        };
    };
    
    private static void StampaDati() {
        System.out.println("Ecco i punti:");
        for(int i=0; i<M; i++) {
            System.out.print(""+i+" : {");
            for(int j=0; j<N; j++) {
                System.out.print(" "+dati[i][j]);
            };
            System.out.println(" }");
        };
    };
    
    private static void StampaCluster() {
        System.out.println("Ecco i centri di riferimento per ogni punto:");
        for(int i=0; i<M; i++) {
            System.out.println(""+i+" : "+cluster[i]);
        };
    };
    
    private static void StampaNumeroIterazioni() {
        System.out.println("Numero iterazioni : "+numero_iterazioni);
    };
    
    private static void StampaObiettivi() {
        System.out.println("Valore funzione obiettivo nella penultima iterazione : "+obiettivoOld);
        System.out.println("Valore funzione obiettivo nell'ultima     iterazione : "+obiettivo);
    };
    
    private static void StampaPrecisione() {
        System.out.println("Precisione corrente : "+precisione);
    };
    
    private static void StampaMotivoTerminazione() {
        if (precisione<=alfa)
            System.out.println("Precisione Prevista Raggiunta");
        if (numero_iterazioni>=iter)
            System.out.println("Numero Massimo Previsto di Iterazioni Raggiunto");
    };
    
    private static void RiceviMeN() {
        // M
        System.out.println("hint : M deve essere maggiore o uguale a "+K);
        do {
            System.out.println("Inserisci M : ");
            M = keyboard.nextInt();
        } while(M<=K);
        
        // N
        System.out.println("hint : N deve essere maggiore o uguale a 1");
        do {
            System.out.println("Inserisci N : ");
            N = keyboard.nextInt();
        } while(N<1);
    };
};
