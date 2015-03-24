package ua.com.jon.actions;

import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.texteditor.ITextEditor;

import ua.com.jon.Activator;
import ua.com.jon.domain.Sprint;
import ua.com.jon.domain.Task;
import ua.com.jon.preference.PreferenceConst;
import ua.com.jon.service.ConsoleWriterService;
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
	private ConsoleWriterService consoleService;


	/**
	 * The constructor.
	 */
	public MainAction() {
		this.taskService = new TaskSelectService();
		this.remoteService = new RemoteService();
		this.consoleService = new ConsoleWriterService();
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
//		System.out.println(action.getStyle());
		String text = getCurrentEditorContent();
		
		
		if (text == null) {
			text = "Fooo";
		}

		//MessageDialog.openInformation(window.getShell(), "Jon", text);
		
		//Работа с конфигом
		String login = Activator.getDefault().getPreferenceStore().getString(PreferenceConst.LOGIN);
		String password = Activator.getDefault().getPreferenceStore().getString(PreferenceConst.PASSWORD);
		//Вывод в консоль
		consoleService.write("You WIN!!", login + " " + password + " 100");
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
		//Получение данных из конфига
		String login = Activator.getDefault().getPreferenceStore().getString(PreferenceConst.LOGIN);
		String password = Activator.getDefault().getPreferenceStore().getString(PreferenceConst.PASSWORD);
		
		List<Sprint> userSprints = remoteService.getUserSprints(login, password);
		
		Menu m = new Menu(parent);
		
		for(Sprint sprint : userSprints) {
			//Просто самый очевидный способ создания выпадающего меню....
			MenuItem item = new MenuItem(m, SWT.CASCADE);//Обязательно CASCADE !
			
			item.setText(sprint.getName());
			
			Menu sub = new Menu(item);//Создание контейнера подменю
			
			item.setMenu(sub);//Связывание пункта меню и контейнера для подменю
			
			for(final Task task : sprint.getTasks()) {
				//Создание понктов подменю с указанием контейнера
				MenuItem subItem = new MenuItem(sub, SWT.CASCADE);// Не обязательно CASCADE..
				subItem.setText(task.getName());
				
				subItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						String projectName = Activator.getDefault().getPreferenceStore().getString(PreferenceConst.PROJECT);
						taskService.createIfNotExist(window, projectName, task.getSprintName(), task.getName(), task.getCode());
					}
				});
			}
			
			
		}	
		
		
		new MenuItem(m, SWT.SEPARATOR); //Разделитель списка
		MenuItem preference = new MenuItem(m, SWT.CASCADE);
		//Пункт меню открывающий окно настроек
		preference.setText("preferences");
		
		Menu prefMenu = new Menu(preference);
		preference.setMenu(prefMenu);
		
		MenuItem mainPref = new MenuItem(prefMenu, SWT.PUSH);
		mainPref.setText("Project");
		mainPref.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				PreferencesUtil.createPreferenceDialogOn(window.getShell(), PreferenceConst.PROJECT_PREFERENCE_ID, null, null).open();
			}
		});
		MenuItem authPref = new MenuItem(prefMenu, SWT.PUSH);
		authPref.setText("Authorization");
		
		authPref.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				PreferencesUtil.createPreferenceDialogOn(window.getShell(), PreferenceConst.AUTH_PREFERENCE_ID, null, null).open();
			}
		});
		return m;
	}
}