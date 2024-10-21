import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }

    public static int cinCheck(int a,int b,Scanner in){
        int input;
        do {input=in.nextInt();
        }while (input<a||input>b);
        return input;
    }
    
    public static int cinMinCheck(int a,Scanner in){
        int input;
        do {input=in.nextInt();
        }while (input<a);
        return input;
    }
}
