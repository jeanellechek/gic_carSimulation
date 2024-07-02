package gic_carSimulation;

import java.util.Arrays;
import java.util.List;

public class Car {
	public String name;
	public int x;
	public int y;
	public char direction;
	public String commands;
	public List<Character> directionArr = Arrays.asList('N', 'E', 'S', 'W');

	public Car(String name, int x, int y, char direction, String commands) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.commands = commands;
	}

	/**
	 * Turn the car 90 degrees to the left
	 */
	public void turnLeft() {
		int currentIndex = directionArr.indexOf(direction);
		direction = directionArr.get((currentIndex + directionArr.size() - 1) % directionArr.size());
	}

	/**
	 * Turn the car 90 degrees to the right
	 */
	public void turnRight() {
		int currentIndex = directionArr.indexOf(direction);
		direction = directionArr.get((currentIndex + 1) % directionArr.size());
	}

	/**
	 * Moves the car one step forward
	 */
	public void moveForward() {
		switch (direction) {
		case 'N':
			y++;
			break;
		case 'E':
			x++;
			break;
		case 'S':
			y--;
			break;
		case 'W':
			x--;
			break;
		}
	}
}
