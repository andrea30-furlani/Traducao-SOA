package app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.ws.transport.http.servlet.WSServletDelegate;

@SuppressWarnings("serial")
public class SoapServlet extends HttpServlet {
	private WSServletDelegate delegate;

    SoapServlet(WSServletDelegate delegate) {
        this.delegate = delegate;
    }
    
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	res.setHeader("Access-Control-Allow-Origin", "*");
    	//res.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
    	res.setHeader("Access-Control-Allow-Headers", "*");
    	res.setHeader("Access-Control-Allow-Credentials", "true");
    	res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    	res.setHeader("Access-Control-Max-Age", "1209600");    	
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        delegate.doGet(req, res, getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	res.setHeader("Access-Control-Allow-Origin", "*");
    	delegate.doPost(req, res, getServletContext());
    }
}