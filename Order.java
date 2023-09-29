public class Order {
	
	//Variables
	String state;
	int zip;
	String address;
	String color;
	String text;
	boolean customImage;
	boolean delivered;
	
	//Constructor used for creating order objects.
	//All variables are initialized here.
	public Order(String state, int zip, String address, String color, String text, boolean customImage, boolean delivered) {
		
		this.state = state;
		this.zip = zip;
		this.address = address;
		this.color = color;
		this.text = text;
		this.customImage = customImage;
		this.delivered = delivered;
		
	}
	
	//All T-Shirt information is printed through this function.
	//For now, the ID is dependent on the amount of orders.
	public void printOrder(int id) {
		System.out.println("\nOrder #" + id);
		System.out.println("State: " + state);
		System.out.println("Zip: " + zip);
		System.out.println("Address: " + address);
		System.out.println("Color: " + color);
		System.out.println("Text: " + text);
		System.out.println("Custom Image: " + customImage);
		System.out.println("Delivered: " + delivered);
	}
	
	//Function used for marking an order successfully delivered.
	public void markFinished() {
		delivered = true;
	}
	
		
}
