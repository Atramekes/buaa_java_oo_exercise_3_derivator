public class Item implements Term {
    private String data;
    
    Item(String input) {
        setData(input);
    }
    
    private void setData(String string) {
        data = string;
    }
    
    @Override
    public String derivative() {
        return null;
    }
    
    @Override
    public void optimize() {
    
    }
    
    @Override
    public String getData() {
        optimize();
        return data;
    }
}
