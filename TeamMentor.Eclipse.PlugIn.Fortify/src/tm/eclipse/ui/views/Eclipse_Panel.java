package tm.eclipse.ui.views;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
//import org.eclipse.swt.widgets.*;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.part.ViewPart;

import tm.eclipse.swt.Add_Composite;
import tm.eclipse.swt.Get_Composite;
import tm.eclipse.swt.Set_Composite;
import tm.eclipse.swt.controls.*;

public class Eclipse_Panel extends ViewPart 
{
	public static final String ID = "tm.eclipse.ui.views.Eclipse_Panel";
	public Composite composite;
	public Add_Composite<Composite> add;
	public Get_Composite<Composite> get;
	public Set_Composite<Composite> set;
	
	//required implementations
	public void createPartControl(Composite parent) 
	{
		composite  = parent;
		add = new Add_Composite<Composite>(parent);
		set = new Set_Composite<Composite>(parent);
		get = new Get_Composite<Composite>(parent);
	}
	public void setFocus() 
	{
	}		
	public void title(final String title)
	{
		UIThreadRunnable.syncExec(composite.getDisplay(), new VoidResult() { public void run()
			{
				Eclipse_Panel.super.setPartName(title);
			}
			});
		
	}
	
	//helper methods
	public Browser_Ex add_Browser()
	{
		return add_WebBrowser();	    	    
	}	
	public Button  add_Button()
	{
		return Button.add_Button(composite, SWT.NONE);	    	    
	}
	public Button  add_Button(int style)
	{
		return Button.add_Button(composite, style);	    	    
	}
	public Button  add_Button(String text)
	{
		return Button.add_Button(composite, SWT.NONE, text);
	}
	public Text    add_Text()
	{
		return Text.add_Text(composite);	    	    
	}
	public Text    add_Text(int style)
	{
		return Text.add_Text(composite, style);	    	    
	}
	public Text    add_Text(String text)
	{
		return Text.add_Text(composite, text);	    	    
	}
	public Text    add_Text_Search()
	{
		return Text.add_Text_Search(composite);	    	    
	}
	public Tree_Ex 	  add_Tree()
	{	
		return Tree_Ex.add_Tree(composite);	  			    	   
	}
	public Tree_Ex 	  add_Tree(int style)
	{	
		return Tree_Ex.add_Tree(composite, style);	  			    	   
	}
	public Browser_Ex add_WebBrowser()
	{
		return Browser_Ex.add_Browser(composite, this);			    	   
	}
	
	
	public Eclipse_Panel clear()
	{
		UIThreadRunnable.syncExec(composite.getDisplay(), new VoidResult() { public void run() 
			{
				for(Control control : controls())
					control.dispose();
			}});
		return this;
	}
	public Eclipse_Panel close()
	{
		UIThreadRunnable.syncExec(composite.getDisplay(), new VoidResult() { public void run() 
			{
				Eclipse_Panel.this.getSite().getPage().hideView(Eclipse_Panel.this);
			}});
		return this;
	}
	public Eclipse_Panel close_InSeconds(final int seconds)
	{
		return close_InMiliSeconds(seconds * 1000);
	}
	public Eclipse_Panel close_InMiliSeconds(final int miliSeconds)
	{
		Thread thread_toClose = new Thread(new Runnable() { public void run()
			{		
				try 
				{
					Thread.sleep(miliSeconds);
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				close();		
			}});
		thread_toClose.start();		
		return this;
	}
	public List<Control> controls()
	{
		return UIThreadRunnable.syncExec(composite.getDisplay(), new Result<List<Control>>() { public List<Control> run() 
					{
						return Arrays.asList(composite.getChildren());
					}});
	}	
	public <T> T 		 control(Class<T> clazz)
	{
		for(Control control : controls())
			if (control.getClass() == clazz)
				return clazz.cast(control);
		return null;		
	}
	
	public Layout 		 layout()
	{
		return UIThreadRunnable.syncExec(composite.getDisplay(), new Result<Layout>() { public Layout run()
			{
				return composite.getLayout();
			}});
	}
	
	public Eclipse_Panel layout(final Layout newLayout)
	{
		UIThreadRunnable.syncExec(composite.getDisplay(), new VoidResult() { public void run()
			{
				composite.setLayoutData(null);
				composite.setLayout(newLayout);
				composite.layout(true);	
			}});
		return this;
	}
	
	public Eclipse_Panel layout_Fill(final Layout newLayout)
	{
		return layout(new FillLayout());
	}
	public Eclipse_Panel layout_Form(final Layout newLayout)
	{
		return layout(new FormLayout());
	}
	public Eclipse_Panel layout_Grid(final Layout newLayout)
	{
		return layout(new GridLayout());
	}
	public Eclipse_Panel layout_Row(final Layout newLayout)
	{
		return layout(new RowLayout());
	}	
	public Eclipse_Panel sleep(int miliseconds)
	{
		try 
		{
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) 
		{ 
			e.printStackTrace();
		}
		return this;
	}
	public Eclipse_Panel refresh()
	{
		UIThreadRunnable.syncExec(composite.getDisplay(), new VoidResult() { public void run() 
					{
						composite.layout(true);
					}});
		return this;
	}
}