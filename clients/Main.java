package clients;
import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierView;
import clients.collection.CollectController;
import clients.collection.CollectModel;
import clients.collection.CollectView;
import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;
import clients.customer.CustomerCatalogue;
import clients.review.ReviewController;
import clients.review.ReviewModel;
import clients.review.ReviewView;
import clients.shopDisplay.DisplayController;
import clients.shopDisplay.DisplayModel;
import clients.shopDisplay.DisplayView;
import clients.warehousePick.PickController;
import clients.warehousePick.PickModel;
import clients.warehousePick.PickView;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


/**
 * Starts all the clients  as a single application.
 * Good for testing the system using a single application but no use of RMI.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */
class Main
{
  // Change to false to reduce the number of duplicated clients

  private final static boolean many = false;        // Many clients? (Or minimal clients)

  public static void main (String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException
  {
    new Main().begin();
  }

  /**
   * Starts test system (Non distributed)
 * @throws LineUnavailableException 
 * @throws IOException 
 * @throws UnsupportedAudioFileException 
   */
  public void begin() throws UnsupportedAudioFileException, IOException, LineUnavailableException
  {
    //DEBUG.set(true); /* Lots of debug info */
    MiddleFactory mlf = new LocalMiddleFactory();  // Direct access
 
    startCustomerGUI_MVC( mlf );
    //startSound();
    if ( many ) 
     startCustomerGUI_MVC( mlf );
    //startCashierGUI_MVC( mlf );
    startCashierGUI_MVC( mlf );
    startBackDoorGUI_MVC( mlf );
    if ( many ) 
      startPickGUI_MVC( mlf );
    startPickGUI_MVC( mlf );
    startDisplayGUI_MVC( mlf );
    if ( many ) 
      startDisplayGUI_MVC( mlf );
    startCollectionGUI_MVC( mlf );
    startCustomerCatalogue(mlf);
    startReviewGUI_MVC(mlf );
  }
  
  public void startSound() throws UnsupportedAudioFileException,IOException,LineUnavailableException
  {
	  begin();
	  playSound("music.wav");
	  Thread t = new Thread( () -> {
		try {
			begin();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} );
	  t.setDaemon(true);
	  t.start();
  }
  
  public void playSound(String soundFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException
  {
	  File f = new File("./" + soundFile);
	  AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
	  Clip clip = AudioSystem.getClip();
	  clip.open(audioIn);
	  clip.start();
  }
  
  public void startCustomerGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();
    window.setTitle( "Customer Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();
    
    CustomerModel model      = new CustomerModel(mlf);
    CustomerView view        = new CustomerView( window, mlf, pos.width, pos.height );
    CustomerController cont  = new CustomerController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // start Screen
  }

  /**
   * start the cashier client
   * @param mlf A factory to create objects to access the stock list
   */
  public void startCashierGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();
    window.setTitle( "Cashier Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();
    
    CashierModel model      = new CashierModel(mlf);
    CashierView view        = new CashierView( window, mlf, pos.width, pos.height );
    CashierController cont  = new CashierController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
    model.askForUpdate();            // Initial display
  }

  public void startCustomerCatalogue(MiddleFactory mlf )
  {
    JFrame  catalogueFrame = new JFrame();
    //catalogueFrame.setTitle( "Customer Catalogue MVC");
    catalogueFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

	catalogueFrame = new JFrame("Catalogue Client MVC");
	catalogueFrame.setSize(400, 300);
	String data[][]= {{"0001", "40' LED HD TV","£269.00"}};
	String column[]= {"CODE", "DESCRIPTION", "PRICE"};
	JTable cat = new JTable(data,column);
	cat.setBounds(30,40,200,300);
	JScrollPane sp=new JScrollPane(cat);
	catalogueFrame.add(sp);

    //model.addObserver( view );       // Add observer to the model
    catalogueFrame.setVisible(true); // start Screen
 
  } 
  
  public void startBackDoorGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "BackDoor Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();
    
    BackDoorModel model      = new BackDoorModel(mlf);
    BackDoorView view        = new BackDoorView( window, mlf, pos.width, pos.height );
    BackDoorController cont  = new BackDoorController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
  }
  

  public void startPickGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "Pick Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();
    
    PickModel model      = new PickModel(mlf);
    PickView view        = new PickView( window, mlf, pos.width, pos.height );
    PickController cont  = new PickController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
  }
  
  public void startDisplayGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "Display Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();
    
    DisplayModel model      = new DisplayModel(mlf);
    DisplayView view        = new DisplayView( window, mlf, pos.width, pos.height );
    DisplayController cont  = new DisplayController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
  }
  
  public void startReviewGUI_MVC(MiddleFactory mlf ) throws IOException
  {
    JFrame  reviewFrame = new JFrame();

    reviewFrame.setTitle( "ReviewView MVC");
    reviewFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();
    
    ReviewModel model      = new ReviewModel(mlf);
    ReviewView view        = new ReviewView( reviewFrame, mlf, pos.width, pos.height );
    ReviewController cont  = new ReviewController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    reviewFrame.setVisible(false);         // Make window visible
  }


  public void startCollectionGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "Collect Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();
    
    CollectModel model      = new CollectModel(mlf);
    CollectView view        = new CollectView( window, mlf, pos.width, pos.height );
    CollectController cont  = new CollectController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
  }

}
