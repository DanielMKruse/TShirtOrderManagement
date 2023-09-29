//General Imports
import java.util.ArrayList;
import java.util.logging.Level;
//Date & Calendar Imports
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

//Imports for the GUI
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class OrderOnlineMain {

	public static void main(String[] args) throws Exception {
		
		/*
		 * 
		 * T-SHIRT ORDER DELIVERY MANAGER (v0.4)
		 * 
		 * The external database has been implemented. Orders can now be saved.
		 * Current orders, removed orders, and finished orders are archived onto tables.
		 * 
		 * Users can now delete removal and finished order history.
		 * 
		 * 
		 */
		
		//Get the current date and time and convert it into a String
      	Date date = Calendar.getInstance().getTime();  
      	DateFormat dFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");  
      	String strDate = dFormat.format(date);
      				
      	//The log is initialized and named after the date and time.
      	OrderLog log = new OrderLog("Log_" + strDate + ".txt");
      						
      	//Log Parameters are set.
      	log.logger.setLevel(Level.ALL);
      	log.logger.setUseParentHandlers(false);
      								
      	try {
      		log.logger.info("Log file has been successfully created.");
      	}
      	catch(Exception e) {
      		e.printStackTrace();
      	}
		
		
      	//Results for SELECT queries.
      	ResultSet rs;
      		
      	try {  
      			
      			//Establish a connection to the MySQL Database.
      			//This program will not run unless connected.
      			Class.forName("com.mysql.cj.jdbc.Driver");
      			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tshirtbase","root","rootpass");  
      			log.logger.info("Successfully connected to the database.");
      			
      			//Connection statement for queries.
      			Statement stmt = con.createStatement();
      			
      			
      	
      	
		
      	
		
		//Creating the Frames
        JFrame frameText = new JFrame("T-Shirt Orders");
        frameText.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameText.setSize(300, 600);
        
        JFrame frameControls = new JFrame("Controls");
        frameControls.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameControls.setSize(300, 150);
        
        JFrame frameParameters = new JFrame("Parameters");
        frameParameters.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameParameters.setSize(300, 200);
        
        //Creating the Panels
        JPanel panelText = new JPanel();
        JPanel panelControls = new JPanel();
        JPanel panelParameters = new JPanel();
        
        //Text Panel Objects
        JTextArea ta = new JTextArea(34, 25);
        ta.setEditable(false);
        ta.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(ta);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        //Control Panel Objects
        GridLayout gLayoutControl = new GridLayout(0,2);
        panelControls.setLayout(gLayoutControl);
        JButton add = new JButton("Add");
        JTextField index = new JTextField(3);
        JButton mark = new JButton("Mark Finished");
        JButton delete = new JButton("Delete");
        JButton reportStats = new JButton("Report Stats");
        JButton reportList = new JButton("Report List");
        JButton deleteHistory = new JButton("Delete History");
        
        //Parameter Panel Objects
        GridLayout gLayoutParam = new GridLayout(0,2);
        panelParameters.setLayout(gLayoutParam);
        JLabel labelState = new JLabel("State");
        JTextField textState = new JTextField();
        JLabel labelZip = new JLabel("Zip Code");
        JTextField textZip = new JTextField();
        JLabel labelAddress = new JLabel("Address");
        JTextField textAddress = new JTextField();
        JLabel labelColor = new JLabel("T-Shirt Color");
        JTextField textColor = new JTextField();
        JLabel labelText = new JLabel("T-Shirt Text");
        JTextField textText = new JTextField();
        JLabel labelImage = new JLabel("T-Shirt Custom Image");
        JCheckBox checkImage = new JCheckBox();
        
        //Add Objects to Panels
        panelText.add(scroll);
        panelControls.add(add);
        panelControls.add(index);
        panelControls.add(mark);
        panelControls.add(delete);
        panelControls.add(reportStats);
        panelControls.add(reportList);
        panelControls.add(deleteHistory);
        panelControls.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelParameters.add(labelState);
        panelParameters.add(textState);
        panelParameters.add(labelZip);
        panelParameters.add(textZip);
        panelParameters.add(labelAddress);
        panelParameters.add(textAddress);
        panelParameters.add(labelColor);
        panelParameters.add(textColor);
        panelParameters.add(labelText);
        panelParameters.add(textText);
        panelParameters.add(labelImage);
        panelParameters.add(checkImage);
        panelParameters.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        //Add Panels to Frames
        frameText.getContentPane().add(panelText);
        frameControls.getContentPane().add(panelControls);
        frameParameters.getContentPane().add(panelParameters);
        
        //Set Spawn Position of Frames
        frameText.setLocation(300, 100);
        frameControls.setLocation(600, 100);
        frameParameters.setLocation(600, 250);
        
        //Set frames to be visible.
        frameText.setVisible(true);
        frameControls.setVisible(true);
        frameParameters.setVisible(true);
		
        log.logger.info("All frames, panels, and objects have been created and initialized.");
        
      	
		
		//Variables for the Statistics Report
		ArrayList<Order> added = new ArrayList<Order>();
		
		log.logger.info("All archive ArrayLists have been created");
		
		
		Random rng = new Random();
		
		/*
		 * 
		 * USER INTERFACE CODE
		 * 
		 */
		
		//Collect order information from the database
		//Display T-Shirt orders in the text window.
		
		try {
		
		ta.setText("");
		rs = stmt.executeQuery("SELECT * FROM tshirttable");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		
		//All information is printed through this while/for loop.
		while (rs.next()) {
			
		    for (int i = 1; i <= columns; i++) {
		    	
		        String columnValue = rs.getString(i);
		        ta.append(rsmd.getColumnName(i) + ":      " + columnValue + "\n");
		        
		    }
		    
		    ta.append("\n");
		    
		}
		
		} catch(Exception e) {
			log.logger.info(e.getMessage());
		}
		
		
		log.logger.info("All current orders are displayed.");
		
		//ADD BUTTON CODE
		add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	
                	//If the parameters are set correctly, a new T-Shirt order will be added to the database.
                	//Newly added orders are stored in another list ready for statistic report.
                	//A prepared statement, which stores the query, is made for execution.
                	
                	PreparedStatement ps = con.prepareStatement("INSERT INTO tshirttable (OrderID, State, Zip, Address, ShirtColor, ShirtText, ShirtImage, Delivered) VALUES ('" +
                			rng.nextInt(1000) + "', '" + 
							textState.getText() + "', '" + 
							textZip.getText() + "', '" + 
							textAddress.getText() + "', '" + 
							textColor.getText() + "', '" + 
							textText.getText() + "', '" + 
							(checkImage.isSelected() ? 1 : 0) + "', '" + 
                			"0');");
					
					//Execute the prepared statement.
					ps.execute();
                	
					added.add(new Order(textState.getText(), Integer.parseInt(textZip.getText()), textAddress.getText(), textColor.getText(), textText.getText(), checkImage.isSelected(), false));
                	log.logger.info("New order was added and archived for report.");
                }
                catch(Exception ex) {
                	//Mismatch errors are caught here.
                	JOptionPane.showMessageDialog(null, "Invalid Parameter(s).");
                	log.logger.info("Invalid Parameters were identified. Exceptions were caught.");
                }
                
                //Refresh and Display T-Shirt orders in the text window.
                try {
                	ta.setText("");
            		ResultSet rs = stmt.executeQuery("SELECT * FROM tshirttable");
            		ResultSetMetaData rsmd = rs.getMetaData();
            		int columns = rsmd.getColumnCount();
            		
            		//All information is printed through this while/for loop.
            		while (rs.next()) {
            			
            		    for (int i = 1; i <= columns; i++) {
            		    	
            		        String columnValue = rs.getString(i);
            		        ta.append(rsmd.getColumnName(i) + ":      " + columnValue + "\n");
            		        
            		    }
            		    
            		    ta.append("\n");
            		    
            		}
            		
            	} catch(Exception ex) {
            		log.logger.info(ex.getMessage());
            	}
                
            }
            
            
        });
		
		//MARK AS FINISHED BUTTON CODE
		mark.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					//If the index is set correctly, that order will be marked as complete.
                	//Complete orders are stored in another database ready for statistic report.
					PreparedStatement ps = con.prepareStatement("UPDATE tshirttable SET Delivered = '1' WHERE OrderID = '" + index.getText() + "';");
					
					//Execute the prepared statement. 
					ps.execute();
					
					
					ps = con.prepareStatement("INSERT INTO tshirtmarked SELECT * FROM tshirttable WHERE OrderID = '" + index.getText() + "';");
					
					ps.execute();
					
                	log.logger.info("Order has been marked as finished and archived for report.");
                }
                catch(Exception ex) {
                	//Out of Bounds and mismatch errors are caught here.
                	JOptionPane.showMessageDialog(null, "Invalid Order Number.");
                	log.logger.info("Invalid value was identified. Exception was caught.");
                }
				
				//Refresh and Display T-Shirt orders in the text window.
				try {
					ta.setText("");
            		ResultSet rs = stmt.executeQuery("SELECT * FROM tshirttable");
            		ResultSetMetaData rsmd = rs.getMetaData();
            		int columns = rsmd.getColumnCount();
            		
            		//All information is printed through this while/for loop.
            		while (rs.next()) {
            			
            		    for (int i = 1; i <= columns; i++) {
            		    	
            		        String columnValue = rs.getString(i);
            		        ta.append(rsmd.getColumnName(i) + ":      " + columnValue + "\n");
            		        
            		    }
            		    
            		    ta.append("\n");
            		    
            		}
            		
            	} catch(Exception ex) {
            		log.logger.info(ex.getMessage());
            	}
				
			}
			
		});
		
		//DELETE BUTTON CODE
		delete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					
					PreparedStatement ps = con.prepareStatement("INSERT INTO tshirtdeleted SELECT * FROM tshirttable WHERE OrderID = '" + index.getText() + "';");
					
					ps.execute();
					
					//If the index is set correctly, that order will be deleted.
                	//Deleted orders are stored in another database ready for statistic report.
					ps = con.prepareStatement("DELETE FROM tshirttable WHERE OrderID = '" + index.getText() + "';");
					
					//Execute the prepared statement. 
					ps.execute();
					
					
					log.logger.info("Order was removed and archived for report.");
                }
                catch(Exception ex) {
                	//Out of Bounds and mismatch errors are caught here.
                	JOptionPane.showMessageDialog(null, "Invalid Order Number.");
                }
				
				//Refresh and Display T-Shirt orders in the text window.
				try {
					ta.setText("");
            		ResultSet rs = stmt.executeQuery("SELECT * FROM tshirttable");
            		ResultSetMetaData rsmd = rs.getMetaData();
            		int columns = rsmd.getColumnCount();
            		
            		//All information is printed through this while/for loop.
            		while (rs.next()) {
            			
            		    for (int i = 1; i <= columns; i++) {
            		    	
            		        String columnValue = rs.getString(i);
            		        ta.append(rsmd.getColumnName(i) + ":      " + columnValue + "\n");
            		        
            		    }
            		    
            		    ta.append("\n");
            		    
            		}
            		
            	} catch(Exception ex) {
            		log.logger.info(ex.getMessage());
            	}
                
                log.logger.info("Current orders display is refreshed");
				
			}
			
		});
		
		//REPORT LIST BUTTON CODE
		reportList.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					  //A new text file is created including the exact date.
					  Date date = Calendar.getInstance().getTime();
					  String strDate = dFormat.format(date);
				      FileWriter report = new FileWriter("ReportList_" + strDate + ".txt");
				      log.logger.info("Report List file is created.");
				      
				      try {
				  		
				  		ResultSet rs = stmt.executeQuery("SELECT * FROM tshirttable");
				  		ResultSetMetaData rsmd = rs.getMetaData();
				  		int columns = rsmd.getColumnCount();
				  		
				  		//All information is printed through this while/for loop.
				  		while (rs.next()) {
				  			
				  		    for (int i = 1; i <= columns; i++) {
				  		    	
				  		        String columnValue = rs.getString(i);
				  		        report.write(rsmd.getColumnName(i) + ":      " + columnValue + "\n");
				  		        
				  		    }
				  		    
				  		    report.write("\n");
				  		    
				  		}
				  		
				  		} catch(Exception ex) {
				  			log.logger.info(ex.getMessage());
				  		}
				      
				      
				      //Report FileWriter is closed, and the user is notified about the text file.
				      report.close();
				      log.logger.info("All current orders have been outputted to the text file.");
				      JOptionPane.showMessageDialog(null, "T-Shirt Orders have been appended to a text file.");
				      
				      
				} catch (IOException ex) {
					
				      //A problem is encountered whilst attempting to create the file.
					  //Should this ever happen, I will have to inspect.
					  log.logger.warning("Unable to create List Report.\n" + ex);
					  JOptionPane.showMessageDialog(null, "An error has occured.");
				      ex.printStackTrace();
				      
				}
				
			}
			
		});

		//REPORT STATS BUTTON CODE
		reportStats.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					  //A new text file is created including the exact date.
					  Date date = Calendar.getInstance().getTime();
					  String strDate = dFormat.format(date);
				      FileWriter report = new FileWriter("ReportStats_" + strDate + ".txt");
				      log.logger.info("Report Stats file is created.");
				      
				      //All Added T-shirt orders are recorded onto a text file.
				      
				      report.write("=====================");
				      report.write("\nALL ADDED ORDERS");
				      report.write("\n=====================\n\n");
				      
				      for(int i = 0; i < added.size(); i++) {
		                	report.write("ADDED ORDER #" + (i+1));
		        			if(added.get(i).delivered) report.write(" (DELIVERED)"); else report.write(" (IN PROGRESS)");
		        			report.write("\n=====================");
		        			report.write("\nState: " + added.get(i).state);
		        			report.write("\nZip Code: " + added.get(i).zip);
		        			report.write("\nAddress: " + added.get(i).address);
		        			report.write("\nColor: " + added.get(i).color);
		        			report.write("\nText: " + added.get(i).text);
		        			report.write("\nCustom Image: ");
		        			if(added.get(i).customImage) report.write("Yes"); else report.write("No");
		        			report.write("\n\n\n");
		        	  }
				      
				      log.logger.info("All added orders have been outputted to the text file.");
				      
				      //All Deleted T-shirt orders are recorded onto a text file.
				      
				      report.write("\n\n\n=====================");
				      report.write("\nALL DELETED ORDERS");
				      report.write("\n=====================\n\n");
				      
				      try {
					  		
					  		ResultSet rs = stmt.executeQuery("SELECT * FROM tshirtdeleted");
					  		ResultSetMetaData rsmd = rs.getMetaData();
					  		int columns = rsmd.getColumnCount();
					  		
					  		//All information is printed through this while/for loop.
					  		while (rs.next()) {
					  			
					  		    for (int i = 1; i <= columns; i++) {
					  		    	
					  		        String columnValue = rs.getString(i);
					  		        report.write(rsmd.getColumnName(i) + ":      " + columnValue + "\n");
					  		        
					  		    }
					  		    
					  		    report.write("\n");
					  		    
					  		}
					  		
					  		} catch(Exception ex) {
					  			log.logger.info(ex.getMessage());
					  		}
				      
				      log.logger.info("All deleted orders have been outputted to the text file.");
				      
				      //All Completed T-shirt orders are recorded onto a text file.
				      
				      report.write("\n\n\n=====================");
				      report.write("\nALL COMPLETED ORDERS");
				      report.write("\n=====================\n\n");
				      
				      try {
					  		
					  		ResultSet rs = stmt.executeQuery("SELECT * FROM tshirtmarked");
					  		ResultSetMetaData rsmd = rs.getMetaData();
					  		int columns = rsmd.getColumnCount();
					  		
					  		//All information is printed through this while/for loop.
					  		while (rs.next()) {
					  			
					  		    for (int i = 1; i <= columns; i++) {
					  		    	
					  		        String columnValue = rs.getString(i);
					  		        report.write(rsmd.getColumnName(i) + ":      " + columnValue + "\n");
					  		        
					  		    }
					  		    
					  		    report.write("\n");
					  		    
					  		}
					  		
					  		} catch(Exception ex) {
					  			log.logger.info(ex.getMessage());
					  		}
				      
				      log.logger.info("All completed orders have been outputted to the text file.");
				      
				      //Report FileWriter is closed, and the user is notified about the text file.
				      report.close();
				      JOptionPane.showMessageDialog(null, "Statistics have been appended to a text file.");
				      
				      
				} catch (IOException ex) {
					
					  //A problem is encountered whilst attempting to create the file.
					  //Should this ever happen, I will have to inspect.
					  log.logger.warning("Unable to create List Report.\n" + ex);
					  JOptionPane.showMessageDialog(null, "An error has occured.");
				      ex.printStackTrace();
				      
				}
				
			}
			
		});
		
		//DELETE HISTORY BUTTON CODE (Deletes all entries in the Marked and Deleted order tables)
				reportList.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						
						try {
							
							PreparedStatement ps = con.prepareStatement("DELETE FROM tshirtdeleted;");
							
							//Execute the prepared statement. 
							ps.execute();
							
							
							
							ps = con.prepareStatement("DELETE FROM tshirtmarked;");
							
							//Execute the prepared statement. 
							ps.execute();
							
							log.logger.info("All entries from the marked/removed tables have been deleted.");
						      
						      
						} catch (Exception ex) {
							
							log.logger.info(ex.getMessage());
						      
						}
						
					}
					
				});
			
      	} catch (Exception e) {
      		log.logger.info("Failed to connect to the database.");
      		log.logger.info(e.getMessage());
      	}
		
	}

}
