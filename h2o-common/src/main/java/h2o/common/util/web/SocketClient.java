package h2o.common.util.web;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import jodd.io.StreamUtil;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.Socket;


public class SocketClient {


	private volatile String server;

	private volatile int port;
	
	private volatile int timeout = 120000;
	
	private volatile String characterEncoding = "UTF-8";
	
	private volatile String revCharacterEncoding = "UTF-8";
	
	private volatile int headLen = 8;
	




    //发送
	public String send(String req) {
		
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		
		try {
		
			socket = new Socket(this.server , this.port);
			socket.setSoTimeout(timeout);

            in  = socket.getInputStream();
			out = socket.getOutputStream();


			return proc( in , out , req );
			
		} catch (Exception e) {
			Tools.log.error("Send Exception", e);
			throw ExceptionUtil.toRuntimeException(e);
		}  finally {
			
			if( in != null ) {
				try {
					in.close();
				} catch(IOException e) {
					Tools.log.error("in.close()", e);
				}
			}
			
			if( out != null ) {
				try {
					out.close();
				} catch(IOException e) {
					Tools.log.error("out.close()", e);
				}
			}
			
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					Tools.log.error("socket.close()", e);
				}
			}
		}
	}



	protected String proc( InputStream in, OutputStream out , String req  ) throws Exception {

        BufferedOutputStream bufOut = new BufferedOutputStream( out );

        byte[] buf = req.getBytes( characterEncoding );

        String len = StringUtils.leftPad( Integer.toString(buf.length) , this.headLen , '0' );

        out.write(len.getBytes());
        out.flush();
        out.write(buf);
        out.flush();

        BufferedInputStream bufin = new BufferedInputStream(in);
        byte[] l = StreamUtil.readBytes( bufin , this.headLen );
        int inlen = Integer.parseInt( new String(l) );

        byte[] inbuf = StreamUtil.readBytes(bufin, inlen);
        String res = new String(inbuf, revCharacterEncoding);


        return res;

    }





    public void setServer(String server) {
        this.server = server;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public void setRevCharacterEncoding(String revCharacterEncoding) {
        this.revCharacterEncoding = revCharacterEncoding;
    }

    public void setHeadLen(int headLen) {
        this.headLen = headLen;
    }


	
}
