import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    //found head:
    //11111111 11111011 10010010 01000000

    public static void main(String[] args){

        boolean first = false;
        boolean second = false;
        boolean end = false;

        int state = 1;
        int B = 0, C = 0, D = 0, E = 0, F = 0, G = 0, H = 0, I = 0, J = 0, K = 0, L = 0, M = 0;

        File file = new File("sound.mp3");
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);

            System.out.println("Total file size to read (in bytes): " + fis.available());

            int content;
            int pos = -1;

            while ((content = fis.read()) != -1) {
                pos++;

                if(!first && content == 255){
                    first = true;
                }

                else if(first && !second && ((content >> 5) & 7) == 7){
                    second = true;
                    System.out.println("Position of head first byte: " + (pos - 1));
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
                    System.out.println("B: " + String.format("%2s", Integer.toBinaryString(B)).replace(' ', '0'));
                    System.out.println("C: " + String.format("%2s", Integer.toBinaryString(C)).replace(' ', '0'));
                    System.out.println("D: " + String.format("%1s", Integer.toBinaryString(D)).replace(' ', '0'));
                    System.out.println("E: " + String.format("%4s", Integer.toBinaryString(E)).replace(' ', '0'));
                    System.out.println("F: " + String.format("%2s", Integer.toBinaryString(F)).replace(' ', '0'));
                    System.out.println("G: " + String.format("%1s", Integer.toBinaryString(G)).replace(' ', '0'));
                    System.out.println("H: " + String.format("%1s", Integer.toBinaryString(H)).replace(' ', '0'));
                    System.out.println("I: " + String.format("%2s", Integer.toBinaryString(I)).replace(' ', '0'));
                    System.out.println("J: " + String.format("%2s", Integer.toBinaryString(J)).replace(' ', '0'));
                    System.out.println("K: " + String.format("%1s", Integer.toBinaryString(K)).replace(' ', '0'));
                    System.out.println("L: " + String.format("%1s", Integer.toBinaryString(L)).replace(' ', '0'));
                    System.out.println("M: " + String.format("%2s", Integer.toBinaryString(M)).replace(' ', '0'));

                    break;
                }

                /*pos++;

                tab[0] = content & 255;
                tab[1] = (content >> 8) & 255;
                tab[2] = (content >> 16) & 255;
                tab[3] = (content >> 24) & 255;

                System.out.println(content);
                System.out.println(tab[3] + " " + tab[2] + " " + tab[1] + " " + tab[0]);

                if(tab[0] == 255){
                    int second = (tab[1] >> 5) & 7;
                    System.out.println(tab[1]);
                    System.out.println(second);

                    if(second == 7){
                        System.out.println("yes");
                        break;
                    }
                }*/
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
