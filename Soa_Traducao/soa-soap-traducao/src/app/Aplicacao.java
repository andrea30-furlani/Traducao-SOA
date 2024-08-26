package app;

import java.awt.Dimension;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.transport.http.servlet.ServletAdapter;
import com.sun.xml.ws.transport.http.servlet.ServletAdapterList;
import com.sun.xml.ws.transport.http.servlet.WSServletDelegate;

import sib.Traducao;

public class Aplicacao {
    public static void main(String[] args) throws Exception {
		//Interface gr�fica do servidor
		JFrame frame = new JFrame();	
		frame.setTitle("Jetty");
		frame.setPreferredSize(new Dimension(180, 180));
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		JLabel label = new JLabel();
		label.setText("Jetty em execucao!");
		label.setBounds(15, 15, 160, 100);
		frame.add(label);
		
		JLabel label2 = new JLabel();
		label2.setText("Web Service SOAP");
		label2.setBounds(15, 45, 160, 100);
		frame.add(label2);		
    	
    	//Servidor		
        Server jettyServer = new Server(9000);
		ContextHandler context = new ContextHandler();
		context.setContextPath("/");
		context.setClassLoader(Thread.currentThread().getContextClassLoader());
		jettyServer.setHandler(context);
		
		//SOAP container		
		ServletContextHandler soapContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        WSEndpoint<Traducao> endpoint = WSEndpoint.create(Traducao.class, false, null, null, null, null,
        		BindingImpl.getDefaultBinding(), null, null, null, false);
        ServletAdapter servletAdapter = new ServletAdapterList(null).createAdapter("soap", "/soap", endpoint);
		 
        SoapServlet soapServlet = new SoapServlet(
        		new WSServletDelegate(
        				Collections.singletonList(servletAdapter), 
        				new ServletContextSoap()
        		)
        ); 
					
        soapContextHandler.addServlet(new ServletHolder(soapServlet), "/soap");        
		jettyServer.setHandler(soapContextHandler);
        
		frame.setVisible(true);
        
		try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
            e.printStackTrace();
        } finally {
            jettyServer.destroy();
        }     
    }
}