import java.util.ArrayList;

public class SimpleFile {
	private String name;
	private Extension extension;
	private String content;
	private User owner;
	private ArrayList<Access> allowedUsers;
	private String path;
	private SimpleFolder parent;
	
	public SimpleFile(String name, Extension extension, String path, String content, SimpleFolder parent, User owner) {
		//TODO
		if(name==null||extension==null||path==null||content==null||parent==null||owner==null) 
			throw new IllegalArgumentException();
		this.name = name;
		this.extension = extension;
		this.content = content;
		this.owner = owner;
		this.path = path;
		this.parent = parent;
		this.allowedUsers = new ArrayList<Access>();
	}	
	//returns the path variable.
	public String getPath(){
		//TODO
		return this.path;
	}

	//return the parent folder of this file.
	public SimpleFolder getParent() {
		//TODO
		return this.parent;
	}

	//returns the name of the file.
	public String getName() {
		//TODO
		return this.name;
	}

	//returns the extension of the file.
	public Extension getExtension() {
		//TODO
		return this.extension;
	}

	//returns the content of the file.
	public String getContent() {
		//TODO
		return this.content;
	}

	//returns the owner user of this file.
	public User getOwner() {
		//TODO
		return this.owner;
	}

	//returns the list of allowed user of this file.
	public ArrayList<Access> getAllowedUsers() {
		//TODO
		return this.allowedUsers;
	}

	//adds a new access(user+accesstype pair) to the list of allowed user.
	public void addAllowedUser(Access newAllowedUser) {
		//TODO
		if(newAllowedUser==null) throw new IllegalArgumentException();
		this.allowedUsers.add(newAllowedUser);
	}
	
	//adds a list of the accesses to the list of allowed users.
	public void addAllowedUsers(ArrayList<Access> newAllowedUser) {
		//TODO
		if(newAllowedUser==null) throw new IllegalArgumentException();
		for(int i = 0; i < newAllowedUser.size(); i++){
			this.allowedUsers.add(newAllowedUser.get(i));
		}
	}
	
	
	// returns true if the user name is in allowedUsers.
	// Otherwise return false.
	public boolean containsAllowedUser(String name){
		//TODO
		if(name==null) throw new IllegalArgumentException();
		ArrayList<String> usernames = new ArrayList<String>();
		for(int i = 0; i < this.allowedUsers.size(); i++){
			usernames.add(this.allowedUsers.get(i).getUser().getName());
		}
		return usernames.contains(name);
	}
	
	
	//removes the file for all users.
	//If the user is owner of the file or the admin or the user has 'w' access,
	//then it is removed for everybody.
	public boolean removeFile(User removeUsr){
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
			this.owner.getFiles().remove(this);
			this.parent.getFiles().remove(this);
			removed = true;
		}
		return removed;
	}
	
	//returns the string representation of the file.
	@Override
	public String toString() {
		String retString = "";
		retString = name + "." + extension.name() + "\t" + owner.getName() + "\t" ;
		for(Access preAccess : allowedUsers){
			retString = retString + preAccess + " ";
		}
		retString = retString + "\t\"" + content + "\"";
		return retString;
	}
	
}
