import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class WaveConversion {

    public static void main(String[] args) {

        try {
            RandomAccessFile readFile = new RandomAccessFile("mono.wav", "r");
            RandomAccessFile writeFile = new RandomAccessFile("stereo.wav", "rw");

            System.out.println("Processing...");

            int pos, value, pom = 0;
            int sampleNumber = 0;
            final int STEREO = 2;

            int flag1 = 22;
            int flag2 = 28;
            int flag3 = 32;
            int flag4 = 34;
            int flag5 = 40;
            int flag6 = 44;

            //changed mono values to stereo:
            for (int i = 0; i < flag6; i++) {
                pos = i;

                readFile.seek(pos);
                writeFile.seek(pos);

                value = readFile.read();

                //changed NumChannels from 1 to 2:
                if (pos == flag1) {
                    if (value == 1) {
                        value = STEREO;
                    } else {
                        System.out.println("Given wave file is already stereo. You do not have to change it!");
                        System.exit(0);
                    }
                }

                //doubled ByteRate value:
                else if (pos == flag2) {
                    pom = value;
                } else if (pos == flag2 + 1) {
                    value <<= 8;
                    pom += value;
                } else if (pos == flag2 + 2) {
                    value <<= 16;
                    pom += value;
                } else if (pos == flag2 + 3) {
                    value <<= 24;
                    pom += value;
                    pom *= STEREO;

                    byte[] bytes = ByteBuffer.allocate(4).putInt(pom).array();

                    for (int j = 0; j < 4; j++) {
                        int getByte = bytes[j] & 0xff;

                        writeFile.seek(pos - j);
                        writeFile.write(getByte);
                    }
                    continue;
                }

                //saved BitsPerSample value:
                else if (pos == flag4) {
                    pom = value;
                } else if (pos == flag4 + 1) {
                    value <<= 8;
                    pom += value;
                    sampleNumber = pom;
                }

                //doubled BlockAlign value:
                else if (pos == flag3) {
                    pom = value;
                } else if (pos == flag3 + 1) {
                    value <<= 8;
                    pom += value;
                    pom *= STEREO;

                    byte[] bytes = ByteBuffer.allocate(4).putInt(pom).array();

                    for (int j = 2; j < 4; j++) {
                        int getByte = bytes[j] & 0xff;

                        writeFile.seek(pos - (4 - j));
                        writeFile.write(getByte);
                    }
                    continue;
                }

                //doubled Subchunk2Size value:
                else if (pos == flag5) {
                    pom = value;
                } else if (pos == flag5 + 1) {
                    value <<= 8;
                    pom += value;
                } else if (pos == flag5 + 2) {
                    value <<= 16;
                    pom += value;
                } else if (pos == flag5 + 3) {
                    value <<= 24;
                    pom += value;
                    pom *= STEREO;

                    byte[] bytes = ByteBuffer.allocate(4).putInt(pom).array();

                    for (int j = 0; j < 4; j++) {
                        int getByte = bytes[j] & 0xff;

                        writeFile.seek(pos - j);
                        writeFile.write(getByte);
                    }

                    //changed ChunkSize value:
                    int chunkSize = 36 + pom;
                    bytes = ByteBuffer.allocate(4).putInt(chunkSize).array();

                    for (int j = 0; j < 4; j++) {
                        int getByte = bytes[j] & 0xff;

                        writeFile.seek(7 - j);
                        writeFile.write(getByte);
                    }

                    continue;
                }

                writeFile.write(value);
            }

            pos = flag6;
            sampleNumber /= 8;

            int tabPos = 0, sampleCount = sampleNumber;
            int[] samples = new int[sampleNumber];
            boolean copy = false;

            //copied one channel samples to another:
            for (int i = flag6; i < readFile.length(); i++) {

                readFile.seek(i);
                value = readFile.read();

                samples[tabPos++] = value;
                sampleCount--;

                if (sampleCount == 0) {
                    copy = true;

                    sampleCount = sampleNumber;
                    tabPos = 0;
                }

                if (copy) {
                    copy = false;
                    int count = 2;

                    while (count > 0) {

                        for (int j = 0; j < sampleNumber; j++) {
                            writeFile.seek(pos++);
                            writeFile.write(samples[j]);
                        }
                        count--;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("There were some problems while processing: " + ex +
                    "\nUnfortunately given wave file has not been changed from mono to stereo!");

            System.exit(0);
        }

        System.out.println("Given wave file has been successfully changed from mono to stereo!");
    }
}
