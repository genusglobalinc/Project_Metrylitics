/*
 * Author: Jyasi Davis
 * 
 * Program Purpose: To read live data in from specific google sheet cells
 */
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.io.IOException;
import java.net.URL;


import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

public class TheTannerMediaApp 
{
    //Google Account Information
    public static final String GOOGLE_ACCOUNT_USERNAME = "genusglobalinc@gmail.com"; // Fill in google account username
    public static final String GOOGLE_ACCOUNT_PASSWORD = "#Peterpan00"; // Fill in google account password
    public static final String SPREADSHEET_URL = "https://spreadsheets.google.com/feeds/spreadsheets/13VVH2LKp-mqUh5tUxVfQizrJCEsqFDF2FLB9W_PuzYs"; //Fill in google spreadsheet URI
    //Put link here: https://docs.google.com/spreadsheets/d/13VVH2LKp-mqUh5tUxVfQizrJCEsqFDF2FLB9W_PuzYs/edit#gid=0
    
    //GUI Information
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    
    //---------------------------------------------------------------
    //
    //---------------------------------------------------------------
    public static void addComponentsToPane(Container pane) 
    {
        if (RIGHT_TO_LEFT) 
        {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
        
        JButton button;
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) 
        {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }
        
        button = new JButton("Sandwiches");
        if (shouldWeightX) 
        {
            c.weightx = 0.5;
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(button, c);
        
        button = new JButton("Sides");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(button, c);
        
        button = new JButton("Desserts");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 2;       //third row
        pane.add(button, c);
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    //---------------------------------------------------------------
    // Void method that displays the GUI
    //---------------------------------------------------------------
    private static void createAndShowGUI() 
    {
        //Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    //---------------------------------------------------------------
    // Main Class
    //---------------------------------------------------------------
    
    public static void main(String[] args) throws IOException, ServiceException
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
       
         /** Our view of Google Spreadsheets as an authenticated Google user. */
        SpreadsheetService service = new SpreadsheetService("Print Google Spreadsheet Demo");
 
        // Login and prompt the user to pick a sheet to use.
        service.setUserCredentials(GOOGLE_ACCOUNT_USERNAME, GOOGLE_ACCOUNT_PASSWORD);
 
        // Load sheet
        URL metafeedUrl = new URL(SPREADSHEET_URL);
        SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl, SpreadsheetEntry.class);
        URL listFeedUrl = ((WorksheetEntry) spreadsheet.getWorksheets().get(0)).getListFeedUrl();
 
        // Print entries
        ListFeed feed = (ListFeed) service.getFeed(listFeedUrl, ListFeed.class);
        for(ListEntry entry : feed.getEntries())
        {
          System.out.println("new row");
          for(String tag : entry.getCustomElements().getTags())
          {
            System.out.println("     "+tag + ": " + entry.getCustomElements().getValue(tag));
          }
        }
        
        //Embedded code for Google Analytics:
        //<iframe scrolling="no" style="border:none;" width="250" height="413" src="https://trends.google.com/trends/hottrends/widget?pn=p1&amp;tn=10&amp;h=413"></iframe> //widget for top search trends from Google
    }
}