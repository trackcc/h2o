package h2o.event.impl.socket.akka;

import akka.util.ByteString;
import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.io.StreamUtil;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;


public class Data {

    private static final int DEF_LEN_SIZE = 8;

    private final int lenSize;

    private Buffer buf;

    private String charset = "UTF-8" ;

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Data() {
        this.lenSize = DEF_LEN_SIZE;
    }

    public Data(int lenSize) {
        this.lenSize = lenSize;
    }

    public boolean putBlock(ByteString block) {

        ByteString d;

        if (buf == null) {

            String l = new String(block.take( this.lenSize ).toArray());
            int len = Integer.parseInt(l);

            buf = new Buffer(len);
            d = block.drop( this.lenSize );

        } else {

            d = block;

        }

        buf.put(d.toArray());

        return buf.ok();
    }


    public String toString() {
        try {
            return new String( buf.get(), this.charset);
        } catch ( Exception e ) {
            throw ExceptionUtil.toRuntimeException(e);
        }

    }

    public ByteString encode2ByteString( String data ) throws UnsupportedEncodingException {

        byte[] bs = data.getBytes( this.charset );

        String len = StringUtils.leftPad(Integer.toString(bs.length), this.lenSize, '0');

        return ByteString.fromArray(len.getBytes()).concat(ByteString.fromArray(bs));
    }

    public void close() {
        if ( buf != null ) {
            buf.close();
            buf = null;
        }
    }


    private static class Buffer {

        private ByteArrayOutputStream bs = new ByteArrayOutputStream();

        private int l;

        public Buffer(int len) {
            this.l = len;
        }

        public void put(byte[] buf) {

            int len = buf.length;
            int ll = len < l ? len : l;

            if (ll > 0) {

                bs.write(buf, 0, ll);

                l -= ll;

            }

        }

        public boolean ok() {
            return l == 0;
        }

        public byte[] get() {
            return bs.toByteArray();
        }


        public void close() {
            StreamUtil.close(bs);
            bs = null;
        }
    }


}