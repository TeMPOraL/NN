/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp.classifier;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
/**
 *
 * @author TeMPOraL
 */
public class MlpClassifier {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        InetSocketAddress addr = new InetSocketAddress(8080);
        HttpServer server = HttpServer.create(addr, 0);
        
        server.createContext("/", new ClassifierRequestHandler()).getFilters().add(new ParameterFilter());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Server is listening on port 8080" );
    }
}

class ClassifierRequestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        Headers responseHeaders = he.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");
        he.sendResponseHeaders(200, 0);
        
        OutputStream responseBody = he.getResponseBody();
        responseBody.write("Hello world!\n".getBytes());
        
        Headers requestHeaders = he.getRequestHeaders();
        Set<String> keySet = requestHeaders.keySet();
        
        Map<String, Object> params = (Map<String, Object>) he.getAttribute("parameters");
        
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            List values = requestHeaders.get(key);
            String s = key + " = " + values.toString() + "\n";
            responseBody.write(s.getBytes());
        }
        responseBody.write((he.getRequestURI().toString() + "\n").getBytes());

        for(Map.Entry<String, Object> param : params.entrySet()) {
            responseBody.write( ("" + param.getKey() + ": " + (String)param.getValue() + "\n").getBytes());
        }
        responseBody.close();
        
    }
}