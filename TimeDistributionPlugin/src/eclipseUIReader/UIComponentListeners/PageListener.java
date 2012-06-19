package eclipseUIReader.UIComponentListeners;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWorkbenchPage;

import timeDistributionPlugin.MyLogger;



public class PageListener implements IPageListener {
	
	@Override
	public void pageOpened(IWorkbenchPage page) {
		addPartListener(page);
	}
	
	@Override
	public void pageClosed(IWorkbenchPage page) {}
	
	@Override
	public void pageActivated(IWorkbenchPage page) {}
	
	@SuppressWarnings("deprecation")
	static void addPartListener(IWorkbenchPage page){
		//for new added parts
		page.addPartListener(new PartListener());
		
		//for existing parts in this page
		for (IEditorPart part : page.getEditors()) {
			try{
				PartListener.addDocumentListener(part);
			}catch(IllegalArgumentException ex){
				//part was not an editor, therefore not of any interest for  us. Ignore these parts
				MyLogger.logInfo("Ignored part "+part.getTitle()+", was not an editor");
			}
        }
	}
}

