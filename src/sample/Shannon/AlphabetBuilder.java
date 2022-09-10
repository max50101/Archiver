package sample.Shannon;
import java.util.ArrayList;

public class AlphabetBuilder {


    private ArrayList<Double> probabilityArray; //массив вероятностей, упорядоченных по убыванию
    private ArrayList<Double> qArray = new ArrayList<Double>(); //массив для величин Qi
    private ArrayList<Integer> codeLengthArray = new ArrayList<Integer>(); // массив длин кодовых слов
    private ArrayList<String> code = new ArrayList<>();

    public AlphabetBuilder(ArrayList<Double> probabilityArray) {
        this.probabilityArray = probabilityArray;
        getTable();
    }

    private void getTable() {
        qArray.add(0, 0.0);
        for (double a : probabilityArray) {
            codeLengthArray.add((int) (-logB(a) + 1));
        }

        probabilityArray.add(0, 0.0);
        for (int i = 1; i < probabilityArray.size() - 1; i++) {
            qArray.add(i, qArray.get(i - 1) + probabilityArray.get(i));
        }
        probabilityArray.remove(0);

        for (int i = 0; i < probabilityArray.size(); i++) {
            code.add(toBinary(qArray.get(i), codeLengthArray.get(i)));
        }
    }

    private double logB(double x) {
        return (int) Math.ceil(Math.log(x) / Math.log(2));
    }

    private String toBinary(double d, int precision) {
        long wholePart = (long) d;
        return fractionalToBinary(d - wholePart, precision);
    }

    private String fractionalToBinary(double num, int precision) {
        StringBuilder binary = new StringBuilder();
        while (num >= 0 && binary.length() < precision) {
            double r = num * 2;
            if (r >= 1) {
                binary.append(1);
                num = r - 1;
            } else {
                binary.append(0);
                num = r;
            }
        }
        return binary.toString();
    }

    public ArrayList<Double> getProbabilityArray() {
        return probabilityArray;
    }

    public ArrayList<Double> getQArray() {
        return qArray;
    }

    public ArrayList<Integer> getCodeLengthArray() {
        return codeLengthArray;
    }

    public ArrayList<String> getCode() {
        return code;
    }

    public double getEntropy(ArrayList<Double> p){
        double entropy = 0;

        for (double pi : p) {
            entropy += pi * logB(pi);
        }

        return -entropy;
    }
}