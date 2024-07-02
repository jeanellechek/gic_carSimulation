package gic_carSimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarSimulation {
	private int width;
	private int height;
	private List<Car> cars;

	public CarSimulation(int width, int height) {
		this.width = width;
		this.height = height;
		this.cars = new ArrayList<>();
	}

	public void addCar(Car car) {
		cars.add(car);
	}

	/**
	 * Prints the list of cars added
	 */

	public void displayCars() {
		System.out.println("Your current list of cars are:");
		for (Car car : cars) {
			System.out.println(
					"- " + car.name + ", (" + car.x + "," + car.y + ") " + car.direction + ", " + car.commands);
		}
	}
	
	public List<Car> getCars(){
		return cars;
	}

	/**
	 * Run the simulation based on commands to get the final position and facing of
	 * the car
	 */
	public void run() {
		boolean isCollided = false;
		int collisionStep = -1;
		Car collidingCarA = null;
		Car collidingCarB = null;

		//processing the command
		for (int step = 0; step < getCommandLength(); step++) {
			for (Car car : cars) {
				if (step < car.commands.length()) {
					char command = car.commands.charAt(step);
					switch (command) {
					case 'L':
						car.turnLeft();
						break;
					case 'R':
						car.turnRight();
						break;
					case 'F':
						car.moveForward();
						withinBound(car);
						break;
					}
				}
			}

			for (int i = 0; i < cars.size(); i++) {
				for (int j = i + 1; j < cars.size(); j++) {
					Car carA = cars.get(i);
					Car carB = cars.get(j);
					if (carA.x == carB.x && carA.y == carB.y) {
						isCollided = true;
						collisionStep = step + 1;
						collidingCarA = carA;
						collidingCarB = carB;
						break;
					}
				}
				if (isCollided)
					break;
			}
			if (isCollided)
				break;
		}

		System.out.println("After the simulation, the result is:");
		if (isCollided) {
			System.out.println(collidingCarA.name + ", collides with " + collidingCarB.name + " at (" + collidingCarA.x
					+ "," + collidingCarA.y + ") at step " + collisionStep);
			System.out.println(collidingCarB.name + ", collides with " + collidingCarA.name + " at (" + collidingCarB.x
					+ "," + collidingCarB.y + ") at step " + collisionStep);
		} else {
			for (Car car : cars) {
				System.out.println(car.name + ", (" + car.x + "," + car.y + ") " + car.direction);
			}
		}
	}

	private int getCommandLength() {
		int maxLength = 0;
		for (Car car : cars) {
			if (car.commands.length() > maxLength) {
				maxLength = car.commands.length();
			}
		}
		return maxLength;
	}

	/**
	 * Check and ensure car is within bound
	 * 
	 * @param car
	 */

	private void withinBound(Car car) {
		if (car.x < 0)
			car.x = 0;
		if (car.x >= width)
			car.x = width - 1;
		if (car.y < 0)
			car.y = 0;
		if (car.y >= height)
			car.y = height - 1;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to Auto Driving Car Simulation!");
		System.out.print("Please enter the width and height of the simulation field in x y format: ");
		int width = scanner.nextInt();
		int height = scanner.nextInt();

		CarSimulation simulation = new CarSimulation(width, height);

		while (true) {
			System.out.println("Please choose from the following options:");
			System.out.println("[1] Add a car to field");
			System.out.println("[2] Run simulation");
			int choice = scanner.nextInt();

			if (choice == 1) {
				System.out.print("Please enter the name of the car: ");
				String name = scanner.next();
				System.out.print("Please enter initial position of car " + name + " in x y Direction format: ");
				int x = scanner.nextInt();
				int y = scanner.nextInt();
				char direction = scanner.next().charAt(0);

				System.out.print("Please enter the commands for car " + name + ": ");
				String commands = scanner.next();

				Car car = new Car(name, x, y, direction, commands);
				simulation.addCar(car);

				simulation.displayCars();
			} else if (choice == 2) {
				simulation.run();

				System.out.println("Please choose from the following options:");
				System.out.println("[1] Start over");
				System.out.println("[2] Exit");
				int postSimulationChoice = scanner.nextInt();
				if (postSimulationChoice == 1) {
					simulation.run();
					break;
				} else if (postSimulationChoice == 2) {
					System.out.println("Thank you for running the simulation. Goodbye!");
					scanner.close();
					return;
				}
			}
		}
	}
}
