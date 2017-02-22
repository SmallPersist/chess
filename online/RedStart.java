package merge2;

public class RedStart {
	public static void main(String[] args) {
		Thread red = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					@SuppressWarnings("unused")
					RedClient red = new RedClient("red");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		
		red.start();
	}
}
