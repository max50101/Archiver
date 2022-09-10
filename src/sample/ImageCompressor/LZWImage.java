package sample.ImageCompressor;



import java.io.*;
import java.util.*;

public class LZWImage {

    public static HashMap<String, Integer> dictionary = new HashMap<>();
    public static int dictSize = 256;
    public static String P = "",filename="",BP="";
    public static byte inputByte;
    public static byte[] buffer = new byte[3];
    public static boolean isLeft = true;

    public LZWImage(File f){
        filename=f.getPath();
        try {
            compress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void compress() throws IOException {

        int i,byteToInt;
        char C;

        // Character dictionary
        for(i=0;i<256;i++) {
            dictionary.put(Character.toString((char)i),i);
        }

        // Read input file and output file
        RandomAccessFile inputFile = new RandomAccessFile(filename,"r");
        String[] getFileNameWOExtn = filename.split("\\.");
        RandomAccessFile outputFile = new RandomAccessFile(getFileNameWOExtn[0].concat(".txt"),"rw");

        try {

            // Read first byte to initialize P
            inputByte = inputFile.readByte();
            byteToInt = new Byte(inputByte).intValue();

            if(byteToInt < 0) byteToInt += 256;
            C = (char) byteToInt;
            P = ""+C;

            while(true) {
                inputByte = inputFile.readByte();
                byteToInt = new Byte(inputByte).intValue();

                if(byteToInt < 0) byteToInt += 256;
                C = (char) byteToInt;

                // if P+C is present in dictionary
                if(dictionary.containsKey(P+C)) {
                    P = P+C;
                }

                /*
                    if P+C is not in dictionary, we obtain the longest string in the dictionary
                    so far which is stored in P. The value of this string is converted in binary.
                    This binary number is then padded to make it 12 bits long (for standardization
                    and avoing overflow or underflow caused using 8 bits). This is then converted
                    into bytes and stored.

                    We write in the file every 2 characters.
                */
                else {
                    BP = convertTo12Bit(dictionary.get(P));
                    if(isLeft) {
                        buffer[0] = (byte) Integer.parseInt(BP.substring(0,8),2);
                        buffer[1] = (byte) Integer.parseInt(BP.substring(8,12)+"0000",2);
                    }
                    else {
                        buffer[1] += (byte) Integer.parseInt(BP.substring(0,4),2);
                        buffer[2] = (byte) Integer.parseInt(BP.substring(4,12),2);
                        for(i=0;i<buffer.length;i++) {
                            outputFile.writeByte(buffer[i]);
                            buffer[i]=0;
                        }
                    }
                    isLeft = !isLeft;
                    if(dictSize < 4096) dictionary.put(P+C,dictSize++);

                    P=""+C;
                }
            }

        }

        catch(IOException ie) {
            BP = convertTo12Bit(dictionary.get(P));
            if(isLeft) {
                buffer[0] = (byte) Integer.parseInt(BP.substring(0,8),2);
                buffer[1] = (byte) Integer.parseInt(BP.substring(8,12)+"0000",2);
                outputFile.writeByte(buffer[0]);
                outputFile.writeByte(buffer[1]);
            }
            else {
                buffer[1] += (byte) Integer.parseInt(BP.substring(0,4),2);
                buffer[2] = (byte) Integer.parseInt(BP.substring(4,12),2);
                for(i=0;i<buffer.length;i++) {
                    outputFile.writeByte(buffer[i]);
                    buffer[i]=0;
                }
            }
            inputFile.close();
            outputFile.close();
        }

    }

    public static String convertTo12Bit(int i) {
        String to12Bit = Integer.toBinaryString(i);
        while (to12Bit.length() < 12) to12Bit = "0" + to12Bit;
        return to12Bit;
    }

}