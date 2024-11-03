package dungeon;

public enum Direction {
	UP, DOWN, LEFT, RIGHT;

	public Direction getOpposite(){
		if(this.equals(Direction.UP)) return Direction.DOWN;
		if(this.equals(Direction.DOWN)) return Direction.UP;
		if(this.equals(Direction.LEFT)) return Direction.RIGHT;
		return Direction.LEFT;
	}
}
