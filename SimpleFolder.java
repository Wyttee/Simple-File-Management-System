import java.util.ArrayList;

public class SimpleFolder {

	private String name;
	private String path;//absolute path of the folder.
	private SimpleFolder parent;
	private User owner;
	private ArrayList<SimpleFolder> subFolders;
	private ArrayList<SimpleFile> files;
	private ArrayList<Access> allowedUsers;

	public SimpleFolder(String name, String path, SimpleFolder parent, User owner) {
		//TODO
		if(name==null||path==null||parent==null||owner==null) 
			throw new IllegalArgumentException();
		this.name = name;
		this.path = path;
		this.parent = parent;
		this.owner = owner;
		this.files = new ArrayList<SimpleFile>();
		this.allowedUsers = new ArrayList<Access>();
		this.subFolders = new ArrayList<SimpleFolder>();
	}
	
	
	//checks if user - "name" is allowed to access this folder or not. 
	//If yes, return true, otherwise false.
	public boolean containsAllowedUser(String name){
		//TODO
		if(name==null) throw new IllegalArgumentException();
		for(int i = 0; i < this.allowedUsers.size(); i++){
			if(this.allowedUsers.get(i).equals(name))
				return true;
		}
		return false;
	}

	//checks if this folder contains the child folder identified by 'fname'.
	// If it does contain then it returns the folder otherwise returns null.
	public SimpleFolder getSubFolder(String fname){
		//TODO
		if(fname==null) throw new IllegalArgumentException();
		for(int i = 0; i < this.subFolders.size(); i++){
			if(this.subFolders.get(i).getName().equals(fname))
				return subFolders.get(i);
		}
		return null;
	}


	//checks if this folder contains the child file identified by "fname".
	// If it does contain, return File otherwise null.
	public SimpleFile getFile(String fname){
		//TODO
		if(fname==null) throw new IllegalArgumentException();
		for(int i = 0; i < this.files.size(); i++){
			if(this.files.get(i).getName().equals(fname))
				return this.files.get(i);
		}
		return null;
	}


	//returns the owner of the folder.
	public User getOwner() {
		//TODO
		return this.owner;
	}

	//returns the name of the folder.
	public String getName() {
		//TODO
		return this.name;
	}

	//returns the path of this folder.
	public String getPath() {
		//TODO
		return this.path;
	}

	//returns the Parent folder of this folder.
	public SimpleFolder getParent() {
		//TODO
		return this.parent;
	}

	//returns the list of all folders contained in this folder.
	public ArrayList<SimpleFolder> getSubFolders() {
		//TODO
		return this.subFolders;
	}

	//adds a folder into this folder.
	public void addSubFolder(SimpleFolder subFolder) {
		//TODO
		if(subFolder==null) throw new IllegalArgumentException();
		this.subFolders.add(subFolder);
	}

	//adds a folder into this folder.
	public void addSubFolder(String name, SimpleFolder parent, User owner){
		//TODO
		if(name==null||parent==null||owner==null) throw new IllegalArgumentException();
		this.subFolders.add(new SimpleFolder(name, this.path, parent, owner));
	}

	//returns the list of files contained in this folder.
	public ArrayList<SimpleFile> getFiles() {
		//TODO
		return this.files;
	}

	//add the file to the list of files contained in this folder.
	public void addFile(SimpleFile file) {
		//TODO
		if(file==null) throw new IllegalArgumentException();
		this.files.add(file);
	}

	//returns the list of allowed user to this folder.
	public ArrayList<Access> getAllowedUsers() {
		//TODO
		return this.allowedUsers;
	}

	//adds another user to the list of allowed user of this folder.
	public void  addAllowedUser(Access allowedUser) {
		//TODO
		if(allowedUser==null) throw new IllegalArgumentException();
		this.allowedUsers.add(allowedUser);
	}

	//adds a list of allowed user to this folder.
	public void addAllowedUsers(ArrayList<Access> allowedUser) {
		//TODO
		if(allowedUser==null) throw new IllegalArgumentException();
		for(int i = 0; i < allowedUser.size(); i++){
			this.allowedUsers.add(allowedUser.get(i));
		}
	}

	//If the user is owner of this folder or the user is admin or the user has 'w' privilege
	//, then delete this folder along with all its content.
	public boolean removeFolder(User removeUsr){
		//TODO
		if(removeUsr==null) throw new IllegalArgumentException();
		boolean removed = false;
		boolean isW = false;
		for(int i = 0; i < this.allowedUsers.size(); i++){
			if(this.allowedUsers.get(i).getUser().equals(removeUsr) && this.allowedUsers.get(i).getAccessType()=='w'){
				isW = true;
			}
		}
		if(this.owner.equals(removeUsr)||isW||removeUsr.getName().equals("admin")){
			this.parent.getSubFolders().remove(this);
			this.owner.getFolders().remove(this);
			removed = true;
		}
		return removed;
	}


	//returns the string representation of the Folder object.
	@Override
	public String toString() {
		String retString = "";
		retString = path + "/" + name + "\t" + owner.getName() + "\t";
		for(Access preAccess: allowedUsers){
			retString = retString + preAccess + " ";
		}

		retString = retString + "\nFILES:\n";

		for(int i=0;i<files.size();i++){
			retString = retString + files.get(i);
			if(i != files.size()-1)
				retString = retString + "\n";

		}				
		return retString;
	}


}
