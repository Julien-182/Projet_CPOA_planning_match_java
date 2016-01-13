package match;

public enum Date_Match {

    jour1 ("2016-01-31"),
    jour2 ("2016-02-01"),
    jour3 ("2016-02-02"),
    jour4 ("2016-02-03"),
    jour5 ("2016-02-04"),
    jour6 ("2016-02-05"),
    jour7 ("2016-02-06");
    
    private String name = "";
    
    Date_Match(String name){
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
