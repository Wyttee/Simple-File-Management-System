import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class FileSystemMain {
	static Scanner input;
	static Scanner content;
	static final User admin = new User("admin");
	public static void main(String[] args) {
		if(args.length != 1){
			System.out.println("Usage: java FileSystemMain FileName");
			System.exit(0);
		}
		String fileName = args[0];
		File inputFile = new File(fileName);
		try {
			input = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String rootName = input.nextLine();
		SimpleFolder root = new SimpleFolder(rootName, rootName, null, admin);
		ArrayList<User> theUsers = new ArrayList<User>();
		ArrayList<Access> allowedUsers = new ArrayList<Access>();
		String[] nameList = input.nextLine().split(", ");
		for(int i = 0; i < nameList.length; i++){
			theUsers.add(new User(nameList[i]));
		}
		for(int i = 0; i < nameList.length; i++){
			allowedUsers.add(new Access(theUsers.get(i), 'w'));
		}
		SimpleFileSystem theSystem = new SimpleFileSystem(root, theUsers);
		ArrayList<String> thePaths = new ArrayList<String>();
		while(input.hasNextLine()){
			thePaths.add(input.nextLine());
		}
		input.close();
		for(int i = 0; i < thePaths.size(); i++){
			String[] steps = thePaths.get(i).split("/");
			if(steps[0].equals(rootName)){
				for(int j = 1; j < steps.length; j++){
					String theStep = steps[j];
					if(!theSystem.containsFileFolder(theStep)){
						if(theStep.contains(".")){
							input = new Scanner(theStep);
							theSystem.addFile(input.next(), input.nextLine().substring(1));
							input.close();
						}
						else{
							theSystem.mkdir(theStep);
						}
					}
					theSystem.moveLoc(theStep);
				}
			}
			theSystem.reset();
		}
		input = new Scanner(System.in);
		boolean continueInput = true;
		while(continueInput){
			System.out.println(theSystem.getCurrUser().getName() + "@CS367$ ");
			String cmd = input.nextLine();
			String[] cmds = cmd.split(" ");
			if(cmds[0].equals("reset")){
				if(cmds.length!=1) System.out.println("No Argument Needed");
				theSystem.reset();
				System.out.println("Reset done");
			}
			else if(cmds[0].equals("pwd")){
				if(cmds.length!=1) System.out.println("No Argument Needed");
				System.out.println(theSystem.getPWD());
			}
			else if(cmds[0].equals("ls")){
				if(cmds.length!=1) System.out.println("No Argument Needed");
				theSystem.printAll();
			}
			else if(cmds[0].equals("u")){
				if(cmds.length!=2) System.out.println("One Argument Needed");
				else if(!theSystem.setCurrentUser(cmds[1])) System.out.println("user " + cmds[1] + " does not exist");
			}
			else if(cmds[0].equals("uinfo")){
				if(cmds.length!=1) System.out.println("No Argument Needed");
				if(theSystem.printUsersInfo());
				else System.out.println("Insufficient privilege");
			}
			else if(cmds[0].equals("cd")){
				if(cmds.length!=2) System.out.println("One Argument Needed");
				else if(theSystem.moveLoc(cmds[1]));
				else System.out.println("Invalid location passed");
			}
			else if(cmds[0].equals("rm")){
				if(cmds.length!=1) System.out.println("One Argument Needed");
				else if(theSystem.containsFileFolder(cmds[1])) System.out.println("Invalid name");
				else if(theSystem.remove(cmds[1])) System.out.println(cmds[1] + " removed");
				else System.out.println("Insufficient privilege");
			}
			else if(cmds[0].equals("mkdir")){
				if(cmds.length!=2) System.out.println("One Argument Needed");
				theSystem.mkdir(cmds[1]);
				if(theSystem.containsFileFolder(cmds[1])) System.out.println(cmds[1] + " added");
			}
			else if(cmds[0].equals("mkfile")){
				if(cmds.length != 2 && cmds.length != 3) System.out.println("One Argument Needed");
				content = new Scanner(cmd);
				content.next();
				String theFileName = content.next();
				String theFileContent = content.nextLine();
				if(!theFileName.contains(".")||
						((!theFileName.endsWith("txt"))&&(!theFileName.endsWith("doc")&&(!theFileName.endsWith("rtf")))))
					System.out.println("Invalid filename");
				if(theFileContent==null) theFileContent = "";
				theSystem.addFile(theFileName, theFileContent);
			}
			else if(cmds[0].equals("sh")){
				if(cmds.length != 4) System.out.println("Three Arguments Needed");
				String fname = cmds[1];
				String username = cmds[2];
				String accesstype = cmds[3];
				boolean invalid = false;
				if(!theSystem.containsFileFolder(fname)){
					System.out.println("Invalid file/folder name");
					invalid = true;
				}
				if(theSystem.containsUser(username)==null){
					System.out.println("Invalid user");
					invalid = true;
				}
				if(accesstype!="w"&&accesstype!="r"){
					System.out.println("Invalid permission type");
					invalid = true;
				}
				if(!invalid){
					if(theSystem.addUser(fname, username, accesstype.toCharArray()[0])) System.out.println("Privilege granted");
					else System.out.println("Insufficient privilege");
				}
			}
			else if(cmds[0].equals("x")) System.exit(0);
			else System.out.println("invalid command");
		}
	}	


}
