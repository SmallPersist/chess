package merge2;

public class BlackStart {
	public static void main(String[] args) throws Exception {
		
		Thread black = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					@SuppressWarnings("unused")
					BlackClient black = new BlackClient("black");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		
		
		black.start();
		
	}
}
