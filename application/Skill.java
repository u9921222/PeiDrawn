package application;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
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
//		SkillCost.put("攻擊",1);
//		SkillCost.put("看破",1);
//		SkillCost.put("KO術",2);
//		SkillCost.put("力量解放",2);
//		SkillCost.put("弱點特效",2);
//		SkillCost.put("水屬性攻擊強化",1);
//		SkillCost.put("火屬性攻擊強化",1);
//		SkillCost.put("冰屬性攻擊強化",1);
//		SkillCost.put("雷屬性攻擊強化",1);
//		SkillCost.put("龍屬性攻擊強化",1);
//		SkillCost.put("屬性解放/裝填擴張",3);
	}
	
	public static Comparator<Skill> SkillNameComparator = new Comparator<Skill>() {

		@Override
		public int compare(Skill arg0, Skill arg1) {
			int ret = 999;
			if(arg0.name.length()>0 && arg1.name.length()>0) {
				ret =(int)arg1.name.charAt(0) - (int)arg0.name.charAt(0); 
			}else if (arg1.name.length()>0) {
				ret =(int)arg1.name.charAt(0);
			}else if (arg0.name.length()>0) {
				ret =(int)arg0.name.charAt(0);
			}
			return ret;
		}
	};
}
