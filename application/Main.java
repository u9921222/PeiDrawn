package application;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

	public static SellectedSkill sellectedSkill = new SellectedSkill();
	public static SellectedSkill sellectedSet = new SellectedSkill();
	public static SellectedSkill weaponHole = new SellectedSkill();
	public static ArrayList<String> filterArray = new ArrayList<String>();
	public static String answer;
	public static int answerLimit = 99;
	public static int answerCount;
	public static boolean stopByValueNotEnough6 = false;
	public static boolean stopByValueNotEnough5 = false;
	public static boolean stopByValueNotEnough4 = false;
	public static boolean stopByValueNotEnough3 = false;
	public static boolean stopByValueNotEnough2 = false;

	@Override
	public void start(Stage s) {
		s.setTitle("簡易配裝網");
		Group root = new Group();
		Scene scene = new Scene(root, -1, -1, Color.WHITE);
		TabPane tp = new TabPane();
		BorderPane bp = new BorderPane();

////搜尋頁
		Tab tab = new Tab();
		tab.setText("搜尋");
		GridPane gridTop = new GridPane();
		gridTop.setVgap(4);
		gridTop.setHgap(10);
		gridTop.setPadding(new Insets(5, 5, 5, 5));
		addWeaponComboBox(gridTop, 0, 0);
		Button btTest = new Button("test");
		gridTop.add(btTest, 1, 0);
		btTest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("");
				for (Skill skill : sellectedSkill) {
					System.out.println("選了 " + skill.toString());
				}
				ArrayList<Equipment> equipments = Equipment.filtSkill("身", Equipment.Body, Equipment.BodyHolesCount,
						sellectedSkill, sellectedSet);
				Collections.sort(equipments, Equipment.EquValueComparator);
				for (int i = 0; i < equipments.size(); i++) {
					System.out.println(equipments.get(i).toString());
				}
//				for (Equipment equipment: equipments) {
//					System.out.println(equipment.toString());
//				}
			}
		});
		TextField filter = new TextField();
		filter.setPromptText("不要的裝備(開頭)");
		Button btnFilter = new Button();
		btnFilter.setText("篩選");
		Text filterWord = new Text();
		btnFilter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				filterArray.add(filter.getText());
				filter.clear();
				String ans = "";
				for (String filt : filterArray) {
					ans = ans + filt + " ";
				}
				filterWord.setText("篩選的 : " + ans);
			}
		});
		Button btnClear = new Button();
		btnClear.setText("清除");
		btnClear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				filterArray.clear();
				filterWord.setText("");
			}
		});
		gridTop.add(filter, 0, 1);
		gridTop.add(btnFilter, 1, 1);
		gridTop.add(btnClear, 2, 1);
		gridTop.add(filterWord, 3, 1);

		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));

		int xSetCount = 0;
		int ySetCount = 0;
//		try {
//			List<String> allLines;
//			allLines = Files.readAllLines(Paths.get("database\\MHW_SKILL.csv"));
//			for (String line : allLines) {
//				String[] data = line.split(",");
//				String name = data[0];
////				System.out.println(name);
//				int count = Integer.valueOf(data[2]);
//				if (xSetCount != 5) {
//					addSkillComboBox(grid, name, count, xSetCount, ySetCount);
//				} else {
//					xSetCount = 0;
//					ySetCount++;
//					addSkillComboBox(grid, name, count, xSetCount, ySetCount);
//				}
//				xSetCount++;
//
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		grid.add(new Text("任務   相關技能"), 0, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "耐力急速回復", 3, 0, ySetCount);
		addSkillComboBox(grid, "跑者", 3, 1, ySetCount);
		addSkillComboBox(grid, "體術", 5, 2, ySetCount);
		addSkillComboBox(grid, "吹笛名人", 1, 3, ySetCount);
		ySetCount++;
		grid.add(new Text("道具   相關技能"), 0, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "快吃", 3, 0, ySetCount);
		addSkillComboBox(grid, "體力回復量UP", 3, 1, ySetCount);
		addSkillComboBox(grid, "最愛菇類", 3, 2, ySetCount);
		addSkillComboBox(grid, "滿足感", 1, 3, ySetCount);
		addSkillComboBox(grid, "廣域化", 5, 4, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "炸彈客", 3, 0, ySetCount);
		addSkillComboBox(grid, "整備", 3, 1, ySetCount);
		ySetCount++;
		grid.add(new Text("戰鬥(生存)   相關技能"), 0, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "迴避性能", 5, 0, ySetCount);
		addSkillComboBox(grid, "迴避距離UP", 3, 1, ySetCount);
		addSkillComboBox(grid, "防禦性能", 5, 2, ySetCount);
		addSkillComboBox(grid, "防禦強化", 1, 3, ySetCount);
		addSkillComboBox(grid, "精靈加護", 3, 4, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "收刀術", 3, 0, ySetCount);
		addSkillComboBox(grid, "減輕膽怯", 3, 1, ySetCount);
		addSkillComboBox(grid, "回復速度", 3, 2, ySetCount);
		ySetCount++;
		grid.add(new Text("耐性   相關技能"), 0, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "耳塞", 5, 0, ySetCount);
		addSkillComboBox(grid, "風壓耐性", 5, 1, ySetCount);
		addSkillComboBox(grid, "耐震", 3, 2, ySetCount);
		addSkillComboBox(grid, "裂傷耐性", 3, 3, ySetCount);
		addSkillComboBox(grid, "瘴氣耐性", 3, 4, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "昏厥耐性", 3, 0, ySetCount);
		addSkillComboBox(grid, "麻痺耐性", 3, 1, ySetCount);
		addSkillComboBox(grid, "毒耐性", 3, 2, ySetCount);
		addSkillComboBox(grid, "睡眠耐性", 3, 3, ySetCount);
		addSkillComboBox(grid, "適應水場", 3, 4, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "泥耐性", 1, 0, ySetCount);
		addSkillComboBox(grid, "適應瘴氣環境", 1, 1, ySetCount);
		addSkillComboBox(grid, "防禦力DOWN耐性", 3, 2, ySetCount);
		addSkillComboBox(grid, "熱傷害無效", 1, 3, ySetCount);
		addSkillComboBox(grid, "爆破異常狀態的耐性", 3, 4, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "屬性異常狀態的耐性", 3, 0, ySetCount);
		ySetCount++;
		grid.add(new Text("屬性變化   相關技能"), 0, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "攻擊", 7, 0, ySetCount);
		addSkillComboBox(grid, "防禦", 7, 1, ySetCount);
		addSkillComboBox(grid, "火耐性", 3, 2, ySetCount);
		addSkillComboBox(grid, "水耐性", 3, 3, ySetCount);
		addSkillComboBox(grid, "雷耐性", 3, 4, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "冰耐性", 3, 0, ySetCount);
		addSkillComboBox(grid, "龍耐性", 3, 1, ySetCount);
		addSkillComboBox(grid, "體力增強", 3, 2, ySetCount);
		ySetCount++;
		grid.add(new Text("戰鬥(屬性/異常狀態)   相關技能"), 0, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "火屬性攻擊強化", 5, 0, ySetCount);
		addSkillComboBox(grid, "水屬性攻擊強化", 5, 1, ySetCount);
		addSkillComboBox(grid, "冰屬性攻擊強化", 5, 2, ySetCount);
		addSkillComboBox(grid, "龍屬性攻擊強化", 5, 3, ySetCount);
		addSkillComboBox(grid, "雷屬性攻擊強化", 5, 4, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "毒屬性強化", 3, 0, ySetCount);
		addSkillComboBox(grid, "爆破屬性強化", 3, 1, ySetCount);
		addSkillComboBox(grid, "睡眠屬性強化", 3, 2, ySetCount);
		addSkillComboBox(grid, "麻痺屬性強化", 3, 3, ySetCount);
		addSkillComboBox(grid, "龍封力強化", 5, 4, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "KO術", 3, 0, ySetCount);
		addSkillComboBox(grid, "破壞王", 3, 1, ySetCount);
		addSkillComboBox(grid, "奪取耐力", 3, 2, ySetCount);
		ySetCount++;
		grid.add(new Text("戰鬥(攻擊)   相關技能"), 0, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "看破", 7, 0, ySetCount);
		addSkillComboBox(grid, "弱點特效", 3, 1, ySetCount);
		addSkillComboBox(grid, "精神抖擻", 3, 2, ySetCount);
		addSkillComboBox(grid, "拔刀術【技】", 3, 3, ySetCount);
		addSkillComboBox(grid, "超會心", 3, 4, ySetCount);
		addSkillComboBox(grid, "騎乘名人", 1, 5, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "飛燕", 1, 0, ySetCount);
		addSkillComboBox(grid, "無屬性強化", 1, 1, ySetCount);
		addSkillComboBox(grid, "砥石使用高速化", 3, 2, ySetCount);
		addSkillComboBox(grid, "剛刃研磨", 1, 3, ySetCount);
		addSkillComboBox(grid, "解放弓的蓄力階段", 1, 4, ySetCount);
		addSkillComboBox(grid, "毒瓶追加", 1, 5, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "麻痺瓶追加", 1, 0, ySetCount);
		addSkillComboBox(grid, "睡眠瓶追加", 1, 1, ySetCount);
		addSkillComboBox(grid, "爆破瓶追加", 3, 2, ySetCount);
		addSkillComboBox(grid, "火場怪力", 5, 3, ySetCount);
		addSkillComboBox(grid, "怨恨", 5, 4, ySetCount);
		addSkillComboBox(grid, "不屈", 1, 5, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "無傷", 3, 0, ySetCount);
		addSkillComboBox(grid, "力量解放", 5, 1, ySetCount);
		addSkillComboBox(grid, "挑戰者", 5, 2, ySetCount);
		addSkillComboBox(grid, "匠", 5, 3, ySetCount);
		addSkillComboBox(grid, "心眼/彈道強化", 1, 4, ySetCount);
		addSkillComboBox(grid, "屬性解放/裝填擴充", 3, 5, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "砲彈裝填數UP", 1, 0, ySetCount);
		addSkillComboBox(grid, "砲術", 3, 1, ySetCount);
		addSkillComboBox(grid, "集中", 3, 2, ySetCount);
		addSkillComboBox(grid, "特殊射擊強化", 2, 3, ySetCount);
		addSkillComboBox(grid, "強化持續", 3, 4, ySetCount);
		addSkillComboBox(grid, "散彈/剛射強化", 1, 5, ySetCount);
		ySetCount++;
		addSkillComboBox(grid, "通常彈/通常箭強化", 1, 0, ySetCount);
		addSkillComboBox(grid, "貫通彈/龍之箭強化", 1, 1, ySetCount);
		ySetCount++;
		grid.add(new Text(""), 0, ySetCount);
		ySetCount++;
		grid.add(new Text("套裝技能"), 0, ySetCount);

		xSetCount = 0;
		ySetCount++;
		try {
			List<String> allLines;
			allLines = Files.readAllLines(Paths.get("database\\MHW_CASESKILL.csv"));
			String name = "";
			for (String line : allLines) {
				String[] data = line.split(",");
				String namenew = data[1];
				if (!namenew.equals(name)) {
					name = namenew;
					if (xSetCount != 5) {
						addSetComboBox(grid, name, 5, xSetCount, ySetCount);
					} else {
						xSetCount = 0;
						ySetCount++;
						addSetComboBox(grid, name, 5, xSetCount, ySetCount);
					}
					xSetCount++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GridPane gridBottom = new GridPane();
		gridBottom.setVgap(4);
		gridBottom.setHgap(10);
		gridBottom.setPadding(new Insets(5, 5, 5, 5));
		Button btnSearch = new Button();
		btnSearch.setText("搜尋");
		btnSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						answer = "";
						answerCount = 0;
						stopByValueNotEnough6 = false;
						stopByValueNotEnough5 = false;
						stopByValueNotEnough4 = false;
						stopByValueNotEnough3 = false;
						stopByValueNotEnough2 = false;
						checkAnswer();
						final Stage dialog = new Stage();
						dialog.initModality(Modality.APPLICATION_MODAL);
						dialog.initOwner(s);
						GridPane dialogVbox = new GridPane();
						dialogVbox.setPadding(new Insets(5));
						dialogVbox.getChildren().add(new Text(
								"搜尋 : " + sellectedSkill.toString() + "    共有 " + answerCount + "條結果\n" + answer));
						Scene dialogScene = new Scene(dialogVbox, -1, -1);
						dialog.setScene(dialogScene);
						dialog.show();
					}
				});
			}
		});
		gridBottom.add(btnSearch, 0, 2);
		Button btnReset = new Button();
		btnReset.setText("重置");
		btnReset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						System.out.println("Reset");
						Iterator<Node> itNode = grid.getChildren().iterator();
						while (itNode.hasNext()) {
							Node node = itNode.next();
							try {
								ComboBox<String> comboBox = (ComboBox<String>) node;
								comboBox.getSelectionModel().select(0);								
							}catch(ClassCastException ex) {
								
							}

						}
					}
				});
			}
		});
		gridBottom.add(btnReset, 1, 2);

		ScrollPane rootScroll = new ScrollPane();
		BorderPane root1 = new BorderPane();
		root1.setPadding(new Insets(5)); // space between elements and window border
		root1.setTop(gridTop);
		root1.setCenter(grid);
		root1.setBottom(gridBottom);
		rootScroll.setContent(root1);
		tab.setContent(rootScroll);
		tab.setClosable(false);
		tp.getTabs().add(tab);

/////珠子
		Tab tab2 = new Tab();
		tab2.setText("現有珠子");
		GridPane grid2 = new GridPane();
		grid2.setVgap(4);
		grid2.setHgap(10);
		grid2.setPadding(new Insets(5, 5, 5, 5));

		xSetCount = 0;
		ySetCount = 0;
		try {
			List<String> allLines;
			allLines = Files.readAllLines(Paths.get("database\\MHW_SKILL.csv"));
			for (String line : allLines) {
				String[] data = line.split(",");
				String name = data[0];
				int count = Integer.valueOf(data[2]);
				if (xSetCount != 5) {
					addStoneComboBox(grid2, name, count, xSetCount, ySetCount);
				} else {
					xSetCount = 0;
					ySetCount++;
					addStoneComboBox(grid2, name, count, xSetCount, ySetCount);
				}
				xSetCount++;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		addStoneComboBox(grid2, "攻擊", 7, 5, 0);
//		addStoneComboBox(grid2, "看破", 7, 1, 0);
//		addStoneComboBox(grid2, "火屬性攻擊強化", 5, 0, 1);
//		addStoneComboBox(grid2, "水屬性攻擊強化", 5, 1, 1);
//		addStoneComboBox(grid2, "冰屬性攻擊強化", 5, 2, 1);
//		addStoneComboBox(grid2, "龍屬性攻擊強化", 5, 3, 1);
//		addStoneComboBox(grid2, "雷屬性攻擊強化", 5, 4, 1);

		HBox hbButtons = new HBox();
		Button btSetZero = new Button();
		btSetZero.setText("全部設為0");
		btSetZero.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				Iterator<Node> itNode = grid2.getChildren().iterator();
				while (itNode.hasNext()) {
					ComboBox<String> comboBox = (ComboBox<String>) itNode.next();
					comboBox.getSelectionModel().select(0);
				}
				System.out.println("全部設為0");
			}
		});
		hbButtons.getChildren().add(btSetZero);
		hbButtons.setMargin(btSetZero, new Insets(0, 5, 0, 0));
		Button btSetMax = new Button();
		btSetMax.setText("全部設為Max");
		btSetMax.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Iterator<Node> itNode = grid2.getChildren().iterator();
				while (itNode.hasNext()) {
					ComboBox<String> comboBox = (ComboBox<String>) itNode.next();
					comboBox.getSelectionModel().select(comboBox.getItems().size() - 1);
				}
				System.out.println("全部設為Max");
			}
		});
		hbButtons.getChildren().add(btSetMax);
		hbButtons.setAlignment(Pos.CENTER_LEFT);
		ScrollPane rootScroll2 = new ScrollPane();
		BorderPane root2 = new BorderPane();
		root2.setPadding(new Insets(5)); // space between elements and window border
		root2.setCenter(grid2);
		root2.setBottom(hbButtons);
		hbButtons.setPadding(new Insets(5));
		rootScroll2.setContent(root2);
		tab2.setContent(rootScroll2);
		tab2.setClosable(false);
		tp.getTabs().add(tab2);
////SET
		Tab tab3 = new Tab();
		tab3.setText("裝備SET");
		tab3.setClosable(false);
		tp.getTabs().add(tab3);

		bp.prefHeightProperty().bind(scene.heightProperty());
		bp.prefWidthProperty().bind(scene.widthProperty());
		bp.setCenter(tp);
		root.getChildren().add(bp);
		s.setScene(scene);
		s.show();
		Stone.loadStones(grid2);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void addWeaponComboBox(GridPane panel, int positionX, int positionY) {
		final ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.setValue("武器鑲嵌槽0-0-0");
		comboBox.getItems().addAll("武器鑲嵌槽1-0-0", "武器鑲嵌槽2-0-0", "武器鑲嵌槽3-0-0", "武器鑲嵌槽1-1-0", "武器鑲嵌槽2-1-0", "武器鑲嵌槽2-2-0",
				"武器鑲嵌槽3-1-0", "武器鑲嵌槽3-2-0", "武器鑲嵌槽3-3-0", "武器鑲嵌槽1-1-1", "武器鑲嵌槽2-1-1", "武器鑲嵌槽2-2-1", "武器鑲嵌槽2-2-2",
				"武器鑲嵌槽3-1-1", "武器鑲嵌槽3-2-1", "武器鑲嵌槽3-2-2", "武器鑲嵌槽3-3-1", "武器鑲嵌槽3-3-2", "武器鑲嵌槽3-3-3");
		comboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				weaponHole.clear();
				String line = comboBox.getValue();
				Pattern pattern = Pattern.compile("武器鑲嵌槽(.)-");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					if (weaponHole.checkContain("鑲嵌槽" + Integer.valueOf(matcher.group(1)))) {
						weaponHole.getSkill("鑲嵌槽" + Integer.valueOf(matcher.group(1))).point++;
					} else if (Integer.valueOf(matcher.group(1)) != 0) {
						weaponHole.add(new Skill("鑲嵌槽" + Integer.valueOf(matcher.group(1)), 1));
					}
				}
				Pattern pattern2 = Pattern.compile("武器鑲嵌槽.-(.)");
				Matcher matcher2 = pattern2.matcher(line);
				if (matcher2.find()) {
					if (weaponHole.checkContain("鑲嵌槽" + Integer.valueOf(matcher2.group(1)))) {
						weaponHole.getSkill("鑲嵌槽" + Integer.valueOf(matcher2.group(1))).point++;
					} else if (Integer.valueOf(matcher2.group(1)) != 0) {
						weaponHole.add(new Skill("鑲嵌槽" + Integer.valueOf(matcher2.group(1)), 1));
					}
				}
				Pattern pattern3 = Pattern.compile("武器鑲嵌槽.-.-(.)");
				Matcher matcher3 = pattern3.matcher(line);
				if (matcher3.find()) {
					if (weaponHole.checkContain("鑲嵌槽" + Integer.valueOf(matcher3.group(1)))) {
						weaponHole.getSkill("鑲嵌槽" + Integer.valueOf(matcher3.group(1))).point++;
					} else if (Integer.valueOf(matcher3.group(1)) != 0) {
						weaponHole.add(new Skill("鑲嵌槽" + Integer.valueOf(matcher3.group(1)), 1));
					}
				}
				System.out.println(weaponHole.toString());
			}
		});
		panel.add(comboBox, positionX, positionY);
	}

	public static void addSkillComboBox(GridPane panel, String name, int limit, int positionX, int positionY) {
		final ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.setValue(name + 0);
		for (int i = 0; i < limit + 1; i++) {
			comboBox.getItems().add(name + i);
		}
		comboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (comboBox.getSelectionModel().getSelectedIndex() == 0) {
					sellectedSkill.removeSkill(name);
				} else if (sellectedSkill.checkContain(name)) {
					sellectedSkill.getSkill(name).point = comboBox.getSelectionModel().getSelectedIndex();
				} else {
					sellectedSkill.addSkill(name, comboBox.getSelectionModel().getSelectedIndex());
				}
			}
		});
		panel.add(comboBox, positionX, positionY);
	}

	public static void addSetComboBox(GridPane panel, String name, int limit, int positionX, int positionY) {
		final ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.setValue(name + 0);
		for (int i = 0; i < limit + 1; i++) {
			comboBox.getItems().add(name + i);
		}
		comboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (comboBox.getSelectionModel().getSelectedIndex() == 0) {
					sellectedSet.removeSkill(name);
				} else if (sellectedSet.checkContain(name)) {
					sellectedSet.getSkill(name).point = comboBox.getSelectionModel().getSelectedIndex();
				} else {
					sellectedSet.addSkill(name, comboBox.getSelectionModel().getSelectedIndex());
				}
				for (Skill skill : sellectedSet) {
					System.out.println(skill + " " + sellectedSet.size());
				}
			}
		});
		panel.add(comboBox, positionX, positionY);
	}

	public static void addStoneComboBox(GridPane panel, String name, int limit, int positionX, int positionY) {
		final ComboBox<String> comboBox = new ComboBox<String>();
		if (Stone.nowStones.containsKey(name)) {
			comboBox.setValue(name + Stone.nowStones.get(name));
		} else {
			comboBox.setValue(name + 0);
		}
		for (int i = 0; i < limit + 1; i++) {
			comboBox.getItems().add(name + i);
		}
		comboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Stone.nowStones.put(name, comboBox.getSelectionModel().getSelectedIndex());
				Stone.saveStones();
			}
		});

		panel.add(comboBox, positionX, positionY);
	}

	public static void checkAnswer() {
		int value = 0;
		for (Skill skill : sellectedSkill) {
			value += skill.point;
			System.out.println("選了 " + skill.toString());
		}
////Head
//		System.out.println("value " + value);
//		int i = 0;
		int equipment1Count = 0;
		for (Equipment equipment : Equipment.filtSkill("頭", Equipment.Head, Equipment.HeadHolesCount, sellectedSkill,
				sellectedSet)) {
			equipment1Count++;
			int value1 = value - equipment.value;
//			System.out.println("value1 " + value1 + " count : " + equipment1Count);
//			System.out.println("head" + i++);
			if (stopByValueNotEnough6 && stopByValueNotEnough5 && stopByValueNotEnough4&& stopByValueNotEnough3&& stopByValueNotEnough2) {
				if (equipment1Count == 2) {
					break;
				} else {
					stopByValueNotEnough2 = false;
					stopByValueNotEnough3 = false;
					stopByValueNotEnough4 = false;
					stopByValueNotEnough5 = false;
					stopByValueNotEnough6 = false;
					break;
				}
			}
			if (answerCount > answerLimit)
				break;
			boolean jump = false;
			for (String string : filterArray) {
				if (equipment.name.startsWith(string)) {
					jump = true;
				}
				if (jump) {
					break;
				}
			}
			if (jump) {
				continue;
			}

			SellectedSkill tmpSellectedSkill = new SellectedSkill(sellectedSkill);
			SellectedSkill holes = new SellectedSkill(weaponHole);
			if (tmpSellectedSkill.checkContain(equipment.skills[0].name)) {
				tmpSellectedSkill.getSkill(equipment.skills[0].name).point -= equipment.skills[0].point;
			}
			if (tmpSellectedSkill.checkContain(equipment.skills[1].name)) {
				tmpSellectedSkill.getSkill(equipment.skills[1].name).point -= equipment.skills[1].point;
			}
			if (equipment.holes[0] != 0) {
				if (holes.checkContain("鑲嵌槽" + equipment.holes[0])) {
					holes.getSkill("鑲嵌槽" + equipment.holes[0]).point++;
				} else {
					holes.addSkill("鑲嵌槽" + equipment.holes[0], 1);
				}
				if (equipment.holes[1] != 0) {
					if (holes.checkContain("鑲嵌槽" + equipment.holes[1])) {
						holes.getSkill("鑲嵌槽" + equipment.holes[1]).point++;
					} else {
						holes.addSkill("鑲嵌槽" + equipment.holes[1], 1);
					}
					if (equipment.holes[2] != 0) {
						if (holes.checkContain("鑲嵌槽" + equipment.holes[2])) {
							holes.getSkill("鑲嵌槽" + equipment.holes[2]).point++;
						} else {
							holes.addSkill("鑲嵌槽" + equipment.holes[2], 1);
						}
					}
				}
			}
////Body
//			int j = 0;
			int equipment2Count = 0;
			for (Equipment equipment2 : Equipment.filtSkill("身", Equipment.Body, Equipment.BodyHolesCount,
					sellectedSkill, sellectedSet)) {
				equipment2Count++;
				int value2 = value1 - equipment2.value;
//				System.out.println("value2 " + value2+ " count : " + equipment2Count);
//				System.out.println("body" + j++);
				if (stopByValueNotEnough6 && stopByValueNotEnough5 && stopByValueNotEnough4&& stopByValueNotEnough3) {
					if (equipment2Count == 2) {
						stopByValueNotEnough2 = true;
						break;
					} else {
						stopByValueNotEnough3 = false;
						stopByValueNotEnough4 = false;
						stopByValueNotEnough5 = false;
						stopByValueNotEnough6 = false;
						break;
					}
				}
				if (answerCount > answerLimit)
					break;
				boolean jump2 = false;
				for (String string : filterArray) {
					if (equipment2.name.startsWith(string)) {
						jump2 = true;
					}
					if (jump2) {
						break;
					}
				}
				if (jump2) {
					continue;
				}
				SellectedSkill tmpSellectedSkill2 = new SellectedSkill(tmpSellectedSkill);
				SellectedSkill holes2 = new SellectedSkill(holes);
				if (tmpSellectedSkill2.checkContain(equipment2.skills[0].name)) {
					tmpSellectedSkill2.getSkill(equipment2.skills[0].name).point -= equipment2.skills[0].point;
				}
				if (tmpSellectedSkill2.checkContain(equipment2.skills[1].name)) {
					tmpSellectedSkill2.getSkill(equipment2.skills[1].name).point -= equipment2.skills[1].point;
				}
				if (equipment2.holes[0] != 0) {
					if (holes2.checkContain("鑲嵌槽" + equipment2.holes[0])) {
						holes2.getSkill("鑲嵌槽" + equipment2.holes[0]).point++;
					} else {
						holes2.addSkill("鑲嵌槽" + equipment2.holes[0], 1);
					}
					if (equipment2.holes[1] != 0) {
						if (holes2.checkContain("鑲嵌槽" + equipment2.holes[1])) {
							holes2.getSkill("鑲嵌槽" + equipment2.holes[1]).point++;
						} else {
							holes2.addSkill("鑲嵌槽" + equipment2.holes[1], 1);
						}
						if (equipment2.holes[2] != 0) {
							if (holes2.checkContain("鑲嵌槽" + equipment2.holes[2])) {
								holes2.getSkill("鑲嵌槽" + equipment2.holes[2]).point++;
							} else {
								holes2.addSkill("鑲嵌槽" + equipment2.holes[2], 1);
							}
						}
					}
				}
////Hand
//				int k = 0;
				int equipment3Count = 0;
				for (Equipment equipment3 : Equipment.filtSkill("手", Equipment.Hand, Equipment.HandHolesCount,
						sellectedSkill, sellectedSet)) {
					equipment3Count++;
					int value3 = value2 - equipment3.value;
//					System.out.println("value3 " + value3+ " count : " + equipment3Count);
//					System.out.println("hand" + k++);
					if (stopByValueNotEnough6 && stopByValueNotEnough5 && stopByValueNotEnough4) {
						if (equipment3Count == 2) {
							stopByValueNotEnough3 = true;
							break;
						} else {
							stopByValueNotEnough4 = false;
							stopByValueNotEnough5 = false;
							stopByValueNotEnough6 = false;
							break;
						}
					}
					if (answerCount > answerLimit)
						break;
					boolean jump3 = false;
					for (String string : filterArray) {
						if (equipment3.name.startsWith(string)) {
							jump3 = true;
						}
						if (jump3) {
							break;
						}
					}
					if (jump3) {
						continue;
					}
					SellectedSkill tmpSellectedSkill3 = new SellectedSkill(tmpSellectedSkill2);
					SellectedSkill holes3 = new SellectedSkill(holes2);
					if (tmpSellectedSkill3.checkContain(equipment3.skills[0].name)) {
						tmpSellectedSkill3.getSkill(equipment3.skills[0].name).point -= equipment3.skills[0].point;
					}
					if (tmpSellectedSkill3.checkContain(equipment3.skills[1].name)) {
						tmpSellectedSkill3.getSkill(equipment3.skills[1].name).point -= equipment3.skills[1].point;
					}
					if (equipment3.holes[0] != 0) {
						if (holes3.checkContain("鑲嵌槽" + equipment3.holes[0])) {
							holes3.getSkill("鑲嵌槽" + equipment3.holes[0]).point++;
						} else {
							holes3.addSkill("鑲嵌槽" + equipment3.holes[0], 1);
						}
						if (equipment3.holes[1] != 0) {
							if (holes3.checkContain("鑲嵌槽" + equipment3.holes[1])) {
								holes3.getSkill("鑲嵌槽" + equipment3.holes[1]).point++;
							} else {
								holes3.addSkill("鑲嵌槽" + equipment3.holes[1], 1);
							}
							if (equipment3.holes[2] != 0) {
								if (holes3.checkContain("鑲嵌槽" + equipment3.holes[2])) {
									holes3.getSkill("鑲嵌槽" + equipment3.holes[2]).point++;
								} else {
									holes3.addSkill("鑲嵌槽" + equipment3.holes[2], 1);
								}
							}
						}
					}
////Pants
//					int l = 0;
					int equipment4Count = 0;
					for (Equipment equipment4 : Equipment.filtSkill("褲", Equipment.Pants, Equipment.PantsHolesCount,
							sellectedSkill, sellectedSet)) {
						equipment4Count++;
						if (stopByValueNotEnough6 && stopByValueNotEnough5) {
							if (equipment4Count == 2) {
								stopByValueNotEnough4 = true;
								break;
							} else {
								stopByValueNotEnough5 = false;
								stopByValueNotEnough6 = false;
								break;
							}
						}
						int value4 = value3 - equipment4.value;
//						System.out.println("value4 " + value4+ " count : " + equipment4Count);
//						System.out.println("pants"+l++);
						if (answerCount > answerLimit)
							break;
						boolean jump4 = false;
						for (String string : filterArray) {
							if (equipment4.name.startsWith(string)) {
								jump4 = true;
							}
							if (jump4) {
								break;
							}
						}
						if (jump4) {
							continue;
						}
						SellectedSkill tmpSellectedSkill4 = new SellectedSkill(tmpSellectedSkill3);
						SellectedSkill holes4 = new SellectedSkill(holes3);
						if (tmpSellectedSkill4.checkContain(equipment4.skills[0].name)) {
							tmpSellectedSkill4.getSkill(equipment4.skills[0].name).point -= equipment4.skills[0].point;
						}
						if (tmpSellectedSkill4.checkContain(equipment4.skills[1].name)) {
							tmpSellectedSkill4.getSkill(equipment4.skills[1].name).point -= equipment4.skills[1].point;
						}
						if (equipment4.holes[0] != 0) {
							if (holes4.checkContain("鑲嵌槽" + equipment4.holes[0])) {
								holes4.getSkill("鑲嵌槽" + equipment4.holes[0]).point++;
							} else {
								holes4.addSkill("鑲嵌槽" + equipment4.holes[0], 1);
							}
							if (equipment4.holes[1] != 0) {
								if (holes4.checkContain("鑲嵌槽" + equipment4.holes[1])) {
									holes4.getSkill("鑲嵌槽" + equipment4.holes[1]).point++;
								} else {
									holes4.addSkill("鑲嵌槽" + equipment4.holes[1], 1);
								}
								if (equipment4.holes[2] != 0) {
									if (holes4.checkContain("鑲嵌槽" + equipment4.holes[2])) {
										holes4.getSkill("鑲嵌槽" + equipment4.holes[2]).point++;
									} else {
										holes4.addSkill("鑲嵌槽" + equipment4.holes[2], 1);
									}
								}
							}
						}
////Foot
						int equipment5Count = 0;
						for (Equipment equipment5 : Equipment.filtSkill("腳", Equipment.Foot, Equipment.FootHolesCount,
								sellectedSkill, sellectedSet)) {
							equipment5Count++;
							int value5 = value4 - equipment5.value;
//							System.out.println("value5 " + value5+ " count : " + equipment5Count);
							if (stopByValueNotEnough6) {
								if (equipment5Count == 2) {
									stopByValueNotEnough5 = true;
									break;
								} else {
									stopByValueNotEnough6 = false;
									break;
								}
							}

							if (answerCount > answerLimit)
								break;
							boolean jump5 = false;
							for (String string : filterArray) {
								if (equipment5.name.startsWith(string)) {
									jump5 = true;
								}
								if (jump5) {
									break;
								}
							}
							if (jump5) {
								continue;
							}
							SellectedSkill tmpSellectedSkill5 = new SellectedSkill(tmpSellectedSkill4);
							SellectedSkill holes5 = new SellectedSkill(holes4);
							if (tmpSellectedSkill5.checkContain(equipment5.skills[0].name)) {
								tmpSellectedSkill5
										.getSkill(equipment5.skills[0].name).point -= equipment5.skills[0].point;
							}
							if (tmpSellectedSkill5.checkContain(equipment5.skills[1].name)) {
								tmpSellectedSkill5
										.getSkill(equipment5.skills[1].name).point -= equipment5.skills[1].point;
							}
							if (equipment5.holes[0] != 0) {
								if (holes5.checkContain("鑲嵌槽" + equipment5.holes[0])) {
									holes5.getSkill("鑲嵌槽" + equipment5.holes[0]).point++;
								} else {
									holes5.addSkill("鑲嵌槽" + equipment5.holes[0], 1);
								}
								if (equipment5.holes[1] != 0) {
									if (holes5.checkContain("鑲嵌槽" + equipment5.holes[1])) {
										holes5.getSkill("鑲嵌槽" + equipment5.holes[1]).point++;
									} else {
										holes5.addSkill("鑲嵌槽" + equipment5.holes[1], 1);
									}
									if (equipment5.holes[2] != 0) {
										if (holes5.checkContain("鑲嵌槽" + equipment5.holes[2])) {
											holes5.getSkill("鑲嵌槽" + equipment5.holes[2]).point++;
										} else {
											holes5.addSkill("鑲嵌槽" + equipment5.holes[2], 1);
										}
									}
								}
							}
////Stone
							int equipment6Count = 0;
							for (Equipment equipment6 : Equipment.filtSkill("石", Equipment.Stone,
									Equipment.StoneHolesCount, sellectedSkill, sellectedSet)) {
								equipment6Count++;
								int value6 = value5 - equipment6.value;
//								System.out.println("value6 " + value6+ " count : " + equipment6Count);
								if (value6 > 0) {
									if (equipment6Count == 1) {
										stopByValueNotEnough6 = true;
										break;
									} else
										break;
								}
								if (answerCount > answerLimit)
									break;
								boolean jump6 = false;
								for (String string : filterArray) {
									if (equipment6.name.startsWith(string)) {
										jump6 = true;
									}
									if (jump6) {
										break;
									}
								}
								if (jump6) {
									continue;
								}
								SellectedSkill tmpSellectedSkill6 = new SellectedSkill(tmpSellectedSkill5);
								SellectedSkill holes6 = new SellectedSkill(holes5);
								if (tmpSellectedSkill6.checkContain(equipment6.skills[0].name)) {
									tmpSellectedSkill6
											.getSkill(equipment6.skills[0].name).point -= equipment6.skills[0].point;
								}
								if (tmpSellectedSkill6.checkContain(equipment6.skills[1].name)) {
									tmpSellectedSkill6
											.getSkill(equipment6.skills[1].name).point -= equipment6.skills[1].point;
								}
								if (equipment6.holes[0] != 0) {
									if (holes6.checkContain("鑲嵌槽" + equipment6.holes[0])) {
										holes6.getSkill("鑲嵌槽" + equipment6.holes[0]).point++;
									} else {
										holes6.addSkill("鑲嵌槽" + equipment6.holes[0], 1);
									}
									if (equipment6.holes[1] != 0) {
										if (holes6.checkContain("鑲嵌槽" + equipment6.holes[1])) {
											holes6.getSkill("鑲嵌槽" + equipment6.holes[1]).point++;
										} else {
											holes6.addSkill("鑲嵌槽" + equipment6.holes[1], 1);
										}
										if (equipment6.holes[2] != 0) {
											if (holes6.checkContain("鑲嵌槽" + equipment6.holes[2])) {
												holes6.getSkill("鑲嵌槽" + equipment6.holes[2]).point++;
											} else {
												holes6.addSkill("鑲嵌槽" + equipment6.holes[2], 1);
											}
										}
									}
								}
								checkResult(tmpSellectedSkill6, holes6, equipment, equipment2, equipment3, equipment4,
										equipment5, equipment6);
							}
						}
					}
				}
			}
		}
	}

	public static void checkResult(SellectedSkill sellectedSkill, SellectedSkill holes, Equipment head, Equipment body,
			Equipment hand, Equipment pants, Equipment foot, Equipment stone) {
		String stoneNeed = "";
		boolean pass = true;
		SellectedSkill tmpholes = new SellectedSkill(holes);

////確認set
		if (sellectedSet.size() > 0) {
			SellectedSkill tmpSellectedSet = new SellectedSkill(sellectedSet);
			if (tmpSellectedSet.checkContain(head.set))
				tmpSellectedSet.getSkill(head.set).point--;
			if (tmpSellectedSet.checkContain(body.set))
				tmpSellectedSet.getSkill(body.set).point--;
			if (tmpSellectedSet.checkContain(hand.set))
				tmpSellectedSet.getSkill(hand.set).point--;
			if (tmpSellectedSet.checkContain(pants.set))
				tmpSellectedSet.getSkill(pants.set).point--;
			if (tmpSellectedSet.checkContain(foot.set))
				tmpSellectedSet.getSkill(foot.set).point--;
			for (Skill skill : tmpSellectedSet) {
				if (skill.point > 0) {
					pass = false;
					break;
				}
			}
//			System.out.println(sellectedSet.toString()+" "+head.set+" "+body.set+" "+hand.set+" "+pants.set+" "+foot.set+" "+pass);
		}

		for (Skill skill : sellectedSkill) {
			if (pass == false)
				break;
			if (!Stone.nowStones.containsKey(skill.name) || Stone.nowStones.get(skill.name) < skill.point) {
				pass = false;
			} else if (skill.point > 0) {
				if (!Skill.SkillCost.containsKey(skill.name)) {
					pass = false;
				} else {
					int cost = Skill.SkillCost.get(skill.name);
					if (skill.point > 0) {
						if (Skill.SkillCost.get(skill.name) == 1) {
							int count = 0;
							for (int s0 = 0; s0 < skill.point; s0++) {
								if (tmpholes.checkContain("鑲嵌槽1") && tmpholes.getSkill("鑲嵌槽1").point > 0) {
									tmpholes.getSkill("鑲嵌槽1").point--;
									count++;
								} else if (tmpholes.checkContain("鑲嵌槽2") && tmpholes.getSkill("鑲嵌槽2").point > 0) {
									tmpholes.getSkill("鑲嵌槽2").point--;
									count++;
								} else if (tmpholes.checkContain("鑲嵌槽3") && tmpholes.getSkill("鑲嵌槽3").point > 0) {
									tmpholes.getSkill("鑲嵌槽3").point--;
									count++;
								} else {
									pass = false;
									break;
								}
							}
							if (count > 0) {
								stoneNeed = stoneNeed + skill.name + count + " ";
							}
						} else if (Skill.SkillCost.get(skill.name) == 2) {
							int count = 0;
							for (int s0 = 0; s0 < skill.point; s0++) {
								if (tmpholes.checkContain("鑲嵌槽2") && tmpholes.getSkill("鑲嵌槽2").point > 0) {
									tmpholes.getSkill("鑲嵌槽2").point--;
									count++;
								} else if (tmpholes.checkContain("鑲嵌槽3") && tmpholes.getSkill("鑲嵌槽3").point > 0) {
									tmpholes.getSkill("鑲嵌槽3").point--;
									count++;
								} else {
									pass = false;
									break;
								}
							}
							if (count > 0) {
								stoneNeed = stoneNeed + skill.name + count;
							}
						} else if (Skill.SkillCost.get(skill.name) == 3) {
							int count = 0;
							for (int s0 = 0; s0 < skill.point; s0++) {
								if (tmpholes.checkContain("鑲嵌槽3") && tmpholes.getSkill("鑲嵌槽3").point > 0) {
									tmpholes.getSkill("鑲嵌槽3").point--;
									count++;
								} else {
									pass = false;
									break;
								}
							}
							if (count > 0) {
								stoneNeed = stoneNeed + skill.name + count + " ";
							}
						}
					}
				}
			}
		}
		if (pass) {
//			System.out.println(head.name + " " + body.name + " " + hand.name + " " + pants.name + " " + foot.name + " "
//					+ stone.name + " " +"  剩餘洞數 : "+ tmpholes.toString() + "  需要石頭 : " + stoneNeed);
			answer = answer + head.name + " " + body.name + " " + hand.name + " " + pants.name + " " + foot.name + " "
					+ stone.name + " " + "  剩餘洞數 : " + tmpholes.toString() + "  需要石頭 : " + stoneNeed + "\n";
			answerCount++;
		}
	}
}