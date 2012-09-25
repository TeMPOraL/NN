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
    
    static Classifier classifier = new NNClassifier_MLP();

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
        
        double[] inputs = {0.0f, 0.0f, 1.0f, 0.0f};
        double[] inputs2 = {10.0f, 2.0f, 1.0f, 0.0f};
        double[] inputs3 = {10.0f, 4.0f, 1.0f, 0.0f};
        double[] inputs4 = {15.0f, 0.0f, 1.0f, 0.0f};
        double[] inputs5 = {40.0f, 0.0f, 1.0f, 0.0f};
        
        classifier.learn(inputs, 1);
        classifier.learn(inputs2, 2);
        //classifier.learn(inputs3, 3);
        //classifier.learn(inputs4, 4);
        classifier.learn(inputs5, 5);

        System.out.println("" + classifier.ask(inputs) + print_array(classifier.debugAskForAll(inputs)));
        System.out.println("" + classifier.ask(inputs2) + print_array(classifier.debugAskForAll(inputs2)));
        System.out.println("" + classifier.ask(inputs3) + print_array(classifier.debugAskForAll(inputs3)));
        System.out.println("" + classifier.ask(inputs4) + print_array(classifier.debugAskForAll(inputs4)));
        System.out.println("" + classifier.ask(inputs5) + print_array(classifier.debugAskForAll(inputs5)));
                                        
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
        responseHeaders.set("Content-Type", "application/json");
        he.sendResponseHeaders(200, 0);
        
        OutputStream responseBody = he.getResponseBody();
        
        Map<String, Object> params = (Map<String, Object>) he.getAttribute("parameters");

       for(Map.Entry<String, Object> param : params.entrySet()) {
            responseBody.write( ("" + param.getKey() + ": " + (String)param.getValue() + "\n").getBytes());
        }
       
       this.execute(responseBody, params);
       
        responseBody.close();
        
    }
}

class GetTimetablesRequestHandler extends ClassifierRequestHandler {
    
    @Override
    public void execute(OutputStream out, Map<String, Object> params) {
        try {
            out.write("GetTimetablesRequest\n".getBytes());
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
            out.write("RememberTimetableRequest\n".getBytes());
        } catch (IOException ex) {
            Logger.getLogger(ClassifierRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
