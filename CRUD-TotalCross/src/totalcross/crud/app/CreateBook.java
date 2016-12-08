package totalcross.crud.app;

import totalcross.crud.dao.impl.BookDAOJDBCImpl;
import totalcross.crud.model.Book;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Spacer;
import totalcross.ui.Toast;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

public class CreateBook extends Window { 

	private Edit title, isbn, pages;
	private Button save, cancel, clear;
	
	public CreateBook() {
		super("Biblioteca", VERTICAL_GRADIENT);

		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		Settings.uiAdjustmentsBasedOnFontHeight = true;

		setBackColor(0xDDDDFF);

		add(new Label("Informe um livro"), CENTER, TOP + 50);

		add(new Label("Titulo: "), LEFT + 100, AFTER);
		add(title = new Edit(), LEFT, SAME);
		title.setRect(LEFT + 100, AFTER, FILL-100, 25);

		add(new Label("ISBN: "), LEFT + 100, AFTER);
		add(isbn = new Edit(), LEFT, SAME);
		isbn.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		add(new Label("Quantidade de páginas: "), LEFT + 100, AFTER);
		add(pages = new Edit(), LEFT, SAME);
		pages.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);

		Spacer sp = new Spacer(0, 0);

		add(sp, CENTER, BOTTOM - 200, PARENTSIZE + 10, PREFERRED);

		add(save = new Button("Salvar"), LEFT + 100, SAME, PREFERRED+100, 25, sp);
		save.setBackColor(Color.GREEN);
		save.setForeColor(Color.BLACK);

		add(clear = new Button("Limpar"), CENTER, SAME, PREFERRED+100, 25, sp);

		add(cancel = new Button("Cancelar"), RIGHT - 100, SAME, PREFERRED+100, 25, sp);
		cancel.setBackColor(Color.RED);
		cancel.setForeColor(Color.WHITE);
	}

	public void onEvent(Event e) {
		try {
			switch (e.type) {
			case ControlEvent.PRESSED:
				if (e.target == clear) {
					clear();
				} else if (e.target == save) {
					if (title.getLength() == 0 || isbn.getLength() == 0 || pages.getLength() == 0) {
						Toast.show("Por favor, preencha todos os campos!!", 2000);
					} else{
						Book book = new Book(title.getText(), isbn.getText(), Integer.valueOf(pages.getText()));
						BookDAOJDBCImpl bookDAO = new BookDAOJDBCImpl();
						bookDAO.save(book);;

						Toast.show("Livro cadastrado com sucesso! ", 5000);

						Application app = new Application();
						app.popup();
						
					}
				} else if (e.target == cancel) {
					Application app = new Application();
					app.popup();
				}
				break;
			}
		} catch (Exception ee) {
			MessageBox.showException(ee, true);
		}
	}
}
