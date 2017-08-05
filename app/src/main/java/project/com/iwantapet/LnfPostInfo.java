package project.com.iwantapet;

/**
 * Created by prayog on 8/5/17.
 */

public class LnfPostInfo {
    String status;
    String type;
    String name;
    String poster;
    String description;
    String key;
    public LnfPostInfo(){}
    public  LnfPostInfo(String status,String type,String name,String poster,String description,String key){
        this.status=status;
        this.type=type;
        this.name=name;
        this.poster=poster;
        this.description=description;
        this.key=key;
    }
}
