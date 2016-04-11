/**
 * @author Myanna Harris
 * CPSC 224
 * Project
 * Cool Music Player
 * 
 * Exception class for action failures
 */

public class FailException extends Exception 
{
	/** Constructor
	  * @pre called
	  * @post FailException object created (thrown)
	  * */
	public FailException(String message)
	{
		super(message);
	}
}
