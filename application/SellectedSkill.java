package application;
import java.util.ArrayList;

public class SellectedSkill extends ArrayList<Skill> {
	
	public SellectedSkill() {
		
	}
	public SellectedSkill(SellectedSkill sellectedSkill) {
		int size = sellectedSkill.size();
		this.clear();
		for(int i = 0;i<size;i++) {
			this.add(new Skill(sellectedSkill.get(i).name,sellectedSkill.get(i).point));
		}
		
	}
	
	public boolean checkContain(String name) {
		boolean contained = false;
		for (Skill skill : this) {
			if (skill.name != null && skill.name.equals(name)) {
				contained = true;
			}
		}
		return contained;
	}
	
	public Skill getSkill(String name) {
		Skill skill = null;
		for (Skill Fskill : this) {
			if (Fskill.name != null && Fskill.name.equals(name)) {
				skill = Fskill;
			}
		}
		return skill;
	}
	
	public void addSkill(String name,int point) {
		Skill skill = new Skill();
		skill.name = name;
		skill.point = point;
		this.add(skill);
	}
	
	public void removeSkill(String name) {
		for (Skill Fskill : this) {
			if (Fskill.name != null && Fskill.name.equals(name)) {
				this.remove(Fskill);
				break;
			}
		}
	}
	
	public String toString() {
		String answer = "";
		for(Skill skill : this) {
			if(skill.point!=0)
			answer = answer+" "+skill.name+" "+skill.point;
		}
		return answer;
	}
}
