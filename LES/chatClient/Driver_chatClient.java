package chatClient;

import chatClient.model.Model_chatClient;
import chatClient.view.View_chatClient;

public class Driver_chatClient {

	View_chatClient vcc;
	Model_chatClient mcc;
	
	public Driver_chatClient() throws InterruptedException{
		vcc= new View_chatClient();
		mcc= new Model_chatClient("uiucles1","aphas1a");
		mcc.startIMConversation();
	}
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Driver_chatClient dcc = new Driver_chatClient();
	}

}
