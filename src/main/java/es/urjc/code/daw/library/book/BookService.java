package es.urjc.code.daw.library.book;

import java.util.List;
import java.util.Optional;

import es.urjc.code.daw.library.features.AppFeatures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import es.urjc.code.daw.library.notification.NotificationService;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

/* Este servicio se usará para incluir la funcionalidad que sea 
 * usada desde el BookRestController y el BookWebController
 */
@Service
public class BookService {
	private FeatureManager manager;
	private BookRepository repository;
	private NotificationService notificationService;
	private LineBreaker lineBreaker;
	private static final int LINEBREAKER_LIMIT = 10;

	public BookService(FeatureManager manager, BookRepository repository, NotificationService notificationService, LineBreaker lineBreaker) {
		this.manager = manager;
		this.repository = repository;
		this.notificationService = notificationService;
		this.lineBreaker = lineBreaker;
	}

	public Optional<Book> findOne(long id) {
		return repository.findById(id);
	}
	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<Book> findAll() {
		return repository.findAll();
	}

	public Book save(Book book) {
		if (manager.isActive(AppFeatures.LINEBREAKER)){
			book.setDescription(lineBreaker.breakLine(book.getDescription(), LINEBREAKER_LIMIT));
		}
		Book newBook = repository.save(book);
		if (manager.isActive(AppFeatures.ASYNC_EVENTS)){
			notificationService.asynchronousNotify("Book Event: book with title="+newBook.getTitle()+" was created");
		} else {
			notificationService.notify("Book Event: book with title=" + newBook.getTitle() + " was created");
		}
		return newBook;
	}

	public void delete(long id) {
		repository.deleteById(id);
		notificationService.notify("Book Event: book with id="+id+" was deleted");
	}
}
