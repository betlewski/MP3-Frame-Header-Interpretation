public class Check {

    public void checkAll(String b, String c, String d, String e, String f, String g,
                    String h, String i, String j, String k, String l, String m){
        checkB(b);
        checkC(c);
        checkD(d);
        checkE(e, b, c);
        checkF(f, b);
        checkG(g);
        checkH(h);
        checkI(i);
        checkJ(j);
        checkK(k);
        checkL(l);
        checkM(m);
    }

    private void checkB(String b) {

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

    private void checkC(String c) {

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

    private void checkD(String d) {

        System.out.print("[D: " + d + "] Protection bit - ");

        if (d.equals("0"))
            System.out.println("Protected by CRC");

        else if (d.equals("1"))
            System.out.println("Not protected");
    }

    private void checkE(String e, String b, String c) {

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

    private void checkF(String f, String b) {

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

    private void checkG(String g) {
        System.out.println("[G: " + g + "] Padding bit");
    }

    private void checkH(String h) {
        System.out.println("[H: " + h + "] Private bit");
    }

    private void checkI(String i) {

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

    private void checkJ(String j) {
        System.out.println("[J: " + j + "] Mode extension");
    }

    private void checkK(String k) {

        System.out.print("[K: " + k + "] Copyright - ");

        if (k.equals("0"))
            System.out.println("Audio is not copyrighted");

        else if (k.equals("1"))
            System.out.println("Audio is copyrighted");
    }

    private void checkL(String l) {

        System.out.print("[L: " + l + "] Original - ");

        if (l.equals("0"))
            System.out.println("Copy of original media");

        else if (l.equals("1"))
            System.out.println("Original media");
    }

    private void checkM(String m) {
        System.out.println("[M: " + m + "] Emphasis");
    }
}
