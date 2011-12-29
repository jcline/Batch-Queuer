

public class Credentials
{
	public String user,
		pass;

	public Credentials(String u, String p)
	{
		user = u;
		pass = p;
	}

	public Credentials(String u, char[] p)
	{
		user = u;
		pass = new String(p);
	}

}
