package planning.match.match;

public enum Tour {

    Qualification ("Qualification"),
    Quart_final ("Quart de finale"),
    Demi_finale ("Demi-finale"),
    Finale ("Finale");
    
    private String name = "";
    
    Tour(String name){
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
