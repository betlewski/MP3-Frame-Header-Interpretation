import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    //found frame header:
    //11111111 11111011 10010010 01000000

    public static void main(String[] args){

        boolean first = false;
        boolean second = false;
        boolean end = false;

        int state = 1;

        int B = 0, C = 0, D = 0, E = 0, F = 0, G = 0, H = 0, I = 0, J = 0, K = 0, L = 0, M = 0;
        String b, c, d, e, f, g, h, i, j, k, l, m;

        File file = new File("sound.mp3");
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);

            System.out.println("\nExecuting...");
            System.out.println("Total file size to read: " + fis.available() + " bytes");

            int content;
            int pos = -1;

            while ((content = fis.read()) != -1) {
                pos++;

                if(!first && content == 255){
                    first = true;
                }

                else if(first && !second && ((content >> 5) & 7) == 7){
                    second = true;
                    System.out.println("Position of the first byte of frame header: " + (pos - 1) + " byte");
                }

                if(second){

                    switch(state){
                        case 1:
                            B = (content >> 3) & 3;
                            C = (content >> 1) & 3;
                            D = content & 1;

                            state = 2;
                            break;

                        case 2:
                            E = (content >> 4) & 15;
                            F = (content >> 2) & 3;
                            G = (content >> 1) & 1;
                            H = content & 1;

                            state = 3;
                            break;

                        case 3:
                            I = (content >> 6) & 3;
                            J = (content >> 4) & 3;
                            K = (content >> 3) & 1;
                            L = (content >> 2) & 1;
                            M = content & 3;

                            end = true;
                            break;
                    }
                }

                if(end){
                    b = toString(B, 2);
                    c = toString(C, 2);
                    d = toString(D, 1);
                    e = toString(E, 4);
                    f = toString(F, 2);
                    g = toString(G, 1);
                    h = toString(H, 1);
                    i = toString(I, 2);
                    j = toString(J, 2);
                    k = toString(K, 1);
                    l = toString(L, 1);
                    m = toString(M, 2);

                    System.out.println("\nResults: ");

                    Check check = new Check();
                    check.checkAll(b, c, d, e, f, g, h, i, j, k, l, m);

                    break;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String toString(int value, int number){
        return String.format("%" + number + "s", Integer.toBinaryString(value)).replace(' ', '0');
    }
}
