package listscrubber

class SupressedEmail {

	String email
	String md5
	
    static constraints = {
        email(unique:true)
    }
}
