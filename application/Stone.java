package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class Stone {
	public static HashMap<String, Integer> nowStones = new HashMap<String, Integer>(); 
	public static void loadStones(GridPane grid2) {
		try {
			List<String> allLines = Files.readAllLines(Paths.get("珠子.txt"));
			for (String line : allLines) {
				String key = "";
				int value = 0;
				Pattern pattern = Pattern.compile("(.*)=");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find())
				{
					key = matcher.group(1);
				}
				Pattern pattern2 = Pattern.compile("=(.*)");
				Matcher matcher2 = pattern2.matcher(line);
				if (matcher2.find())
				{
					value = Integer.valueOf(matcher2.group(1));
				}
				nowStones.put(key, value);
			}
		} catch (IOException e) {
//			e.printStackTrace();
			Iterator<Node> itNode = grid2.getChildren().iterator();
			while (itNode.hasNext()) {
				ComboBox<String> comboBox = (ComboBox<String>) itNode.next();
				comboBox.getSelectionModel().select(comboBox.getItems().size() - 1);
			}
		}
	}
	public static void saveStones() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("珠子.txt", "UTF-8");
			Iterator it = nowStones.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        writer.println(pair.getKey() + "=" + pair.getValue());
		    }
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void printStones() {
		Iterator it = nowStones.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.print("現有珠子 : "+pair.getKey() + "=" + pair.getValue()+"\n");
	    }
	}

}
