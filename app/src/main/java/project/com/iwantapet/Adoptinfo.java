package project.com.iwantapet;

/**
 * Created by prayog on 7/1/17.
 */

public class Adoptinfo {
    String pemail;
    String type;
    String name;
    String age;
    String description;
    String key;
    public Adoptinfo(){

    }
    public Adoptinfo(String pemail,String type,String name,String age,String description,String key){
        this.pemail=pemail;
        this.type=type;
        this.name=name;
        this.age=age;
        this.description=description;
        this.key=key;
    }
}
