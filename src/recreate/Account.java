package recreate;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Account {
	private ArrayList<User> account = new ArrayList<User>();
	private User currentUser = null;
	private File accountFile = new File("./src/db/players.txt");
	
	public Account(){ }
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void logout() {
		currentUser = null;
	}
	
	public boolean accountAuth(String name, String pass) {
		for (User user : account) {
			if(user.getName().equals(name) && user.getPass().equals(pass)) {
				currentUser = new User(user.getName(), user.getPass(), user.getScore());
				System.out.println("[*] welcome " + name);
				return true;
			}
		}
		System.out.println("[!] account doesn't exists");
		return false;
	}
	
	public String[] deserialize(String rawLine) {
		return rawLine.split("#");
	}
	
	public void readAccount() {
		account.clear();
		
		try {
			Scanner scanAcc = new Scanner(accountFile);
			
			while(scanAcc.hasNextLine()) {
				String[] result = deserialize(scanAcc.nextLine());
				
				if(result.length >= 3) {
					account.add(new User(result[0], result[1], Integer.parseInt(result[2])));
				}
			}
			
			scanAcc.close();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[!] fail to read account file");
		}
	}
	
	public void displayRank() {
		Comparator<User> UserComparator = new Comparator<>() {
			@Override
			public int compare(User a, User b) {
				return b.getScore() - a.getScore();
			}
		};
		
		Collections.sort(account, UserComparator);
		
		for (User user : account) {
			System.out.printf("%-20s - %-10d\n", user.getName(), user.getScore());
		}
	}
	
	public void writeAccount(User writeIt) {
		try {
			if(!accountFile.exists()) {
				accountFile.createNewFile();
			}
		} catch (Exception e) {
			System.out.println("[!] failed to create account file");
		}
		
		try {
			FileWriter fw = new FileWriter(accountFile, true);
			fw.write(writeIt.getName() + "#"+writeIt.getPass() + "#" + writeIt.getScore() + "\n");
			fw.close();
		} catch (Exception e) {
			System.out.println("[!] failed to write account to file");
		}
	}
	
	public void updateCurrUser() {
		if(currentUser != null) {
			for (User user : account) {
				if(user.getName().equals(currentUser.getName())
						&& user.getPass().equals(currentUser.getPass())) {
					user.setScore(currentUser.getScore());
				}
			}

			try {
				FileWriter fw = new FileWriter(accountFile, false);
				for (User user : account) {
					fw.write(user.getName() + "#"+user.getPass() + "#" + user.getScore() + "\n");					
				}
				fw.close();
			} catch (Exception e) {
				System.out.println("[!] failed to write account to file");
			}
		}
	}
}
