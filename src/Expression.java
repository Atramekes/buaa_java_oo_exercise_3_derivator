import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression implements Term {
    private String data;
    
    Expression(String input) {
        setData(input);
    }
    
    private void setData(String string) {
        data = string;
    }
    
    private String singleDerivative(String up) throws Exception {
        String unprocessed = up.replace("&", "+")
                .replace("|", "-")
                .replace("{", "(")
                .replace("}", ")");
        String ans = Character.toString(unprocessed.toCharArray()[0]);
        String d = new Item(unprocessed.replaceFirst("[+-]", "")).derivative();
        if (d.matches("\\d+")) {
            return ans + d;
        }
        return ans + "(" + d + ")";
    }
    
    @Override
    public String derivative() throws Exception {
        String unprocessed = getData();
        String pre = "[(][^()]*[)]";
        Pattern prePattern = Pattern.compile(pre);
        Matcher preM = prePattern.matcher(unprocessed);
        while (preM.find()) {
            unprocessed = unprocessed.replace(
                    preM.group(), preM.group().replace("+", "&")
                            .replace("-", "|")
                            .replace("(", "{")
                            .replace(")", "}"));
            preM = prePattern.matcher(unprocessed);
        }
        String regex = "[+-].*?[^*(][+-]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(unprocessed);
        String ans = "";
        if (matcher.find()) {
            String little = matcher.group()
                    .replace("&", "+").replace("|", "-")
                    .replace("{", "(").replace("}", ")");
            String plus1 = "^[+-]";
            Pattern p1 = Pattern.compile(plus1);
            Matcher m1 = p1.matcher(little);
            m1.find();
            ans += m1.group() + "(";
            String plus2 = "[+-]$";
            Pattern p2 = Pattern.compile(plus2);
            Matcher m2 = p2.matcher(little);
            m2.find();
            String big = unprocessed.replaceFirst(regex, "")
                    .replace("&", "+").replace("|", "-")
                    .replace("{", "(").replace("}", ")");
            big = m2.group() + big;
            ans += new Item(little.replaceFirst(plus1, "")
                    .replaceFirst(plus2, "")).derivative() + ")";
            ans += new Expression(big).derivative();
            return ans;
        }
        return singleDerivative(unprocessed);
    }
    
    @Override
    public void optimize() {
        if (data.matches("^[(].*[)]$")) {
            String simplified;
            simplified = data.replaceAll("[)]$", "").replaceAll("^[(]", "");
            if (!simplified.matches("[+-].*")) {
                simplified = "+" + simplified;
            }
            if (!simplified.matches("[^(]*[)].*")) {
                setData(simplified);
            }
        }
    }
    
    @Override
    public String getData() {
        optimize();
        return data;
    }
}
