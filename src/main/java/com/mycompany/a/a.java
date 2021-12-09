
class A{

	public static void main(String[] args){
		System.out.print("class A constructor \n");
		System.out.print ("	class A before jetty\n");
		JettyServer server = new JettyServer();

		try {
        		server.start();
        	} catch (Exception e) {           
            		e.printStackTrace();
        	}

		System.out.print ("class A constructor end\n");
		return;  
	}

	
}
