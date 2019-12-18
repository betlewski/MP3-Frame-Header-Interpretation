public class Tag {

    //data in ID3v1 tag:
    private boolean isPresentV1 = false;
    private String title = "";
    private String artist = "";
    private String album = "";
    private String year = "";
    private String comment = "";
    private String musicType = "";

    //data in ID3v2 tag:
    private boolean isPresentV2 = false;
    private String version = "";
    private int size = 0;

    public void id3V1(int[] tab) {

        if ((char) tab[0] == 'T' && (char) tab[1] == 'A' && (char) tab[2] == 'G') {
            isPresentV1 = true;

            for (int i = 3; i < 33; i++)
                title += (char) tab[i];


            for (int i = 33; i < 63; i++)
                artist += (char) tab[i];


            for (int i = 63; i < 93; i++)
                album += (char) tab[i];


            for (int i = 93; i < 97; i++)
                year += (char) tab[i];


            for (int i = 97; i < 127; i++)
                comment += (char) tab[i];

            musicType += (char) tab[127];
        }
    }

    public void id3V2(int[] tab){

        if ((char) tab[0] == 'I' && (char) tab[1] == 'D' && (char) tab[2] == '3') {

            isPresentV2 = true;
            version = "ID3v2." + tab[3] + "." + tab[4];

            String bin;
            String all = "";

            for(int i = 6; i < 10; i++) {

                tab[i] &= 0b01111111;
                bin = String.format("%7s", Integer.toBinaryString(tab[i]))
                        .replace(' ', '0');

                all += bin;
            }

            size = Integer.parseInt(all, 2);
        }
    }

    public void checkV1(){

        if (isPresentV1) {
            System.out.println(
                    "\nID3v1 tag is present:" +
                    "\nTitle: " + title +
                    "\nArtist: " + artist +
                    "\nAlbum: " + album +
                    "\nYear: " + year +
                    "\nComment: " + comment +
                    "\nMusic type: " + musicType);
        }
        else {
            System.out.println("\nID3v1 tag is not present.");
        }
    }

    public void checkV2(){

        if (isPresentV2) {
            System.out.println(
                    "\nID3v2 tag is present:" +
                    "\nVersion: " + version +
                    "\nSize: " + size);
        }
        else {
            System.out.println("ID3v2 tag is not present.");
        }
    }
}
