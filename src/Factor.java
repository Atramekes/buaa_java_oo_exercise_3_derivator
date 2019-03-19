abstract class Factor implements Term {
    private String data;
    
    Factor(String input) {
        setData(input);
    }
    
    void setData(String newData) {
        data = newData;
    }
    
    public String getData() {
        return data;
    }
}
