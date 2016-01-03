package planning.match.match;


public enum Creneau {

    am_8 ("8am"),
    am_11 ("11am"),
    pm_3 ("3pm"),
    pm_6 ("6pm"),
    pm_9 ("9pm");    
    
    private String name = "";
    
    Creneau(String name){
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
