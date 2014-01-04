package tm.eclipse.swt.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;

import tm.lang.Reflection;


public class Button extends org.eclipse.swt.widgets.Button
{	
	public static Button add(Composite target)
	{
		return add_Button(target);
	}
	public static Button add(Composite target, String text)
	{
		return add_Button(target).text(text);
	}
	
	public static Button add_Button(final Composite target)
	{
		return add_Button(target, SWT.PUSH);
	}
	public static Button add_Button(final Composite target, final int style)
	{	
		return UIThreadRunnable.syncExec(target.getDisplay(),new Result<Button>() { public Button run() 
					{
						Button button = new Button(target,style);
						target.layout(true);
						return button;
					}});
		
	}
	public static Button add_Button(final Composite target, final int style, String text)
	{		
		Button button = add_Button(target, style);
		button.setText(text);
		return button;
	}

	public static Button add_Button(final Composite target, String text)
	{
		return add_Button(target, SWT.None, text);
	}	
		
	public Display 		_display;
public SWTBotButton swtBotButton;
	public Reflection 	reflection;
	
	public Button(Composite parent, int style) 
	{		
		super(parent, style);
		swtBotButton = new SWTBotButton(this);
		_display = parent.getDisplay();			// we need to store this in case there are multiple ones
		reflection = new Reflection(this);
	}	
	
	protected void checkSubclass()
	{}
	
	
	//extra methods
	
	public Button click()
	{		
		this.reflection.invoke_onSuperClass("click");
		return this;
	}
	public Button disable()
	{
		return enabled(false);
	}	
	public Boolean disabled()
	{
		return enabled() == false;
	}	
	public Button enable()
	{
		return enabled(true);
	}
	
	public Boolean enabled()
	{
		return UIThreadRunnable.syncExec(_display,new Result<Boolean>() { public Boolean run() 
		{
			return Button.super.isEnabled();			
		}});
	}
	public Button enabled(final boolean value)
	{
		UIThreadRunnable.syncExec(_display,new VoidResult() { public void run() 
		{
			Button.super.setEnabled(value);			
		}});
		return this;
	}
	public String getText()
	{
		return UIThreadRunnable.syncExec(_display,new Result<String>() { public String run() 
		{
			return Button.super.getText();			
		}});		
	}
	public Button onClick(final Runnable runnable)
	{
		if (runnable != null)
			UIThreadRunnable.syncExec(_display,new VoidResult() { public void run() 
				{
					Button.this.addListener(SWT.Selection, new Listener() { public void handleEvent(Event event) 
					{
						runnable.run();
					}});
				}});
	
		return this; 
	}
	public void   setImage(final Image image)
	{
		UIThreadRunnable.syncExec(_display,new VoidResult() { public void run() 
		{
			Button.super.setImage(image);			
		}});
	}
	public void   setText(final String text)
	{
		if (text!=null)
			UIThreadRunnable.syncExec(_display,new VoidResult() { public void run() 
			{
				Button.super.setText(text);
				Button.this.getParent().layout(true);  // need to see if there is a better to way to refresh the contents				
			}});
	}
	public String text()
	{
		return getText();
	}
	public Button text(String text)
	{
		setText(text);
		return this;
	}
}
