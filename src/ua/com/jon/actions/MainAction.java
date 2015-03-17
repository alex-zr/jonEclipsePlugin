package ua.com.jon.actions;

import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import ua.com.jon.domain.Sprint;
import ua.com.jon.service.RemoteService;
import ua.com.jon.service.TaskSelectService;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class MainAction implements IWorkbenchWindowPulldownDelegate {
	private IWorkbenchWindow window;
	private TaskSelectService taskService;
	private RemoteService remoteService;

	/**
	 * The constructor.
	 */
	public MainAction() {
		this.taskService = new TaskSelectService();
		this.remoteService = new RemoteService();
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		System.out.println(action.getStyle());
		String text = getCurrentEditorContent();
		if (text == null) {
			text = "Fooo";
		}

		MessageDialog.openInformation(window.getShell(), "Jon", text);
	}

	public String getCurrentEditorContent() {
		final IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (!(editor instanceof ITextEditor)) {
			return null;
		}
		ITextEditor ite = (ITextEditor) editor;
		IDocument doc = ite.getDocumentProvider().getDocument(ite.getEditorInput());
		return doc.get();
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	@Override
	public Menu getMenu(Control parent) {
		List<Sprint> userSprints = remoteService.getUserSprints("login", "pass");
		
		Menu m = new Menu(parent);
		MenuItem i = new MenuItem(m, SWT.DROP_DOWN);
		Menu menuDebug = new Menu(m.getParent(), SWT.DROP_DOWN);
		Menu m1 = new Menu(m);
		MenuItem i2 = new MenuItem(menuDebug, 4);
		i2.setText("33");
		MenuItem i1 = new MenuItem(m, 4);
		i1.setText("Bar");
//		ExpandItem item0 = new ExpandItem (m, SWT.NONE);
		
		i.setText("Foo");
		
		i.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				taskService.handleSelectTask();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		return m;
	}
}