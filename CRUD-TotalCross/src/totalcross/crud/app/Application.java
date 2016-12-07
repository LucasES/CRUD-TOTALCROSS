package totalcross.crud.app;

import totalcross.crud.util.BaseDAO;
import totalcross.sys.Settings;
import totalcross.ui.MainWindow;
import totalcross.ui.event.Event;

public class Application extends MainWindow {

	private BaseDAO baseDAO;

	public Application() {

		super("Locadora de filmes", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		setUIStyle(Settings.Android);
		Settings.uiAdjustmentsBasedOnFontHeight = true;
		setBackColor(0xDDDDFF);
	}

	@SuppressWarnings("static-access")
	public void initUI() {
		

		baseDAO = new BaseDAO();

		baseDAO.createTables();
	}

	public void onEvent(Event e) {

	}

}