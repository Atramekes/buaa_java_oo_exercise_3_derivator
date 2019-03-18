public class Poly implements Term {
    private String data;
    
    Poly(String input) {
        data = input;
    }
    
    @Override
    public String derivative() {
        return null;
    }
    
    @Override
    public void optimize() {
    
    }
    
    @Override
    public String getString() {
        return data;
    }
}
