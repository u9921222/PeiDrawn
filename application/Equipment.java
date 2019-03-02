package application;

import java.awt.HeadlessException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AbstractDocument.BranchElement;

public class Equipment {
	public String name;
	public int defenence;
	public int[] holes = { 0, 0, 0 };
	public int value=0;
	public Skill[] skills = { new Skill(), new Skill() };
	public String set = null;
	public String[] setSkills = { "", "" };
	public int[] setNeed = { 0, 0 };

	public Equipment() {

	}

	public Equipment(Equipment equipment) {
		this.name = equipment.name;
		this.defenence = equipment.defenence;
		this.holes[0] = equipment.holes[0];
		this.holes[1] = equipment.holes[1];
		this.holes[2] = equipment.holes[2];
		this.skills[0] = new Skill(equipment.skills[0].name, equipment.skills[0].point);
		this.skills[1] = new Skill(equipment.skills[1].name, equipment.skills[1].point);
		this.set = equipment.set;
		this.setSkills[0] = equipment.setSkills[0];
		this.setSkills[1] = equipment.setSkills[1];
		this.setNeed[0] = equipment.setNeed[0];
		this.setNeed[1] = equipment.setNeed[1];
		this.value = equipment.value;
	}

	public Equipment(String name, String skill1, int point1, String skill2, int point2, int hole1, int hole2, int hole3,
			int def, String setName, String setSkill1, int setNeed, String setSkill2, int setNeed2,int value) {
		this.name = name;
		if (!skill1.equals("")) {
			Skill sskill1 = new Skill(skill1, point1);
			this.skills[0] = sskill1;
			if (!skill2.equals("")) {
				Skill sskill2 = new Skill(skill2, point2);
				this.skills[1] = sskill2;
			}
		}
		if (hole1 != 0) {
			this.holes[0] = hole1;
			if (hole2 != 0) {
				this.holes[1] = hole2;
			}
			if (hole3 != 0) {
				this.holes[2] = hole3;
			}
		}
		this.defenence = def;

		this.set = setName;
		this.setSkills[0] = setSkill1;
		this.setNeed[0] = setNeed;
		this.setSkills[1] = setSkill2;
		this.setNeed[1] = setNeed2;
		this.value = value; 
	}

	public static void readFile(String path,ArrayList<Equipment> part,int[][][] holesCount) {
		List<String> allLines;
		try {
			allLines = Files.readAllLines(Paths.get(path));
			for (String line : allLines) {
				String[] data = line.split(",");
				String name = data[0];
				String skill1 = data[9];
				String skill2 = data[11];
				int point1 = 0;
				int point2 = 0;
				int hole1 = 0;
				int hole2 = 0;
				int hole3 = 0;
				int def = 0;
				int value = 0;
				if (!data[10].equals("")) {
					point1 = Integer.valueOf(data[10]);
				}
				if (!data[12].equals("")) {
					point2 = Integer.valueOf(data[12]);
				}
				if (!data[2].equals("")) {
					def = Integer.valueOf(data[2]);
				}
				if (!data[8].substring(0, 1).equals("-")) {
					hole1 = Integer.valueOf(data[8].substring(0, 1));
					value++;
				}
				if (!data[8].substring(1, 2).equals("-")) {
					hole2 = Integer.valueOf(data[8].substring(1, 2));
					value++;
				}
				if (!data[8].substring(2, 3).equals("-")) {
					hole3 = Integer.valueOf(data[8].substring(2, 3));
					value++;
				}
				String set = data[23];
				Equipment equipment = new Equipment(name, skill1, point1, skill2, point2, hole1, hole2, hole3, def, set,
						null, 0, null, 0,value);
				holesCount[hole1][hole2][hole3]++;
				part.add(equipment);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int[][][] HeadHolesCount = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } };
	public static ArrayList<Equipment> Head = new ArrayList<Equipment>();
	static {
		readFile("database\\MHW_HEAD.csv", Head, HeadHolesCount);
	}
	public static int[][][] BodyHolesCount = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } };
	public static ArrayList<Equipment> Body = new ArrayList<Equipment>();
	static {
		readFile("database\\MHW_BODY.csv", Body, BodyHolesCount);
	}
	public static int[][][] HandHolesCount = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } };
	public static ArrayList<Equipment> Hand = new ArrayList<Equipment>();
	static {
		readFile("database\\MHW_HAND.csv", Hand, HandHolesCount);
	}
	public static int[][][] PantsHolesCount = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } };
	public static ArrayList<Equipment> Pants = new ArrayList<Equipment>();
	static {
		readFile("database\\MHW_WAIST.csv", Pants, PantsHolesCount);
	}
	public static int[][][] FootHolesCount = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } };
	public static ArrayList<Equipment> Foot = new ArrayList<Equipment>();
	static {
		readFile("database\\MHW_LEG.csv", Foot, FootHolesCount);
	}
	public static int[][][] StoneHolesCount = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } } };
	public static ArrayList<Equipment> Stone = new ArrayList<Equipment>();
	static {
		List<String> allLines;
		try {
			allLines = Files.readAllLines(Paths.get("database\\MHW_GUARDIANSTONE.csv"));
			for (String line : allLines) {
				String[] data = line.split(",");
				String name = data[0];
				String skill1 = data[1];
				String skill2 = data[3];
				int point1 = 0;
				int point2 = 0;
				int hole1 = 0;
				int hole2 = 0;
				int hole3 = 0;
				int def = 0;
				int value = 0;

				if (!data[2].equals("")) {
					point1 = Integer.valueOf(data[2]);
					value++;
				}
				if (!data[4].equals("")) {
					point2 = Integer.valueOf(data[4]);
					value++;
				}
				Equipment equipment = new Equipment(name, skill1, point1, skill2, point2, hole1, hole2, hole3, def, "",
						null, 0, null, 0,value);
				Stone.add(equipment);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<Equipment> filtSkill(String part,ArrayList<Equipment> equipments, int[][][] holesCount,
			SellectedSkill skillWant, SellectedSkill setWant) {
		ArrayList<Equipment> filtedEquipments = new ArrayList<Equipment>();
		filtedEquipments.add(new Equipment("任意", "", 0, "", 0, 0, 0, 0, 0, "", "", 6, "", 6,0));
		for (Equipment tmpequipment : equipments) {
			Equipment equipment = new Equipment(tmpequipment); 
			int value = equipment.value;
			boolean match = false;
				if (skillWant.checkContain(equipment.skills[0].name)) {
					match = true;
					value+=equipment.skills[0].point;
				}
				if (skillWant.checkContain(equipment.skills[1].name)) {
					match = true;
					value+=equipment.skills[1].point;
				}
				if (setWant.checkContain(equipment.set)) {
					match = true;
				}
			
			if (match) {
				equipment.value = value;
				filtedEquipments.add(equipment);
//				System.out.println("Filted : "+equipment.toString());
				continue;
			}
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					if (holesCount[i][j][k] > 0) {
						int value2 = 0;
//					System.out.println(i+"-"+j+"-"+k+" "+holesCount[i][j][k]+" ");
						if(i>0)value2++;
						if(j>0)value2++;
						if(k>0)value2++;
						filtedEquipments.add(new Equipment("任意" + i + "-" + j + "-" + k +part, "", 0, "", 0, i, j, k, 0,
								"", "", 6, "", 6,value2));
//						System.out.println(i + "-" + j + "-" + k + " " + holesCount[i][j][k] + " " + filtedEquipments.toString());
					}
				}
			}
		}
		filtedEquipments.sort(EquValueComparator);
		return filtedEquipments;
	}

	public String toString() {
		return "Equipment : " + this.name + " " + this.skills[0] + " " + this.skills[1]+" holes : "+this.holes[0]+this.holes[1]+this.holes[2]+" value : "+this.value;
	}
	
	 public static Comparator<Equipment> EquValueComparator = new Comparator<Equipment>() {

			@Override
			public int compare(Equipment arg0, Equipment arg1) {
				return arg1.value-arg0.value;
			}};
}
