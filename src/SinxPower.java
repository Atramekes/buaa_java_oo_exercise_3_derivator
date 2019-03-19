import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SinxPower extends Factor implements Term {
    SinxPower(String input) {
        super(input);
    }
    
    @Override
    public String derivative() throws Exception {
        String data = getData();
        if (data.matches("1")) {
            return "0";
        } else if (data.matches(".*\\^\\d+")) {
            String ans;
            String regex = "\\d+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(data);
            matcher.find();
            BigInteger coefficient = new BigInteger(matcher.group());
            BigInteger power = new BigInteger(matcher.group())
                    .subtract(BigInteger.ONE);
            if (power.subtract(new BigInteger("10000")).signum() == 1) {
                throw new Exception();
            }
            ans = coefficient + "*cos(";
            String content;
            content = data.replaceFirst("sin[(]", "")
                    .replaceFirst("[)]\\^\\d+$", "");
            ans += content + ")^" + power + "*(";
            ans += new Item(content).derivative();
            ans += ")";
            return ans;
        } else {
            String ans;
            ans = data.replaceFirst("sin", "cos");
            ans += "*(";
            String content;
            content = data.replaceFirst("sin[(]", "").replaceFirst("[)]$", "");
            ans += new Item(content).derivative();
            ans += ")";
            return ans;
        }
    }
    
    @Override
    public void optimize() {
        if (super.getData().matches(".*\\^0*1")) {
            setData(super.getData().replaceAll("\\^0*1", ""));
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
