import java.lang.*;
import java.util.*;
import java.io.*;
import java.security.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Login
{
	static int count = 3;
	public Login()
	{
		JFrame f = new JFrame("Duplicate File Removal");
		JButton submit = new JButton("Submit");
		JTextField userText = new JTextField();
		JPasswordField passText = new JPasswordField(15);
		JLabel userName = new JLabel("UserName");
		JLabel passWord = new JLabel("Password");
		JLabel warning = new JLabel(count+" attempt to login");
		ImageIcon img = new ImageIcon("images/c.jpg");
		JLabel im = new JLabel(img);

		f.setSize(500,500);
		im.setBounds(0,0,500,500);
		userName.setBounds(100,120,100,40);
		passWord.setBounds(100,200,100,40);
		userText.setBounds(300,120,150,30);
		passText.setBounds(300,200,150,30);
		warning.setBounds(100,250,250,40);
		submit.setBounds(100,300,100,40);

		f.add(im);

		im.add(userName);
		im.add(passWord);
		im.add(userText);
		im.add(passText);
		im.add(warning);
		im.add(submit);

		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String user = userText.getText();
				String pass = passText.getText();
				if(user.equals("mayur") && pass.equals("mayur@2402"))
				{
					f.setVisible(false);
					new AcceptFolder();
				}
				else if(count == 1)
				{
					f.setVisible(false);
				}
				else
				{
					count--;
					f.setVisible(false);
					new Login();	
				}
			}
		});
	}
}

class AcceptFolder
{
	public AcceptFolder()
	{

		JFrame f = new JFrame("Duplicate File Removal");
		JButton acceptButton = new JButton("Check Duplicate");
		JTextField folderField = new JTextField();
		JLabel folder = new JLabel("Folder Name");
		ImageIcon img = new ImageIcon("images/c.jpg");
		JLabel im = new JLabel(img);

		f.setSize(500,500);
		im.setBounds(0,0,500,500);
		folder.setBounds(100,100,100,40);
		folderField.setBounds(100,200,150,30);
		acceptButton.setBounds(100,300,150,30);

		f.add(im);
		im.add(acceptButton);
		im.add(folderField);
		im.add(folder);
		
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		acceptButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				OpenDirectory OD = new OpenDirectory(folderField.getText());
				f.setVisible(false);
				new ShowDeleteFiles(OpenDirectory.deletecount);
			}
		});
	}
}


class OpenDirectory
{
	static int deletecount = 0;
	public String name;
	public FileInputStream FIS= null;
	LinkedList <String> check = new LinkedList<String>();
	public OpenDirectory(String name)
	{
		try
		{
			this.name = name;
			File dir = new File(this.name);
			if(dir.isDirectory())
			{
				travelFile(this.name);
			}
			else
			{
				System.out.println(dir+" is not Directory");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public void travelFile(String dir)
	{
		File d = new File(dir);
		File[] fName = d.listFiles();

		for(File name : fName)
		{
			if(name.isFile())
			{
				checkSum(name);
			}
		}
	}
	public void checkSum(File name)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			FIS = new FileInputStream(name);
			
			byte[] arr = new byte[(int)name.length()];
			FIS.read(arr);
			md.update(arr);

			byte[] digest = md.digest();

			StringBuffer hexString = new StringBuffer();
      
      			for(int i = 0;i<digest.length;i++)
			{
        			hexString.append(Integer.toHexString(0xFF & digest[i]));
      			}
      			String check_sum = hexString.toString();
      			duplicateFile(check_sum,name);
      		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public void duplicateFile(String name,File fName)
	{
		boolean d = false;
		if(check.contains(name))
		{
			if(fName.delete())
			{
				deletecount++;
			}
		}
		check.add(name);
	}
}

class ShowDeleteFiles
{
	public ShowDeleteFiles(int count)
	{
		String c = count+" files Deleted";
		JLabel show = new JLabel(c);
		JFrame f = new JFrame("Duplicate File Removal");
		ImageIcon img = new ImageIcon("images/c.jpg");
		JLabel im = new JLabel(img);

		f.setSize(500,500);
		im.setBounds(0,0,500,500);

		show.setBounds(100,100,200,100);
		show.setFont(new Font("Arial",Font.PLAIN,15));

		f.add(im);
		im.add(show);

		f.setLayout(null);
		f.setVisible(true);	
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
class Main
{
	public static void main(String[] args) 
	{
		new Login();		
	}
}
