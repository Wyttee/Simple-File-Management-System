import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SimpleFileSystem {

	SimpleFolder root;
	ArrayList<User> users;
	SimpleFolder currLoc;
	User currUser;
	User admin;

	//constructor
	public SimpleFileSystem(SimpleFolder root, ArrayList<User> users) {
		//TODO
		if(root==null||users==null) throw new IllegalArgumentException();
		this.root = root;
		this.users = users;
		this.currUser = this.root.getOwner();
		this.currLoc = this.root;
		this.admin = this.root.getOwner();
	}

	// resets current user to admin and current location to root
	public void reset(){
		//TODO
		this.currUser = this.root.getOwner();
		this.currLoc = this.root;
	}


	//gets currUser.
	public User getCurrUser() {
		//TODO
		return this.currUser;
	}

	//sets the current user to the user with name passed in the argument.
	public boolean setCurrentUser(String name){
		//TODO
		if(name==null) throw new IllegalArgumentException();
		boolean set = false;
		for(int i = 0; i < this.users.size(); i++){
			if(this.users.get(i).getName().equals(name)){
				this.currUser = this.users.get(i);
				set = true;
				this.currLoc = this.root;
			}
		}
		return set;
	}


	//checks if the user is contained in the existing users list or not.
	public User containsUser(String name){
		//TODO
		if(name==null) throw new IllegalArgumentException();
		for(int i = 0; i < this.users.size(); i++){
			if(this.users.get(i).getName().equals(name)){
				return this.users.get(i);
			}
		}
		return null;
	}


	//checks whether curr location contains any file/folder with name name = fname
	public boolean containsFileFolder(String fname){
		//TODO
		if(fname==null) throw new IllegalArgumentException();
		if(this.currLoc.getSubFolder(fname)!=null||this.currLoc.getFile(fname)!=null) return true;
		else return false;
	}



	//changes the current location. If successful returns true, false otherwise.
	public boolean moveLoc(String argument){
		//TODO
		if(argument==null) throw new IllegalArgumentException();
		String[] argts = argument.split("/");
		if(argts[0].equals("")){
			this.currLoc = this.root;
			for(int i = 2; i < argts.length; i++){
				if(this.currLoc.getSubFolder(argts[i])==null) return false;
				else this.currLoc = this.currLoc.getSubFolder(argts[i]);
			}
		}
		else{
			if(argument.charAt(argument.length()-1)=='/'){
				for(int i = 0; i < argts.length; i++){
					if(this.currLoc.getParent()==null) return false;
					else this.currLoc = this.currLoc.getParent();
				}
			}
			else{
				for(int i = 0; i < argts.length; i++){
					if(this.currLoc.getSubFolder(argts[i])==null) return false;
					else this.currLoc = this.currLoc.getSubFolder(argts[i]);
				}
			}
		}
		return true;
	}


	//returns the currentlocation.path + currentlocation.name.
	public String getPWD(){
		//TODO
		return this.currLoc.getPath() + "/" + this.currLoc.getName();
	}//return of getPWD method


	//deletes the folder/file identified by the 'name'
	public boolean remove(String name){
		//TODO
		if(name==null) throw new IllegalArgumentException();
		boolean dltd = false;
		for(int i = 0; i < currUser.getFiles().size(); i++){
			if(currUser.getFiles().get(i).getName().equals(name)){
				currUser.removeFile(currUser.getFiles().get(i));
				dltd = true;
			}
		}
		for(int i = 0; i < currUser.getFolders().size(); i++){
			if(currUser.getFolders().get(i).getName().equals(name)){
				currUser.removeFolder(currUser.getFolders().get(i));
				dltd = true;
			}
		}
		return dltd;
	}


	//Gives the access 'permission' of the file/folder fname to the user if the 
	//current user is the owner of the fname. If succeed, return true, otherwise false.
	public boolean addUser(String fname, String username, char permission){
		//TODO
		if(fname==null||username==null) throw new IllegalArgumentException();
		boolean added = false;
		User theUser = null;
		for(int i = 0; i < this.users.size(); i++){
			if(this.users.get(i).getName().equals(username)) theUser = users.get(i);
		}
		if(theUser!=null && this.containsFileFolder(fname)){
			if(this.currLoc.getFile(fname).getOwner().equals(theUser)){
				this.currLoc.addAllowedUser(new Access(theUser, permission));
				added = true;
			}
			else if(this.currLoc.getSubFolder(fname).getOwner().equals(theUser)){
				this.currLoc.addAllowedUser(new Access(theUser, permission));
				added = true;
			}
		}
		return added;
	}


	//displays the user info in the specified format.
	public boolean printUsersInfo(){
		//TODO
		boolean displayed = false;
		if(this.currUser.getName().equals("admin")){
			for(int i = 0; i < this.users.size(); i++)
				System.out.println(this.users.get(i).toString());
			displayed = true;
		}
		return displayed;
	}



	//makes a new folder under the current folder with owner = current user.
	public void mkdir(String name){
		//TODO
		if(name==null) throw new IllegalArgumentException();
		this.currLoc.addSubFolder(name, this.currLoc, this.currUser);
		this.currLoc.getSubFolder(name).addAllowedUser(new Access(this.currUser, 'w'));
		if(!this.currLoc.getSubFolder(name).containsAllowedUser("admin")) 
			this.currLoc.getSubFolder(name).addAllowedUser(new Access(this.admin, 'w'));
	}


	//makes a new file under the current folder with owner = current user.
	public void addFile(String filename, String fileContent){
		//TODO
		if(filename==null||fileContent==null) throw new IllegalArgumentException();
		Extension theExtension = null;
		if(filename.endsWith("txt")) theExtension = Extension.txt;
		else if(filename.endsWith("doc")) theExtension = Extension.doc;
		else if(filename.endsWith("rtf")) theExtension = Extension.rtf;
		if(!theExtension.equals(null)){
			SimpleFile added = new SimpleFile(filename, theExtension, this.getPWD(), fileContent, this.currLoc, this.currUser);
			added.addAllowedUser(new Access(this.currUser, 'w'));
			if(!added.containsAllowedUser("admin")) added.addAllowedUser(new Access(this.admin, 'w'));
			this.currLoc.addFile(added);
		}
	}


	//prints all the folders and files under the current user for which user has access.
	public void printAll(){
		for(SimpleFile f : currLoc.getFiles()){
			if(f.containsAllowedUser(currUser.getName()))
			{
				System.out.print(f.getName() + "." + f.getExtension().toString() + " : " + f.getOwner().getName() + " : ");
				for(int i =0; i<f.getAllowedUsers().size(); i++){
					Access a = f.getAllowedUsers().get(i);
					System.out.print("("+a.getUser().getName() + "," + a.getAccessType() + ")");
					if(i<f.getAllowedUsers().size()-1){
						System.out.print(",");
					}
				}
				System.out.println();
			}
		}
		for(SimpleFolder f: currLoc.getSubFolders()){
			if(f.containsAllowedUser(currUser.getName()))
			{
				System.out.print(f.getName() +" : " + f.getOwner().getName() + " : ");
				for(int i = 0; i<f.getAllowedUsers().size(); i++){
					Access a = f.getAllowedUsers().get(i);
					System.out.print("("+a.getUser().getName() + "," + a.getAccessType() + ")");
					if(i<f.getAllowedUsers().size()-1){
						System.out.print(",");
					}
				}
				System.out.println();
			}
		}


	}

}
