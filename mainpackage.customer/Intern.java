package mainpackage.customer;

import org.springframework.stereotype.Component;

@Component
public class Intern {
	
	private String nameAndSurname;

	public String getNameAndSurname() {
		return nameAndSurname;
	}
	
	public void setNameAndSurname(String nameAndSurname) {
		this.nameAndSurname = nameAndSurname;
	}
	
	public Intern() {
		
	}
	
	public Intern(String nameAndSurname) {
		setNameAndSurname(nameAndSurname);
	}
	
}
