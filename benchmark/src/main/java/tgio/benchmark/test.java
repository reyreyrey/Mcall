package tgio.benchmark;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @作者 ：xrz
 * @日期：2022.11.08 18:27
 * @简介：
 */
public class test {
    public static void main(String... args){
        try {
            FileReader fr = new FileReader(new File("E:/log/log.txt"));
            BufferedReader br = new BufferedReader(fr);
            String line;
            StringBuffer sb = new StringBuffer();
            while((line = br.readLine()) != null) {
                sb.append(line);
            }

            String json = sb.toString();

            JSONArray jsonArray = new JSONArray(json);
            System.out.println(jsonArray.toString());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
}
