package nl.justobjects.pushlet.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import nl.justobjects.pushlet.util.Log;
/**
 * 解决中文乱码问题
 * 来自:http://blog.163.com/kangle0925@126/blog/static/27758198201282651142892/
 * @author dengqiang
 * 2014-05-09 15:00
 *
 */
public class XMLAdapter implements ClientAdapter {
	/**
	 * Header for strict XML
	 */
	// public static final String XML_HEAD =
	// "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
	private String contentType = "text/plain;charset=UTF-8";
	// private ServletOutputStream out = null; 
	private PrintWriter pw = null;
	private HttpServletResponse servletRsp;
	private boolean strictXML;

	/**
	 * Initialize.
	 */
	public XMLAdapter(HttpServletResponse aServletResponse) {
		this(aServletResponse, false);
	}

	/**
	 * Initialize.
	 */
	public XMLAdapter(HttpServletResponse aServletResponse, boolean useStrictXML) {
		servletRsp = aServletResponse;

		// Strict XML implies returning a complete XML document
		strictXML = useStrictXML;
		if (strictXML) {
			contentType = "text/xml;charset=UTF-8";
		}
	}

	public void start() throws IOException {

		// If content type is plain text
		// then this is not a complete XML document, but rather
		// a stream of XML documents where each document is
		// an Event. In strict XML mode a complete document is returned.
		servletRsp.setContentType(contentType);

		pw = servletRsp.getWriter();

		// Don't need this further
		servletRsp = null;

		// Start XML document if strict XML mode
		if (strictXML) {
			pw.print("<pushlet>");
		}
	}

	/**
	 * Force client to refresh the request.
	 */
	public void push(Event anEvent) throws IOException {
		debug("event=" + anEvent);

		// Send the event as XML to the client and flush.
		pw.print(anEvent.toXML(strictXML));
		pw.flush();
	}

	/**
	 * No action.
	 */
	public void stop() throws IOException {
		// Close XML document if strict XML mode
		if (strictXML) {
			pw.print("</pushlet>");
			pw.flush();
		}
	}

	private void debug(String s) {
		Log.debug("[XMLAdapter]" + s);
	}
}
