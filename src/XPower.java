import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XPower extends Factor implements Term {
    XPower(String input) {
        super(input);
    }
    
    @Override
    public String derivative() throws Exception {
        String string = getData();
        if (string.matches("1")) {
            return "0";
        } else if (string.matches("x")) {
            return "1";
        }
        String regex = "[+-]?\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        BigInteger coefficient;
        BigInteger power;
        if (matcher.find()) {
            coefficient = new BigInteger(matcher.group());
            power = new BigInteger(matcher.group()).subtract(BigInteger.ONE);
            if (power.subtract(new BigInteger("10000")).signum() == 1) {
                throw new Exception();
            }
        } else {
            coefficient = BigInteger.ZERO;
            power = BigInteger.ZERO;
        }
        String ans = "";
        if (!coefficient.equals(BigInteger.ONE)) {
            ans = coefficient.toString() + "*";
        }
        ans += "x";
        if (!power.equals(BigInteger.ONE)) {
            ans += "^" + power.toString();
        }
        return ans;
    }
    
    @Override
    public void optimize() {
        if (super.getData().matches("x\\^0*1")) {
            setData("x");
        } else if (super.getData().matches("x\\^0*")) {
            setData("1");
        }
    }
    
    @Override
    public String getData() {
        optimize();
        return super.getData();
    }
}
