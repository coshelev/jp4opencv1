import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.BufferedReader;
import com.google.gson.*;
import java.util.*;
import jakarta.mail.*;
import jakarta.mail.search.*;
import jakarta.mail.internet.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import java.net.URI;
import java.net.http.*;

public class MailServlet extends HttpServlet {
    private String greeting="Hello World from MailServlet";
    public MailServlet(){}
    public MailServlet(String greeting){
        this.greeting=greeting;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>"+greeting+"</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
        
        readAutobrokerMail();
    }
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
	   StringBuffer jb = new StringBuffer();
  	   String line = null;
      String json = "";
   	try {
    	} catch (Exception e) { /*report an error*/ }     
   } 
   
   private void readAutobrokerMail(){
         final String user = "luidorexpertALL";       // имя пользователя
         final String pass = "kiduttocmomzgdqk";      // пароль
         final String host = "imap.yandex.ru";        // адрес почтового сервера

         try {
            // Создание свойств
            Properties props = new Properties();

            //включение debug-режима
             props.put("mail.debug", "false");

            //Указываем протокол - IMAP с SSL
            props.put("mail.store.protocol", "imaps");
            Session session = Session.getInstance(props);
            Store store = session.getStore();

            //подключаемся к почтовому серверу
            store.connect(host, user, pass);

            //получаем папку с входящими сообщениями
            Folder inbox = store.getFolder("INBOX");

            //открываем её только для чтения
            inbox.open(Folder.READ_ONLY);
            
            final FromTerm fromTerm             = new FromTerm(new InternetAddress("no-reply@a-b63.ru"));
            //final SubjectTerm subjectTerm     = new SubjectTerm("Заявка:");
            //AndTerm termsSummary              = new AndTerm(fromTerm, subjectTerm);
           
            var a = java.time.LocalDate.now().minusDays(1);
            Date  receivedDate = java.sql.Date.valueOf(a);
            System.out.println("receivedDate = " + receivedDate);
            final ReceivedDateTerm received  = new ReceivedDateTerm(ComparisonTerm.GT, receivedDate);
            //termsSummary                   = new AndTerm(received, termsSummary);
            //AndTerm termsSummary           = new AndTerm(received, subjectTerm);
  
            //final Message[] foundMessages    = inbox.search(termsSummary);            
            final var foundMessages       = inbox.search(received);  
            System.out.println(" ***** foundMessages = "+foundMessages.length); 

            for (var i: foundMessages){
                System.out.println("********************");
               
               //Filter by sender
               if (i.match(fromTerm)!=true) continue;
        
               MimeMessage m = (MimeMessage) i;
               String messageId = m.getMessageID();
               messageId = messageId.replace("<", "");
               messageId = messageId.replace(">", "");
              
               //Multipart mp1 = (Multipart) i.getContent();
               String mp1 = (String) i.getContent();
            
               Document doc = Jsoup.parse(mp1);
               String text = doc.body().text();  
               System.out.println("text = "+text);
              
               int SendWebhook = 1;
               if (SendWebhook == 1){ 
               Gson gson= new Gson();
               Map<String, String> inputMap = new HashMap<String, String>();
               inputMap.put("type",          "autobroker mail message");
               inputMap.put("messageID",     messageId);
               inputMap.put("messageBody",   text);
               String requestBody = gson.toJson(inputMap);
                      
               var client = HttpClient.newHttpClient();
               var request = HttpRequest.newBuilder()
                  .uri(URI.create("http://mainappl.main.luidorauto.ru/sys_agr/hs/webhooks/anypost/v1"))
                  .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                  .header("accept", "application/json") 
                  .build();
              
               //HttpResponse<String> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
               //var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
               //System.out.println(response.body());  
               client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
               };
            }
          
          } catch (Exception e) { /*report an error*/ }

   }
    
}