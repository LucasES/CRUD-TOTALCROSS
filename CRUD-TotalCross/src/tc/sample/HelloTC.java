package tc.sample;
import java.sql.SQLException;
 
import totalcross.phone.*;
import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sql.Statement;
import totalcross.sys.*;
import totalcross.ui.*;
import totalcross.ui.dialog.*;
import totalcross.ui.event.*;
import totalcross.ui.gfx.*;
import totalcross.util.*;
import totalcross.util.Date;
 
public class HelloTC extends MainWindow {
   private Edit edName,edBorn,edPhone;
   private Button btInsert, btClear,btDial;
   private Connection dbcon;
      
   public HelloTC() {
      super("Hello TotalCross", VERTICAL_GRADIENT);
      gradientTitleStartColor = 0; gradientTitleEndColor = 0xAAAAFF;
      // sets the default user interface style to Android
      // There are others, like 
      setUIStyle(Settings.Android);
      // Use font height for adjustments, not pixels
      Settings.uiAdjustmentsBasedOnFontHeight = true;
      setBackColor(0xDDDDFF);
   }
   
   // Initialize the user interface
   public void initUI() {
      add(new Label("ENTER YOUR DATA"),CENTER,TOP+50);
      
      add(new Label("Name: "), LEFT,AFTER+100);
      add(edName = new Edit(),LEFT,AFTER);
      
      add(new Label("Born date"), LEFT,AFTER+50);
      add(edBorn = new Edit(),LEFT,AFTER);
      edBorn.setMode(Edit.DATE);
      
      add(new Label("Phone number"),LEFT,AFTER+50);
      //                         x    y          w             h
      add(edPhone = new Edit(),LEFT,AFTER,PARENTSIZE+70,PREFERRED);
      edPhone.setKeyboard(Edit.KBD_NUMERIC);
      add(btDial = new Button("Dial"),RIGHT,SAME,PARENTSIZE+20,PREFERRED);
      btDial.setEnabled(Settings.platform.equals(Settings.ANDROID) || Settings.isWindowsDevice() || Settings.isIOS());
      
      Spacer sp = new Spacer(0,0);
      add(sp, CENTER,BOTTOM-300, PARENTSIZE+10, PREFERRED);
      add(btInsert = new Button("Insert"), BEFORE, SAME, PARENTSIZE+40, PREFERRED,sp);
      add(btClear  = new Button("Clear"),  AFTER,  SAME, PARENTSIZE+40, PREFERRED,sp);
      btInsert.setBackColor(Color.GREEN);
      btClear.setBackColor(Color.PINK);
      
      if (Settings.onJavaSE || Settings.platform.equals(Settings.WIN32))
         add(new Label("Press F11 on date/number to open keypad"),CENTER,BOTTOM);
   
      try {
         dbcon = DriverManager.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, "test.db"));
         Statement st = dbcon.createStatement();
         st.execute("create table if not exists person (name varchar, born datetime, number varchar)");
         st.close();
      }
      catch (Exception e) {
         MessageBox.showException(e, true);
         exit(0);
      }
      Toast.posY = CENTER;
   }
 
   public void onEvent(Event e) {
      try {
         switch (e.type) {
            case ControlEvent.PRESSED:
               if (e.target == btClear)
                  clear();
               else if (e.target == btInsert)
                  doInsert();
               else if (e.target == btDial && edPhone.getTrimmedLength() > 0)
                  Dial.number(edPhone.getText());
               break;
         }
      }
      catch (Exception ee) {
         MessageBox.showException(ee,true);
      }
   }
   
   private void doInsert() throws SQLException, InvalidDateException {
      if (edName.getLength() == 0 || edBorn.getLength() == 0 || edPhone.getLength() == 0)
         Toast.show("Please fill all fields!",2000);
      else {
         String name = edName.getText();
         Date born = new Date(edBorn.getText());
         String phone = edPhone.getText();
         
         Statement st = dbcon.createStatement();
         st.executeUpdate("insert into person values('"+name+"','"+born.getSQLString()+"','"+phone+"')");
         st.close();
         clear();
         Toast.show("Data inserted successfully!",2000);
      }
   }
}