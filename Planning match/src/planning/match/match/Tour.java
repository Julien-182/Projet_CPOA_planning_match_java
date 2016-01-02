package planning.match.match;

public enum Tour {

    Qualification ("Qualifications"),
    Quart_final ("Quart de finale"),
    Demi_finale ("Demi-finale"),
    Finale ("Finale");
    
    private String name = "";
    
    Tour(String name){
        this.name = name;
    }
    
    public String toString(){
        return name;
    }
}
