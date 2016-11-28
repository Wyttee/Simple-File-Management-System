import java.util.ArrayList;

public class User {
	
	private String name; //name of the user.
	private ArrayList<SimpleFile> files;//list of files owned/created by user
	private ArrayList<SimpleFolder> folders;//list of folder owned by user.

	public User(String name) {
		//TODO
		if(name==null) throw new IllegalArgumentException();
		this.name = name;
		this.files = new ArrayList<SimpleFile>();
		this.folders = new ArrayList<SimpleFolder>();
	}
	
	@Override
	public boolean equals(Object obj) {
		//TODO		
		if(obj==null) throw new IllegalArgumentException();
		return obj.equals(this.name);
	}

	//returns the name of the user.
	public String getName() {
		//TODO
		return this.name;
	}

	//returns the list of files owned by the user.
	public ArrayList<SimpleFile> getFiles() {
		//TODO
		return this.files;
	}

	//adds the file to the list of files owned by the user.
	public void addFile(SimpleFile newfile) {
		//TODO
		if(newfile==null) throw new IllegalArgumentException();
		this.files.add(newfile);
	} 
	
	//removes the file from the list of owned files of the user.
	public boolean removeFile(SimpleFile rmFile){
		//TODO
		if(rmFile==null) throw new IllegalArgumentException();
		return this.files.remove(rmFile);
	}

	//returns the list of folders owned by the user.
	public ArrayList<SimpleFolder> getFolders() {
		//TODO
		return this.folders;
	}

	//adds the folder to the list of folders owned by the user.
	public void addFolder(SimpleFolder newFolder) {
		//TODO
		if(newFolder==null) throw new IllegalArgumentException();
		this.folders.add(newFolder);
	}

	//removes the folder from the list of folders owned by the user.
	public boolean removeFolder(SimpleFolder rmFolder){
		//TODO
		if(rmFolder==null) throw new IllegalArgumentException();
		return this.folders.remove(rmFolder);
	}
	
	//returns the string representation of the user object.
	@Override
	public String toString() {
		String retType = name + "\n";
		retType = retType + "Folders owned :\n";
		for(SimpleFolder preFolder : folders){
			retType = retType + "\t" + preFolder.getPath() + "/" + preFolder.getName() + "\n";
		}
		retType = retType + "\nFiles owned :\n"; 
		for(SimpleFile preFile : files){
			retType = retType + "\t" + preFile.getPath() + "/" + preFile.getName() + "." + preFile.getExtension().toString() + "\n";
		}
		return retType;
	}
	
	
	
}
