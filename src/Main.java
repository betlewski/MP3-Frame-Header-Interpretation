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

                    b = String.format("%2s", Integer.toBinaryString(B)).replace(' ', '0');
                    c = String.format("%2s", Integer.toBinaryString(C)).replace(' ', '0');
                    d = String.format("%1s", Integer.toBinaryString(D)).replace(' ', '0');
                    e = String.format("%4s", Integer.toBinaryString(E)).replace(' ', '0');
                    f = String.format("%2s", Integer.toBinaryString(F)).replace(' ', '0');
                    g = String.format("%1s", Integer.toBinaryString(G)).replace(' ', '0');
                    h = String.format("%1s", Integer.toBinaryString(H)).replace(' ', '0');
                    i = String.format("%2s", Integer.toBinaryString(I)).replace(' ', '0');
                    j = String.format("%2s", Integer.toBinaryString(J)).replace(' ', '0');
                    k = String.format("%1s", Integer.toBinaryString(K)).replace(' ', '0');
                    l = String.format("%1s", Integer.toBinaryString(L)).replace(' ', '0');
                    m = String.format("%2s", Integer.toBinaryString(M)).replace(' ', '0');

                    System.out.println("\nResults: ");

                    checkB(b);
                    checkC(c);
                    checkD(d);
                    checkE(e, b, c);
                    checkF(f, b);
                    System.out.println("[G: " + g + "] Padding bit");
                    System.out.println("[H: " + h + "] Private bit");
                    checkI(i);
                    System.out.println("[J: " + j + "] Mode extension");
                    checkK(k);
                    checkL(l);
                    System.out.println("[M: " + m + "] Emphasis");

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

    private static void checkB(String b) {

        System.out.print("[B: " + b + "] MPEG Audio version ID - ");

        switch (b) {
            case "00":
                System.out.println("MPEG Version 2.5");
                break;
            case "01":
                System.out.println("reserved");
                break;
            case "10":
                System.out.println("MPEG Version 2");
                break;
            case "11":
                System.out.println("MPEG Version 1");
                break;
        }
    }

    private static void checkC(String c) {

        System.out.print("[C: " + c + "] Layer description - ");

        switch (c) {
            case "00":
                System.out.println("reserved");
                break;
            case "01":
                System.out.println("Layer III");
                break;
            case "10":
                System.out.println("Layer II");
                break;
            case "11":
                System.out.println("Layer I");
                break;
        }
    }

    private static void checkD(String d) {

        System.out.print("[D: " + d + "] Protection bit - ");

        if (d.equals("0"))
            System.out.println("Protected by CRC");

        else if (d.equals("1"))
            System.out.println("Not protected");
    }

    private static void checkE(String e, String b, String c) {

        System.out.print("[E: " + e + "] Bitrate index - ");

        switch (e) {
            case "0000":
                System.out.println("free");
                break;

            case "0001":
                if(b.equals("11")){
                    System.out.println("32");
                }
                else if(b.equals("00") || b.equals("10")){

                    if(c.equals("11"))
                        System.out.println("32");

                    else if(c.equals("10") || c.equals("01"))
                        System.out.println("8");
                }
                break;

            case "0010":
                if(b.equals("11")){
                    switch (c) {
                        case "11":
                            System.out.println("64");
                            break;
                        case "10":
                            System.out.println("48");
                            break;
                        case "01":
                            System.out.println("40");
                            break;
                    }
                }
                else if(b.equals("00") || b.equals("10")){

                    if(c.equals("11"))
                        System.out.println("48");

                    else if(c.equals("10") || c.equals("01"))
                        System.out.println("16");
                }
                break;

            case "0011":
                if(b.equals("11")){
                    switch (c) {
                        case "11":
                            System.out.println("96");
                            break;
                        case "10":
                            System.out.println("56");
                            break;
                        case "01":
                            System.out.println("48");
                            break;
                    }
                }
                else if(b.equals("00") || b.equals("10")){

                    if(c.equals("11"))
                        System.out.println("56");

                    else if(c.equals("10") || c.equals("01"))
                        System.out.println("24");
                }
                break;

            case "0100":

                break;

            case "0101":

                break;

            case "0110":

                break;

            case "0111":

                break;

            case "1000":

                break;

            case "1001":

                break;

            case "1010":

                break;

            case "1011":

                break;

            case "1100":

                break;

            case "1101":

                break;

            case "1110":

                break;

            case "1111":
                System.out.println("bad");
                break;
        }
    }

    private static void checkF(String f, String b) {

        System.out.print("[F: " + f + "] Sampling rate frequency index (in Hz) - ");

        switch (f) {
            case "00":
                switch (b) {
                    case "11":
                        System.out.println("44100");
                        break;
                    case "10":
                        System.out.println("22050");
                        break;
                    case "00":
                        System.out.println("11025");
                        break; }
                break;

            case "01":
                switch (b) {
                    case "11":
                        System.out.println("48000");
                        break;
                    case "10":
                        System.out.println("24000");
                        break;
                    case "00":
                        System.out.println("12000");
                        break; }
                break;

            case "10":
                switch (b) {
                    case "11":
                        System.out.println("32000");
                        break;
                    case "10":
                        System.out.println("16000");
                        break;
                    case "00":
                        System.out.println("8000");
                        break; }
                break;

            case "11":
                System.out.println("reserved");
                break;
        }
    }

    private static void checkI(String i) {

        System.out.print("[I: " + i + "] Channel Mode - ");

        switch (i) {
            case "00":
                System.out.println("Stereo");
                break;
            case "01":
                System.out.println("Joint stereo (Stereo)");
                break;
            case "10":
                System.out.println("Dual channel (Stereo)");
                break;
            case "11":
                System.out.println("Single channel (Mono)");
                break;
        }
    }

    private static void checkK(String k) {

        System.out.print("[K: " + k + "] Copyright - ");

        if (k.equals("0"))
            System.out.println("Audio is not copyrighted");

        else if (k.equals("1"))
            System.out.println("Audio is copyrighted");
    }

    private static void checkL(String l) {

        System.out.print("[L: " + l + "] Original - ");

        if (l.equals("0"))
            System.out.println("Copy of original media");

        else if (l.equals("1"))
            System.out.println("Original media");
    }
}
