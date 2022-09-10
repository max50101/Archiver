package sample;

import sample.ImageCompressor.LZWImage;
import sample.ImageCompressor.LZWImageDecompress;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.LZW.LZW;
import sample.Shannon.AlphabetBuilder;
import sample.Shannon.Coding;
import sample.Shannon.Decoding;
import sample.TextFiles.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Archiever {
    final static FileChooser fileOpener=new FileChooser();
    private List<Integer> compressed=new ArrayList<Integer>();
    private static FileReader fileReader=null;
    private  static AlphabetBuilder alphabetBuilder=null;
    private static List<Integer> l=null;
    private static Map<String,String> sixtenthSystem= Stream.of(new String[][]{
            {"0000","0"},
            {"0001","1"},
            {"0010","2"},
            {"0011","3"},
            {"0100","4"},
            {"0101","5"},
            {"0110","6"},
            {"0111","7"},
            {"1000","8"},
            {"1001","9"},
            {"1010","A"},
            {"1011","B"},
            {"1100","C"},
            {"1101","D"},
            {"1110","E"},
            {"1111","F"},
    }).collect(Collectors.collectingAndThen(
            Collectors.toMap(data->data[0],data->data[1]),
            Collections::<String,String> unmodifiableMap));

    private static void initialize(File f) throws IOException {
         fileReader=new FileReader(f.getPath());
         alphabetBuilder=new AlphabetBuilder(fileReader.getProbabilityArrayList());
        fileReader.writeFile("alphabet.txt", "Char\t\t\t|Probability\t|P(i)\t\t|Q(i)\t|L(i)\t|Code\n");
        fileReader.writeFile("alphabet.txt", "--------------------------------------------\n");
        for (int i = 0; i < fileReader.getProbabilityArrayList().size(); i++) {
            fileReader.writeFile("alphabet.txt", fileReader.getCharacterArrayList().get(i) + "\t\t\t\t|"
                    + fileReader.getProbabilityArrayList().get(i) + "\t\t\t|"
                    + alphabetBuilder.getProbabilityArray().get(i) + "\t\t|" + alphabetBuilder.getQArray().get(i) + "\t|"
                    + alphabetBuilder.getCodeLengthArray().get(i) + "\t\t|" + alphabetBuilder.getCode().get(i) + "\n");
        }
    }

    public static void compress(File f) throws IOException {
        initialize(f);
        Coding coding = new Coding(alphabetBuilder.getCode(), fileReader.getCharacterArrayList(), fileReader.readFile(f.getPath()));
        l= LZW.compress(coding.textToBinText());
        fileReader.writeFile(f.getPath(),(l.toString()));
    }

    public static void decompress(File f) throws IOException{
        String result=LZW.decompress(l);
        fileReader.writeFile(f.getPath(),result);
        Decoding decoding = new Decoding(alphabetBuilder.getCode(), fileReader.getCharacterArrayList(), fileReader.readFile(f.getPath()));
        fileReader.writeFile(f.getPath(),decoding.binTextToText());
    }
    public static void compressLZVS(File f) throws IOException{
        initialize(f);
        Coding coding = new Coding(alphabetBuilder.getCode(), fileReader.getCharacterArrayList(), fileReader.readFile(f.getPath()));
        l=LZW.compress(FileReader.readFile(f));
        fileReader.writeFile(f.getPath(),l.toString());
        fileReader.writeFile(f.getPath(),coding.textToBinText());
    }
    private static String compressBinString(String compressedBin){
        StringBuilder result=new StringBuilder();
        int cheker=0;
        String f="";
        for(int i=1;i<compressedBin.length()-1;i++){
            if (compressedBin.charAt(i) == '\n') {
                cheker=0;
                f="";
                result.append("\n");
                continue;
            }
            f+=compressedBin.charAt(i);
            cheker++;
            if(cheker==4){
                cheker=0;
                result.append(sixtenthSystem.get(f));
                f="";
            }
        }
        return result.toString();
    }
    public static void compressImage(File file) throws IOException {
        LZWImage lzwImage=new LZWImage(file);
//        File f =fileOpener.showOpenDialog(new Stage());
//        try {
//            initialize(f);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Coding coding = new Coding(alphabetBuilder.getCode(), fileReader.getCharacterArrayList(), fileReader.readFile(f.getPath()));
//        fileReader.writeFile(f.getPath(),coding.textToBinText());
    }
    public static void decompresImage(File file) throws  IOException{
//        Decoding decoding = new Decoding(alphabetBuilder.getCode(), fileReader.getCharacterArrayList(), fileReader.readFile(f.getPath()));
//        fileReader.writeFile(f.getPath(),decoding.binTextToText());
        File f =fileOpener.showOpenDialog(new Stage());
        LZWImageDecompress lzw=new LZWImageDecompress(f);

    }
}
