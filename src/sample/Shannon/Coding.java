package sample.Shannon;

import java.util.ArrayList;

public class Coding {
    private ArrayList<String> code;
    private ArrayList<String> charArray;
    private String sourceText;


    public Coding(ArrayList<String> code, ArrayList<String> charArray, String text){
        this.code = code;
        this.sourceText = text;
        this.charArray = charArray;
    }

    public String textToBinText(){
        String[] textArray = sourceText.split("");

        for (int i = 0; i < textArray.length; i++) {
            for (int j = 0; j < charArray.size(); j++) {
                if((textArray[i].equals(" ") && charArray.get(j).equals("(space)"))
                        || (textArray[i].equals("\n") && charArray.get(j).equals("(\\n)"))
                        || (textArray[i].equals("\r") && charArray.get(j).equals("(\\r)"))){
                    textArray[i] = code.get(j);
                    break;
                }
                if(textArray[i].equals(charArray.get(j))){
                    textArray[i] = code.get(j);
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for(String s : textArray) {
            builder.append(s);
        }

        return builder.toString();
    }

}
