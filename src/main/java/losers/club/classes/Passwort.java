package losers.club.classes;

public class Passwort {

    /**
     *
     *  Passwort Class
     *  Erstelle ein Passwort und lasse es verschlüsseln.
     *
     */

    private final String password;

    public Passwort(String password){
        this.password = cryptPassword(password);
    }

    private  String cryptPassword(String cryptPassword){
        return null;
    }

    private String unCryptPassword(String cryptPassword){

        return null;
    }

    public String getPassword(){
        return unCryptPassword(this.password);
    }

    public String getCryptPassword(){
        return this.password;
    }

}
