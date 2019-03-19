public interface Term {
    String derivative() throws Exception;
    
    String getData();
    
    void optimize();
}
