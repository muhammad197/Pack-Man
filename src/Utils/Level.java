package Utils;

public enum Level {
	//values
	 easy(1), medium(2), hard(3), super_hard(4);
	
	//class member
	private final int num;
	
	public int getNum() {
		return num;
	}

	//constructor
	Level(int i) { 
		this.num= i;
		}

	//return difficulty by level number
	public static Level returnDifficulty(int level) {
		switch (level) {
		case 1:
			return Level.easy;
		case 2:
			return Level.medium;
		case 3:
			return Level.hard;
		case 4:
			return Level.super_hard;
		}
		return null;
	}

	

}
