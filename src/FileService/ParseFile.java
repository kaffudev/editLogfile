package FileService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseFile {
    public static void main(String[] args) {

        String myPath = "C:\\asd\\2\\logFile";

        List<String> arguments;
        arguments = getDataFromFile(myPath);

        String[] params = getParamsTable(arguments, 3);

        String[] sglQuery = getSeparatedQuery(arguments, 3, params);

    }

    private static String[] getParamsTable(List<String> arguments, int index) {
        String[] array = arguments.get(index).split(" ");
        int begin = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i].contains("[params=")) {
                begin = i;
            }

        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = begin; i < array.length; i++) {
            if (array[i].contains("(")) {
                continue;
            }
            stringBuilder.append(array[i]);
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        String[] finalArray = String.valueOf(stringBuilder).split(",");

        return finalArray;
    }

    private static String[] getSeparatedQuery(List<String> arguments, int index, String[] values) {
        String[] array = arguments.get(index).split(" ");

        int beginIndex = 0;
        int endIndex = 0;

        for (int i = 0; i < array.length; i++) {
            //System.out.println(i + " " + array[i]);
            if ((array[i].equals("SELECT")) || (array[i].equals("DELETE")) || (array[i].equals("UPDATE"))) {
                beginIndex = i;
            }
            if (array[i].contains("params")) {
                endIndex = i;
            }
        }
        String[] array2 = Arrays.copyOfRange(array, beginIndex, endIndex);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array2.length; i++) {
            stringBuilder.append(array2[i] + " ");

        }

        //.replaceAll("\\?", "222");
        String repleceSting = String.valueOf(stringBuilder);
        for (int i = 0; i < values.length; i++) {
            repleceSting.replaceFirst("\\?", values[i]);
            System.out.println(i + "  " + repleceSting);
        }

        System.out.println(repleceSting);

        /*StringBuilder stringBuilder = new StringBuilder();

        for (int j = beginIndex; j < endIndex; j++) {
            stringBuilder.append((array[j]) + " ");
        }
        System.out.println("sdadad");
        System.out.println(stringBuilder);

        //String[] query = String.valueOf(stringBuilder).split()*/
        return array2;
    }

    private static List<String> getDataFromFile(String myPath) {
        File file = new File(myPath);
        List<String> lines = null;

        if (!file.exists()) {
            System.out.println("Nie znaleziono pliku");
        }

        try {
            lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
