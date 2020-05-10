import java.util.Random;
import java.util.Scanner;
public class bataille{

    public static int[][] gridComp = new int[10][10];
    public static int[][] gridPlay = new int[10][10];

    public static Random rand = new Random();
    public static String readString(){
        return System.console().readLine();
    }
    public static boolean isInt(String s){
        return s.matches("\\d+");
    }
    public static int readInt(){
        while (true){
            String s = readString();
            if (isInt(s)){
                return Integer.parseInt(s);
            }
        }
    }
    public static int randRange (int a, int b){
        return rand.nextInt(b-a)+a;
    }
    public static boolean posOk(int [][] grille, int l, int c, int d, int t){
        if (l>=grille[0].length) {
            return false;
        }
        if (c>=grille.length) {
            return false;
        }
        if (d==1) {
            if (c+t>grille.length) {
                return false;
            }
        }
        else {
            if (l+t>grille.length) {
                return false;
            }
        }
        for (int i=0;i<t;i++) {
            if (d==1) {
                if(grille[l][c+i]!=0) {
                    return false;
                }
            }
            else {
                if(grille[l+i][c]!=0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void initGridComp () {
        int i = 5;
        int c = 0;
        int l = 0;
        int d = 0;
        int[] t = {5,4,3,3,2};

        while (i!=0) {
            c = randRange(0,10);
            l = randRange(0,10);
            d = randRange(1,3);
            if (posOk(gridComp,l,c,d,t[i-1])) {
                for (int n=0;n<t[i-1];n++) {
                    if (d==1) {
                        gridComp[l][c+n] = i;
                    }
                    else {
                        gridComp[l+n][c] = i;
                    }
                }
                i=i-1;
            }
        }
    }



    public static void initGridPlay(){
        int i = 5;
        //Scanner in = new Scanner(System.in);
        int[] t2 = {5,4,3,3,2};
        String[] t3 = {"porte-avions","croiseur","contre-torpilleurs","sous-marin","torpilleur"};
        String[] t = {"A","B","C","D","E","F","G","H","I","J"};
        String Letter = "";
        int d;
        int c;
        int l;
        while (i != 0){
            c = -1;
            System.out.println("Donnez la lettre pour le "+t3[i-1] + " :" );
            Letter = readString();
            System.out.println("Donnez le nombre pour le "+t3[i-1] + " :");
            l = readInt();
            System.out.println("Voulez vous qu'il soit horizontal(1) ou vertical(2) ?");
            d = readInt();
            for (int j = 0;j<t.length; j++){
                if (t[j].equals(Letter)){
                    c = j;
                }
            }
            if (c == -1 || l > 10 || l < 1){
                System.out.println("Erreur : "+t3[i-1]+" ne rentre pas dans la grille.");
            }
            else {
                l = l - 1;
                if (posOk(gridPlay,l,c,d,t2[i-1])){
		                  for (int n = 0; n < t2[i-1]; n++){
                        if (d == 1){
                            gridPlay[l][c+n] = i;
                        }
                        else {
                            gridPlay[l+n][c] = i;
                        }
                    }
                    i = i-1;
                    System.out.println("i vaut : " + i);
                    print(gridPlay);
                }
                else {
                    System.out.println("Erreur : "+t3[i-1]+" ne rentre pas dans la grille.");
                }
            }
        }
    }

    public static boolean hasDrowned(int[][] grille, int n){

        if (n < 1 || n > 5)
            return true;

        for (int i = 0; i < grille.length; i++){
            for (int j = 0; j < grille[i].length; j++){
                if (grille[i][j] == n){
                    return false;
                }
            }
        }
        return true;
    }

    public static void oneMove(int[][] grille, int l, int c){
        int[] t = {5,4,3,3,2};
        String[] bat = {"� l'eau","porte-avions","croiseur","contre-torpilleurs","sous-marin","torpilleur","� l'eau"};
        if (grille[l][c] == 0 || grille[l][c] == 6){
            System.out.println("A l'eau");
            print(grille);
            System.out.println();
            System.out.println();
        }
        else {
            int n = grille[l][c];
            grille[l][c] = 6;
            if (hasDrowned(grille,n)){
                System.out.println("Vous avez coule un : "+bat[n]);
            } else {
                System.out.println("Vous avez touche un : " + bat[n]);
            }
            print(grille);
            System.out.println();
            System.out.println();
        }
    }

    public static int[] playComp(){
        int l = randRange(0,10);
        int c = randRange(0,10);
        int[] tab = {l,c};
        return tab;
    }

    public static boolean isOver(int[][] grille){
        for (int i = 0; i < grille.length; i++){
            for (int j = 0; j < grille[i].length; j++){
                if (grille[i][j] != 0 && grille[i][j] != 6){
                    return false;
                }
            }
        }
        return true;
    }

    public static void play(){
        initGridComp();
        initGridPlay();
        System.out.println("TEST");
        while (!isOver(gridPlay) || !isOver(gridComp)){
          System.out.println("Veuillez saisir des coordonnees valides : ");
          int x = readInt();
          int y = readInt();
          oneMove(gridComp,x-1,y-1);
          System.out.println("A l'ordinateur de jouer : ");
          int x1 = randRange(0,10);
          int y1 = randRange(0,10);
          oneMove(gridPlay,x1,y1);
        }
    }

    public static void print (int[][] t){
        System.out.println("   A B C D E F G H I J");
        for(int i = 0; i < t.length; i++){
            if (i < 9) {
                System.out.print(i + 1 + "  ");
            } else {
                System.out.print(i+1 + " ");
            }
            for (int j = 0; j < t[i].length; j++){
                System.out.print(t[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String args[]){
        play();
    }

}
