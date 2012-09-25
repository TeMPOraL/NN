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
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author TeMPOraL
 */
public class MlpClassifier {
    
    public static Classifier classifier = new NNClassifier_MLP();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        InetSocketAddress addr = new InetSocketAddress(8080);
        HttpServer server = HttpServer.create(addr, 0);
        
        server.createContext("/getTimetables", new GetTimetablesRequestHandler()).getFilters().add(new ParameterFilter());
        server.createContext("/deleteTimetable", new DeleteTimetableRequestHandler()).getFilters().add(new ParameterFilter());
        server.createContext("/rememberTimetable", new RememberTimetableRequestHandler()).getFilters().add(new ParameterFilter());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Server is listening on port 8080" );
        
    }
    
    static String print_array(double[] arr) {
        String ret = "[ ";
        
        for(double el : arr) {
            ret += el + " ";
        }
        
        return ret + "]";
    }
}

abstract class ClassifierRequestHandler implements HttpHandler {

    public abstract void execute(OutputStream out, Map<String, Object> params);
        
    @Override
    public void handle(HttpExchange he) throws IOException {
        Headers responseHeaders = he.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/javascript");
        he.sendResponseHeaders(200, 0);
        
        OutputStream responseBody = he.getResponseBody();
        
        Map<String, Object> params = (Map<String, Object>) he.getAttribute("parameters");
       
        this.execute(responseBody, params);
       
        responseBody.close();
        
    }
}

class GetTimetablesRequestHandler extends ClassifierRequestHandler {
    
    @Override
    public void execute(OutputStream out, Map<String, Object> params) {
        try {
            out.write(((String)params.get("callback")).getBytes());
            
            double[] input = {
                Double.parseDouble((String)params.get("time")),
                Double.parseDouble((String)params.get("day")),
                Double.parseDouble((String)params.get("locX")),
                Double.parseDouble((String)params.get("locY"))
            };
            
            int[] output = MlpClassifier.classifier.askForMultiple(input);

            out.write(("([").getBytes());

            for(int numOut : output) {
                out.write(("" + numOut + ",").getBytes());
            }
            out.write(("]);").getBytes());
                    
        } catch (IOException ex) {
            Logger.getLogger(ClassifierRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class DeleteTimetableRequestHandler extends ClassifierRequestHandler {
    
    @Override
    public void execute(OutputStream out, Map<String, Object> params) {
        try {
            out.write("DeleteTimetableRequest\n".getBytes());
        } catch (IOException ex) {
            Logger.getLogger(ClassifierRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class RememberTimetableRequestHandler extends ClassifierRequestHandler {
    
    @Override
    public void execute(OutputStream out, Map<String, Object> params) {
        try {
            out.write(((String)params.get("callback")).getBytes());
            
            double[] input = {
                Double.parseDouble((String)params.get("time")),
                Double.parseDouble((String)params.get("day")),
                Double.parseDouble((String)params.get("locX")),
                Double.parseDouble((String)params.get("locY"))
            };
            
            int output = Integer.parseInt((String)params.get("timetable"));
            
            MlpClassifier.classifier.learn(input, output);
            
        } catch (IOException ex) {
            Logger.getLogger(ClassifierRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
