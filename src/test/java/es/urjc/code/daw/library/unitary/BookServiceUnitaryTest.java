package es.urjc.code.daw.library.unitary;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.urjc.code.daw.library.book.Book;
import es.urjc.code.daw.library.book.BookRepository;
import es.urjc.code.daw.library.book.BookService;
import es.urjc.code.daw.library.notification.NotificationService;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;

@DisplayName("BookService Unitary tests")
public class BookServiceUnitaryTest {

    private BookService bookService;
    private NotificationService notificationService;
    private BookRepository repository;

    private FeatureManager manager;

    @BeforeEach
	public void setup() {
        
        repository = mock(BookRepository.class);
        notificationService = mock(NotificationService.class);
        manager = mock(FeatureManager.class);
        bookService = new BookService(manager, repository, notificationService);
			
    }

    @Test
    @DisplayName("Cuando se guarda un libro utilizando BookService, se guarda en el repositorio y se lanza una notificación")
	public void createBook(){

        Book book = new Book("FAKE BOOK", "FAKE DESCRIPTION");

		// Given
		when(repository.save(book)).thenReturn(book);
        when(manager.isActive(any(Feature.class))).thenReturn(false);
		
		// When
		bookService.save(book);

        // Then
        verify(repository).save(book);
        verify(notificationService).notify("Book Event: book with title="+book.getTitle()+" was created");
    }
    
    @Test
    @DisplayName("Cuando se borra un libro utilizando BookService, se elimina del repositorio y se lanza una notificación")
	public void deleteBook(){
        
        long fakeId = 1L;

        // Given
        // No es necesario definir el comportamiento del delete()
        // -> mock() se ocupa de que no realice las acciones reales
        // -> Devuelve void
		// when(repository.delete(fakeId);
		
		// When
		bookService.delete(fakeId);

        // Then
        verify(repository).deleteById(fakeId);
        verify(notificationService).notify("Book Event: book with id="+fakeId+" was deleted");
	}

}
