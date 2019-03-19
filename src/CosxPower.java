public class CosxPower extends Factor implements Term {
    CosxPower(String input) {
        super(input);
    }
    
    @Override
    public String derivative() {
        return null;
    }
    
    @Override
    public void optimize() {
        if (super.getData().matches(".*\\^0*1")) {
            setData(getData().replaceAll("\\^0*1", ""));
        } else if (super.getData().matches(".*\\^0*")) {
            setData("1");
        }
    }
    
    @Override
    public String getData() {
        optimize();
        return super.getData();
    }
}
