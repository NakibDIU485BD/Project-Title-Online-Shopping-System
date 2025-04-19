
import java.util.*;

class Product {
    private String name;
    private String description;
    private double price;
    private int stockLevel;

    public Product(String name, String description, double price, int stockLevel) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockLevel = stockLevel;
    }

    // Getters
    public String getName() { 
    	return name; 
    	}
    public double getPrice() {
    	return price; 
    	}
    public int getStockLevel() {
    	return stockLevel; 
    	}

    // Method to update stock
    public void updateStockLevel(int quantity) {
        if (stockLevel >= quantity) 
        {
            stockLevel -= quantity;
        } 
        else {
            System.out.println("Not enough stock!");
        }
    }
}

class Inventory {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product, int initialStock) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public boolean inStock(Product product) {
        return product != null && product.getStockLevel() > 0;
    }
}

class ShoppingCart {
    private List<Product> items = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        if (product.getStockLevel() >= quantity) {
            for (int i = 0; i < quantity; i++) {
                items.add(product);
            }
            product.updateStockLevel(quantity);
            System.out.println(quantity + " " + product.getName() + "(s) added to cart.");
        } else {
            System.out.println("Insufficient stock available.");
        }
    }

    public void viewCart() {
        for (Product item : items) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }

    public double totalCost() {
        double total = 0;
        for (Product item : items) {
            total += item.getPrice();
        }
        return total;
    }


    public void clearCart() {
        items.clear();
    }

    public List<Product> getItems() {
        return items;
    }
}

class Customer {
    private String username;
    private String password;
    
    public Customer(String username, String password) {
        this.username = username;
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        this.password = password;
    }

    public String getUsername() {
    	return username; 
    	}
    public boolean checkPassword(String password) { 
    	return this.password.equals(password); 
    	}
}

class CustomerManager {
    private HashMap<String, Customer> customers = new HashMap<>();
    
    public void registerCustomer(Scanner scanner) {
        System.out.print("Enter username: ");
        scanner.nextLine();
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        if (!customers.containsKey(username)) {
            customers.put(username, new Customer(username, password));
            System.out.println("Registration successful!");
        } else {
            System.out.println("Username already exists!");
        }
    }

    public Customer loginCustomer(Scanner scanner) {
        System.out.print("Enter username: ");
        scanner.nextLine();
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        if (customers.containsKey(username) && customers.get(username).checkPassword(password)) {
            System.out.println("Login successful!");
            return customers.get(username);
        }
        System.out.println("Invalid username or password!");
        return null;
    }

    public boolean checkAdminLogin(Scanner scanner) {
        System.out.print("Enter Admin username: ");
        scanner.nextLine();
        String adminUsername = scanner.nextLine();
        System.out.print("Enter Admin password: ");
        String adminPassword = scanner.nextLine();
        
        return adminUsername.equals("Sazzad_Islam") && adminPassword.equals("232-15-835");
    }
}

class Order {
    private Customer customer;
    private List<Product> products;
    private double totalAmount;
    private String paymentMethod;

    public Order(Customer customer, List<Product> products, double totalAmount, String paymentMethod) {
        this.customer = customer;
        this.products = new ArrayList<>(products);
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    public Customer getCustomer() {
    	return customer; 
    	}
    public List<Product> getProducts() { 
    	return products; 
    	}
    public double getTotalAmount() { 
    	return totalAmount; 
    	}
    public String getPaymentMethod() {
    	return paymentMethod; 
    	}

    public void displayOrderDetails() {
        System.out.println("Order Details for Customer: " + customer.getUsername());
        products.forEach(product -> System.out.println(product.getName() + " - $" + product.getPrice()));
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("Payment Method: " + paymentMethod);
    }
}

public class Main {
    private static List<Order> orderHistory = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();
        ShoppingCart cart = new ShoppingCart();
        CustomerManager customerManager = new CustomerManager();
        Customer currentCustomer = null;

        // Adding sample products
        Product p1 =new Product("iPhone", "Latest iPhone", 999.99, 10);
        Product p2 =new Product("Laptop", "High-end gaming laptop", 1500.00, 5);
        Product p3 =new Product("Clothing","Stylish t-shirts",500.00,15);
        Product p4 =new Product("Footwear","Casual sneakers",3000.00,20);
        Product p5 =new Product("Bags","Travel bags",999.00,15);
        Product p6 =new Product("Watches","Smartwatches",2700.00,50);
        Product p7 =new Product("Sunglasses","Accessories",300.00,25);
        Product p8 =new Product("PlayStation 5","Gaming Consoles",66500.00,7);
        Product p9 =new Product("Sony WH-1000XM5","Headphones",34900.00,21);
        Product p10=new Product("Samsung Galaxy S24 Ultra","Latest SmartPhone",10400,10);
        
        inventory.addProduct(p1, 10);
        inventory.addProduct(p2, 5);
        inventory.addProduct(p3, 15);
        inventory.addProduct(p4, 20);
        inventory.addProduct(p5, 15);
        inventory.addProduct(p6, 50);
        inventory.addProduct(p7, 25);
        inventory.addProduct(p8, 7);
        inventory.addProduct(p9, 21);
        inventory.addProduct(p10, 10);
        
        

        System.out.println("Welcome to the Online Shopping System");

        while (true) {
            if (currentCustomer == null) {
                System.out.println("\n1. Register\n2. Login\n3. Admin Login\n7. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        customerManager.registerCustomer(scanner);
                        break;

                    case 2:
                        currentCustomer = customerManager.loginCustomer(scanner);
                        break;

                    case 3:
                        if (customerManager.checkAdminLogin(scanner)) {
                            while (true) {
                                System.out.println("\nAdmin Options:\n1. View Customer Orders\n2. Logout");
                                System.out.print("Enter your choice: ");
                                int adminChoice = scanner.nextInt();
                                
                                switch (adminChoice) {
                                    case 1:
                                        if (orderHistory.isEmpty()) {
                                            System.out.println("No orders available.");
                                        } else {
                                            for (Order order : orderHistory) {
                                                order.displayOrderDetails();
                                            }
                                        }
                                        break;

                                    case 2:
                                        System.out.println("Admin logged out.");
                                        break;

                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }

                                if (adminChoice == 2) break;
                            }
                        } else {
                            System.out.println("Invalid Admin credentials!");
                        }
                        break;

                    case 7:
                        System.out.println("Thank you for shopping!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("\n1. View Products\n2. Add to Cart\n3. View Cart\n4. Checkout\n5. Logout");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Available Products:");
                        for (Product product : inventory.getProducts()) {
                            System.out.println(product.getName() + " - $" + product.getPrice() 
                                + " (Stock: " + product.getStockLevel() + ")");
                        }
                        break;

                    case 2:
                        System.out.print("Enter product name to add to cart: ");
                        scanner.nextLine();
                        String productName = scanner.nextLine();
                        Product selectedProduct = inventory.getProductByName(productName);
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        if (selectedProduct != null && inventory.inStock(selectedProduct)) {
                            cart.addProduct(selectedProduct, quantity);
                        } else {
                            System.out.println("Product not available or out of stock.");
                        }
                        break;

                    case 3:
                        System.out.println("Cart Contents:");
                        cart.viewCart();
                        System.out.println("Total Cost: $" + cart.totalCost());
                        break;

                    case 4:
                        System.out.println("Total Cost: $" + cart.totalCost());
                        System.out.print("Enter payment method (Credit/Debit/NetBanking): ");
                        String paymentMethod = scanner.next();
                        Order order = new Order(currentCustomer, new ArrayList<>(cart.getItems()), cart.totalCost(), paymentMethod);
                        orderHistory.add(order);
                        order.displayOrderDetails();
                        System.out.println("Payment Successful! Amount Paid: $" + cart.totalCost() + " via " + paymentMethod);
                        cart.clearCart();
                        break;

                    case 5:
                        currentCustomer = null;
                        System.out.println("You have logged out.");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}