import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class WaveConversion {

    public static void main(String[] args) {

        try {
            RandomAccessFile readFile = new RandomAccessFile("mono.wav", "r");
            RandomAccessFile writeFile = new RandomAccessFile("stereo.wav", "rw");

            System.out.println("Executing...");

            int pos, value, pom = 0;
            int sample1 = 0, sample2;
            final int STEREO = 2;

            int flag1 = 22;
            int flag2 = 28;
            int flag3 = 32;
            int flag4 = 40;
            int flag5 = 44;
            boolean copy = false;

            //changing mono values to stereo:
            for(int i = 0; i < flag5; i++) {
                pos = i;

                readFile.seek(pos);
                writeFile.seek(pos);

                value = readFile.read();

                if(pos == flag1){
                    value = STEREO;
                }

                if(pos == flag2){
                    pom = value;
                }

                if(pos == flag2 + 1) {
                    value <<= 8;
                    pom += value;
                }

                if(pos == flag2 + 2) {
                    value <<= 16;
                    pom += value ;
                }

                if(pos == flag2 + 3) {
                    value <<= 24;
                    pom += value ;
                    pom *= STEREO;

                    byte[] bytes = ByteBuffer.allocate(4).putInt(pom).array();

                    for(int j = 0; j < 4; j++){
                        int getByte = bytes[j] & 0xff;

                        writeFile.seek(pos - j);
                        writeFile.write(getByte);
                    }
                    continue;
                }

                if(pos == flag3){
                    pom = value;
                }

                if(pos == flag3 + 1) {
                    value <<= 8;
                    pom += value ;
                    pom *= STEREO;

                    byte[] bytes = ByteBuffer.allocate(4).putInt(pom).array();

                    for(int j = 2; j < 4; j++){
                        int getByte = bytes[j] & 0xff;

                        writeFile.seek(pos - (4 - j));
                        writeFile.write(getByte);
                    }
                    continue;
                }

                if(pos == flag4){
                    pom = value;
                }

                if(pos == flag4 + 1) {
                    value <<= 8;
                    pom += value ;
                }

                if(pos == flag4 + 2) {
                    value <<= 16;
                    pom += value ;
                }

                if(pos == flag4 + 3) {
                    value <<= 24;
                    pom += value ;
                    pom *= STEREO;

                    byte[] bytes = ByteBuffer.allocate(4).putInt(pom).array();

                    for(int j = 0; j < 4; j++){
                        int getByte = bytes[j] & 0xff;

                        writeFile.seek(pos - j);
                        writeFile.write(getByte);
                    }
                    continue;
                }

                writeFile.write(value);
            }

            pos = flag5;

            //copying one channel samples to another:
            for (int i = flag5; i < readFile.length(); i++) {

                readFile.seek(i);
                value = readFile.read();

                if (!copy) {
                    sample1 = value;
                    copy = true;
                }
                else {
                    sample2 = value;
                    copy = false;

                    int count = 0;

                    while(count < 2) {
                        writeFile.seek(pos++);
                        writeFile.write(sample1);

                        writeFile.seek(pos++);
                        writeFile.write(sample2);

                        count++;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        /*try {
            RandomAccessFile file1 = new RandomAccessFile("mono.wav", "r");
            RandomAccessFile file2 = new RandomAccessFile("stereo.wav", "r");
            System.out.println("Executing...");

            int pos, value;

            for(int i = 0; i < 100; i++) {
                pos = i;

                file1.seek(pos);
                file2.seek(pos);

                value = file1.read();
                System.out.print(pos + ": " + value);

                value = file2.read();
                System.out.println(" - " + value);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
    }
}
