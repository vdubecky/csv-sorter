package sorter.cmd;

import com.beust.jcommander.IStringConverter;

import java.nio.charset.Charset;

public class CharsetConverter implements IStringConverter<Charset> {
    @Override
    public Charset convert(String value) {
        return Charset.forName(value);
    }
}
