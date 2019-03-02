package application;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Skill {

	public String name = "";
	public int point = 0;

	public Skill() {
		
	}
	
	public Skill(String name,int point) {
		this.name = name;
		this.point = point;
	}
	
	public String toString() {
		return name + point;
	}

	public static HashMap<String,Integer> SkillCost = new HashMap<String, Integer>();
	static {
		List<String> allLines;
		try {
			allLines = Files.readAllLines(Paths.get("database\\MHW_DECO.csv"));
			for (String line : allLines) {
				String[] data = line.split(",");
				SkillCost.put(data[1], Integer.valueOf(data[2]));
//				System.out.println("cost : "+data[1]+ Integer.valueOf(data[2]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		SkillCost.put("����",1);
//		SkillCost.put("�ݯ}",1);
//		SkillCost.put("KO�N",2);
//		SkillCost.put("�O�q�ѩ�",2);
//		SkillCost.put("�z�I�S��",2);
//		SkillCost.put("���ݩʧ����j��",1);
//		SkillCost.put("���ݩʧ����j��",1);
//		SkillCost.put("�B�ݩʧ����j��",1);
//		SkillCost.put("�p�ݩʧ����j��",1);
//		SkillCost.put("�s�ݩʧ����j��",1);
//		SkillCost.put("�ݩʸѩ�/�˶��X�i",3);
	}
}
