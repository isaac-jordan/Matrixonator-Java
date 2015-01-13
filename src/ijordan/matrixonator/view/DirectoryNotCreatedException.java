package ijordan.matrixonator.view;

/**
 * Directory Creation Exception called when generating directories in
 * application. NON-CRITICAL
 * 
 * @author Ewan McCartney
 *
 *         Call with isBasic = false when mkdir() returns false, else call
 *         isBasic = true for all other exceptions
 */

/*
 * THIS IS A NON CRITICAL EXCEPTION. ONCE THROWN WILL SET A FLAG IN THE IO CLASS
 * WHICH WILL STOP IO OPERATIONS FROM OCCURING.
 */

public class DirectoryNotCreatedException extends Exception {

	// Added by Eclipse to suppress warnings
	private static final long serialVersionUID = 4118735588039465261L;
	private String message = "";

	public DirectoryNotCreatedException(boolean isBasic) {
		super();

		this.message = "There was a problem creating working directories for Matrixonator.\nYou may need to contact your system administrator for rights to create folders!\n\nMatrixonator will still operate, however you wont be able to save any data or preferences.";
	}

	public String getMessage() {
		return this.message;
	}
}
