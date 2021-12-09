import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.BufferedReader;
import com.google.gson.*;
import java.util.*;

public class SleepServlet extends HttpServlet
{
    private String greeting="Hello World from SleepServlet";
    public SleepServlet(){}
    public SleepServlet(String greeting)
    {
        this.greeting=greeting;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>"+greeting+"</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
	   StringBuffer jb = new StringBuffer();
  	   String line = null;
      String json = "";
   	try {
    	   BufferedReader reader = request.getReader();
         while ((line = reader.readLine()) != null){
            jb.append(line);
            System.out.println("line = "+line);
           };
           
           json      = jb.toString();
           Gson gson = new Gson(); 
           
           //v1 - 
           //Map map = (Map) gson.fromJson(json, Object.class);  
           
           //v2, here key and values cast to String
           Map<String, String> map = (Map<String, String>) gson.fromJson(json, Object.class); 
                      
           System.out.println(map.getClass().toString());
           System.out.println(map);
           System.out.println(map.get("chatid"));
           
           String chatid = map.get("chatid");
           System.out.println(chatid);
          
           int period = Integer.parseInt(map.get("period"));
           if(period>0){
             Thread.sleep(period*1000);   
            
             //  Thread thread1 = new Thread(){
             //       public void run(){
             //          Thread.sleep(10);
             //          System.out.println("Thread Running");        
             //       }
             //  };
             //  thread1.start();
     
           }
           System.out.println(map.get("period"));


      } catch (Exception e) { /*report an error*/ }
      
} 
    
}