package tm.eclipse.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.internal.registry.FileEditorMapping;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;

import tm.lang.Reflection;

public class Registry 
{	
	public EclipseAPI eclipse;
	public Display    display;
	
	public Registry(EclipseAPI eclipse)
	{
		this.eclipse = eclipse;
		this.display = eclipse.display;
	}
	public IExtensionRegistry extensionRegistry()
	{	
		return UIThreadRunnable.syncExec(display,new Result<IExtensionRegistry>() { public IExtensionRegistry run() 	
			{				
				return Platform.getExtensionRegistry();
			}});
	}
	
	public List<IExtensionPoint> extensions()
	{
		IExtensionRegistry extensionRegistry = extensionRegistry();
		return Arrays.asList(extensionRegistry.getExtensionPoints());
	}
	
	public List<String> namespaces()
	{
		IExtensionRegistry extensionRegistry = extensionRegistry();
		return Arrays.asList(extensionRegistry.getNamespaces());
	}
	
	@SuppressWarnings("restriction")
	public org.eclipse.ui.internal.WorkbenchPlugin workbenchPlugin()
	{
		return org.eclipse.ui.internal.WorkbenchPlugin.getDefault();
	}
	@SuppressWarnings("restriction")
	public IViewRegistry viewRegistry()
	{
		return workbenchPlugin().getViewRegistry();
	}
	
	@SuppressWarnings("restriction")
	public List<IEditorDescriptor> editors()
	{
		List<IEditorDescriptor> editors = new ArrayList<IEditorDescriptor>(); 
		for(IFileEditorMapping fileEditorMapping : workbenchPlugin().getEditorRegistry().getFileEditorMappings())
			for(IEditorDescriptor editorDescriptor : fileEditorMapping.getEditors())
				editors.add(editorDescriptor);
		return editors;
	}
	
	@SuppressWarnings("restriction")
	public List<IViewDescriptor> views()
	{
		IViewDescriptor[]  views = workbenchPlugin().getViewRegistry().getViews();
		return Arrays.asList(views);
	}
	public List<String> views_Ids()
	{
		return views("getId"); 
	}
	public List<String> views_Descriptions()
	{
		return views("getDescription"); 
	}
	public List<String> views_Labels()
	{
		return views("getLabel"); 
	}
			
	public List<String> views(String getterMethodName)
	{
		//Question: is there a better way to do this in Java? In C# I could had used Lambda methods but here it seems that using the reflection trick bellow is the only option
		List<String> ids = new ArrayList<String>();
		for(IViewDescriptor viewDescriptor : views())
		{
			Reflection reflection = new Reflection(viewDescriptor);			
			ids.add(reflection.invoke(String.class,getterMethodName));
		}
		return ids;
	}
	
	
}