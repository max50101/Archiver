package sample.TextFiles;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FileReader {
    private ArrayList<String> characterArrayList = new ArrayList<>();
    private ArrayList<Double> probabilityArrayList = new ArrayList<>();

    public FileReader(String path) throws IOException {
        culcProbability(readFile(path));
    }

    public void writeFile(String fileName, String str) throws IOException {
        FileWriter file = new FileWriter(fileName, false);
        file.write(str);
        file.close();
    }

    public String readFile(String fileName) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileName, "r");
        String res = "";
        int b = file.read();
        while (b != -1) {
            res += (char) b;
            b = file.read();
        }
        file.close();
        return res;
    }

    private void culcProbability(String text) {
        int howChar = text.length(), count, i = 0;
        char[] simbol = text.toCharArray();
        Set<Character> setChar = new HashSet<>();
        for (char temp : simbol) {
            setChar.add(temp);
        }
        char[] listChar = new char[setChar.size()];
        int[] index = new int[listChar.length];
        for (Character temp : setChar) {
            listChar[i] = temp;
            count = 0;
            for (int j = 0; j < simbol.length; j++) {
                if (listChar[i] == simbol[j]) {
                    count++;
                }
            }
            index[i] = count;
            i++;
        }
        for (int j = 0; j < index.length; j++) {
            for (int k = 0; k < index.length - 1; k++) {
                if (index[k] < index[k + 1]) {
                    int temp = index[k];
                    char tmp = listChar[k];
                    index[k] = index[k + 1];
                    listChar[k] = listChar[k + 1];
                    index[k + 1] = temp;
                    listChar[k + 1] = tmp;
                }
            }
        }
        for (int k = 0; k < listChar.length; k++) {

            if (listChar[k] == '\n') {
                characterArrayList.add(String.valueOf("(\\n)"));
            } else if (listChar[k] == ' ') {
                characterArrayList.add(String.valueOf("(space)"));
            } else if (listChar[k] == '\r') {
                characterArrayList.add(String.valueOf("(\\r)"));
            } else {
                characterArrayList.add(String.valueOf(listChar[k]));
            }

            probabilityArrayList.add((double) index[k] / howChar);
        }
    }

    public ArrayList<String> getCharacterArrayList() {
        return characterArrayList;
    }

    public ArrayList<Double> getProbabilityArrayList() {
        return probabilityArrayList;
    }


    public static String readFile(File file){
        StringBuilder result=new StringBuilder("");
        try(BufferedReader reader=new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while((line=reader.readLine())!=null){
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }   
        return result.toString();
    }



     public static String addFileSize(String s,File file){
        StringBuilder sb=new StringBuilder(s);
        try {
            long bytes= Files.size(file.toPath());
            sb.append("\nFile size: ").append(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
