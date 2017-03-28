package revenuerecognition;

/**
 * Created by mrteera on 1/19/2017 AD.
 */
public class ApplicationException extends Throwable {
    private static final long serialVersionUID = 7575447914636815160L;
    private Exception e;

    public ApplicationException(Exception e)
    {
        this.e = e;
    }

    @Override
    public String getMessage()
    {
        return e.getMessage();
    }
}
