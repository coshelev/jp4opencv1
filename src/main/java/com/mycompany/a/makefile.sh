
//***************** Работа без maven из папки ~/javaprojects/jp3/src/main/java/com/mycompany/a

//1.HelloServlet.java compilation
javac -classpath "/opt/jetty/lib/servlet-api-3.1.jar" HelloServlet.java

//1.Servlet2.java compilation
javac -classpath "/opt/jetty/lib/servlet-api-3.1.jar" Servlet2.java


//2. JettyServer.java compilation
javac -classpath ".:/opt/jetty/lib/jetty-server-9.4.12.v20180830.jar:/opt/jetty/lib/jetty-util-9.4.12.v20180830.jar:/opt/jetty/lib/jetty-servlet-9.4.12.v20180830.jar:/opt/jetty/lib/servlet-api-3.1.jar" JettyServer.java

//3. a.java compilation
javac a.java

//4.1 run app без копирования библиотек
$ java -classpath ".:/opt/jetty/lib/jetty-server-9.4.12.v20180830.jar:/opt/jetty/lib/jetty-util-9.4.12.v20180830.jar:/opt/jetty/lib/servlet-api-3.1.jar:/opt/jetty/lib/jetty-http-9.4.12.v20180830.jar:/opt/jetty/lib/jetty-io-9.4.12.v20180830.jar:/opt/jetty/lib/jetty-servlet-9.4.12.v20180830.jar:/opt/jetty/lib/jetty-security-9.4.12.v20180830.jar" A

//4.2 с копированием библиотек в подкаталог приложения
$ java -classpath ".:lib/jetty-server-9.4.12.v20180830.jar:lib/jetty-util-9.4.12.v20180830.jar:lib/servlet-api-3.1.jar:lib/jetty-http-9.4.12.v20180830.jar:lib/jetty-io-9.4.12.v20180830.jar:lib/jetty-servlet-9.4.12.v20180830.jar:lib/jetty-security-9.4.12.v20180830.jar" A

//*******************с maven из папки ~/javaprojects/jp3


