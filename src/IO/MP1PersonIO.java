package IO;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

class Person implements Serializable {
	String name;
	int age;
	
	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return String.format("%s\t\t\t%d", this.name, this.age);
	}
}

public class MP1PersonIO {
	String fileName;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	static Scanner kbInput = new Scanner(System.in);

	public MP1PersonIO(String fileName) {
		// fill in the code
		// set fileName
		this.fileName = fileName;
		try {
			// construct ObjectOutputStream oos using fileName
			oos = new ObjectOutputStream(new FileOutputStream(this.fileName/*, true*/));
			
			// construct ObjectInputStream ois using fileName
			ois = new ObjectInputStream(new FileInputStream(this.fileName));
			// FIXME I'm having trouble here. The program will save information to the binary file,
			// however when I run the program a second time, opening the stream erases the file.
			// If I try to append the data, I get a corruption error that I am having trouble solving.
		} catch (IOException e) {
			System.out.println("Error.");
			e.printStackTrace();
		}
	}

	public void add(Person p) {
		if (oos != null) {
			try {
				oos.writeObject(p);
				System.out.println("Added " + p.name + ".");
				return;
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		System.out.println("Unable to add Person.");
	}

	public void display() {
		System.out.println("****************************");
		System.out.printf("%s\t\t\t%s\n", "Name","Age");
		System.out.println("----------------------------");
		int count = 0;
		
		try {
			while (true) {
				Person p = (Person) ois.readObject();
				System.out.println(p);
				count++;
			}
		} catch (EOFException e) {
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error.");
			e.printStackTrace();
		}
		
		if(count == 0) System.out.println("No data to display.");
		System.out.println("****************************");
	}

	public static void main(String[] args) {
		MP1PersonIO mp1 = new MP1PersonIO("person");
		try {
			int option = -1;
			while (option != 0) {
				System.out.println("Please choose an option:");
				System.out.println("0: quit");
				System.out.println("1: add");
				System.out.println("2: display");
				option = validInt("Selection: ", 0, 2);
				switch (option) {
				case 0:
					System.out.println("Bye");
					break;
				case 1:
					System.out.print("Enter a name: ");
					String name = kbInput.nextLine();
					int age = validInt("Enter an age: ");
					// invoke add
					mp1.add(new Person(name, age));
					break;
				case 2:
					// invoke display
					mp1.display();
				}

			} 
		} finally {
			try {
				// close ois
				if (mp1.ois != null) {
					mp1.ois.close();
				}
				// close oos
				if (mp1.oos != null) {
					mp1.oos.close();
				}

			} catch (IOException e) {
				System.out.println("Error.");
				e.printStackTrace();
			}

		}

	}

	public static int validInt(String prompt) {
		boolean valid = false;
		int number = -1;
		System.out.print(prompt);
		while (!valid) {
			if (kbInput.hasNextInt()) {
				number = kbInput.nextInt();
				if (number >= 0) {
					valid = true;
					break;
				}
			}
			System.out.println("Invalid entry. Try again");
			kbInput.nextLine();
		}
		kbInput.nextLine();
		return number;
	}
	
	public static int validInt(String prompt, int min, int max) {
		boolean valid = false;
		int number = -1;
		System.out.print(prompt);
		while (!valid) {
			if (kbInput.hasNextInt()) {
				number = kbInput.nextInt();
				if (number >= min && number <= max) {
					valid = true;
					break;
				}
			}
			kbInput.nextLine();
			System.out.println("Invalid entry. Try again");
		}
		kbInput.nextLine();
		return number;
	}
}