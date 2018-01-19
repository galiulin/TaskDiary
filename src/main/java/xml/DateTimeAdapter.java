package xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;

public class DateTimeAdapter extends XmlAdapter<String, Timestamp> {
    @Override
    public Timestamp unmarshal(String v) throws Exception {
        return Timestamp.valueOf(v);
    }

    @Override
    public String marshal(Timestamp v) throws Exception {
        return v.toString();
    }
}
