
public class Item {

		public String name;
		public String title;
		public String description;
		public Integer attack;
		public boolean can_eat;
		public Integer hp;
		
		public Item(String name, String title, String description, Integer attack, Integer hp, boolean can_eat) {
			this.setName(name);
			this.setTitle(title);
			this.setDescription(description);
			this.setAttack(attack);
			this.setCanEat(can_eat);
			this.setHp(hp);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getAttack() {
			return attack;
		}

		public void setAttack(Integer attack) {
			this.attack = attack;
		}

		public boolean getCanEat() {
			return can_eat;
		}

		public void setCanEat(boolean can_eat) {
			this.can_eat = can_eat;
		}

		public Integer getHp() {
			return hp;
		}

		public void setHp(Integer hp) {
			this.hp = hp;
		}
		
		
}
