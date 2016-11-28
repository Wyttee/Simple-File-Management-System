
public class Access {
	
	private User user;
	private char accessType;
	
	public Access(User user, char accessType) {
		//TODO
		this.user = user;
		this.accessType = accessType;
	}

	public User getUser() {
		//TODO
		return this.user;
	}

	public char getAccessType() {
		//TODO
		return this.accessType;
	}

	public void setAccessType(char accessType) {
		//TODO
		if(accessType=='r'||accessType=='w')
			this.accessType = accessType;
		else throw new IllegalArgumentException();
	}
	
	@Override
	public String toString() {
		return (user.getName() + ":" + accessType);
	}
	
}
