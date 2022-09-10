package sample.Shannon;

import java.util.ArrayList;

public class Decoding {
    private ArrayList<String> code;
    private ArrayList<String> charArray;
    private String binText;

    public Decoding(ArrayList<String> code, ArrayList<String> charArray, String binText){
        this.code = code;
        this.binText = binText;
        this.charArray = charArray;
    }


    public String binTextToText(){
        String[] binTextArray = binText.split("");
        StringBuilder bufText = new StringBuilder();
        StringBuilder text = new StringBuilder();
        for (String aBinTextArray : binTextArray) {
            bufText.append(aBinTextArray);
            for (int j = 0; j < charArray.size(); j++) {
                if (bufText.toString().equals(code.get(j))) {
                    switch (charArray.get(j)) {
                        case "(space)":
                            text.append(" ");
                            break;
                        case "(\\n)":
                            text.append("\n");
                            break;
                        case "(\\r)":
                            text.append("\r");
                            break;
                        default:
                            text.append(charArray.get(j));
                            break;
                    }
                    bufText.setLength(0);
                }
            }
        }
        return text.toString();
    }
}
