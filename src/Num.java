public class Num extends Factor implements Term {
    Num(String input) {
        super(input);
    }
    
    @Override
    public String derivative() {
        return "0";
    }
    
    @Override
    public void optimize() {
    
    }
    
}
