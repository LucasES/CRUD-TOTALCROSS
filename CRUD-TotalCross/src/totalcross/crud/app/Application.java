package totalcross.crud.app;

import java.util.List;

import totalcross.crud.dao.impl.BookDAOJDBCImpl;
import totalcross.crud.model.Book;
import totalcross.crud.util.BaseDAO;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Grid;
import totalcross.ui.Label;
import totalcross.ui.ListBox;
import totalcross.ui.MainWindow;
import totalcross.ui.Spacer;
import totalcross.ui.Toast;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.gfx.Rect;

public class Application extends MainWindow {
	Button createBook, update, delete;
	ListBox lb;
	Grid grid;
	
	private BaseDAO baseDAO;
		
	private BookDAOJDBCImpl bookDAOJDBCImpl;

	public Application() {

		super("Biblioteca", VERTICAL_GRADIENT);
		baseDAO = new BaseDAO();
		baseDAO.createTables();
		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		add(new Label("Lista de livros"), CENTER, TOP + 50);
		
		bookDAOJDBCImpl = new BookDAOJDBCImpl();
		List<Book> bookList = bookDAOJDBCImpl.findAll();

		Rect r = getClientRect();

		String[] gridCaptions = {"Id", " Título ", " ISBN ", " Páginas "};
		int gridWidths[] = {-7, -40, -30, -10};
		int gridAligns[] = { LEFT,LEFT, LEFT, LEFT};
		grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
		add(grid, LEFT + 5, TOP + 5, r.width, r.height / 2 + r.height / 3);
		grid.secondStripeColor = Color.getRGB(235, 235, 235);

		String[][] lista = new String[100][4];
		int i, j;
		i = j = 0;

		for (Book book : bookList) {
			lista[i][j] = String.valueOf(book.getId());
			j++;
			lista[i][j] = book.getTitle();
			j++;
			lista[i][j] = book.getTitle();
			j++;
			lista[i][j] = String.valueOf(book.getPages());
			j = 0;
			i++;
		}
		grid.setItems(lista);

		Spacer sp = new Spacer(0, 0), sp2 = new Spacer(0, 0);

		add(sp, CENTER, BOTTOM - 200, PARENTSIZE + 10, PREFERRED);
		add(sp2, CENTER, BOTTOM - 400, PARENTSIZE + 10, PREFERRED);
		
		add(createBook = new Button("ADICIONAR"), LEFT, SAME, PREFERRED+100, 25, sp);
		createBook.setBackColor(Color.GREEN);
		createBook.setForeColor(Color.BLACK);
		add(update = new Button("EDITAR"), CENTER, SAME, PREFERRED+100, 25, sp);
		update.setBackColor(Color.YELLOW);
		update.setForeColor(Color.BLACK);
		add(delete = new Button("EXCLUIR"), RIGHT, SAME, PREFERRED+100, 25, sp);
		delete.setBackColor(Color.RED);
		delete.setForeColor(Color.WHITE);
	}

	public void initUI() {
//		baseDAO = new BaseDAO();
//		baseDAO.createTables();
	}

	public void onEvent(Event event) {
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == grid) {
				@SuppressWarnings(value = "unused")
				Object element = grid.getSelectedItem();
			} else if (event.type == ControlEvent.PRESSED && event.target == createBook) {
				CreateBook createBook = new CreateBook();
				createBook.popup();
			} else if (event.type == ControlEvent.PRESSED && event.target == update) {
				if (grid.getSelectedItem() == null) {
					Toast.show("Selecione pelo menos um livro editar!", 2000);
					Application app = new Application();
					app.popup();
				} else {
					String value[] = (String[]) grid.getSelectedItem();
					UpdateBook updateBook = new UpdateBook(value[0]);
					updateBook.popup();
				}
			} else if (event.type == ControlEvent.PRESSED && event.target == delete) {
				if (grid.getSelectedItem() == null) {
					Toast.show("Selecione pelo menos um livro excluir!", 2000);
					Application app = new Application();
					app.popup();
				} else {
					String value[] = (String[]) grid.getSelectedItem();

					bookDAOJDBCImpl = new BookDAOJDBCImpl();
					bookDAOJDBCImpl.delete(value[0]);

					Toast.show("Livro excluído com sucesso!", 2000);
					Application app = new Application();
					app.popup();
				}
			}
		}
	}

}