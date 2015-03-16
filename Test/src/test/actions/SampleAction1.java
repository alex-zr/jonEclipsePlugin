package test.actions;

//import org.eclipse.core.resources.IFolder;
//import org.eclipse.core.resources.IProjectDescription;
//import org.eclipse.core.resources.IWorkspaceRoot;
//import org.eclipse.core.resources.ResourcesPlugin;
//import org.eclipse.core.runtime.CoreException;
//import org.eclipse.jdt.core.IJavaProject;
//import org.eclipse.jdt.core.JavaCore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction1 implements IWorkbenchWindowPulldownDelegate {
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public SampleAction1() {
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
		final IEditorPart editor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (!(editor instanceof ITextEditor))
			return null;
		ITextEditor ite = (ITextEditor) editor;
		IDocument doc = ite.getDocumentProvider().getDocument(
				ite.getEditorInput());
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

	public Menu getMenu(Control parent) {
		Menu m = new Menu(parent);
		MenuItem i = new MenuItem(m, 4);
		
		i.setText("Fooo");
		
		i.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IProject project = root.getProject("test");
				IJavaProject javaProject;
				if (!project.exists()) {
					javaProject = newJavaProject(project);
				} else {
					javaProject = JavaCore.create(project);
				}
				
				IFolder src = project.getFolder("src");
				try {
					IPackageFragment pack = javaProject.getPackageFragmentRoot(src).createPackageFragment("testPackage", false, null);
					String name = "Test";
					
					String source = "package " + pack.getElementName() + ";\n\n\n";
					source += "public class " + name + "{\n\n" +
					"\tpublic static void main(String [] args){\n\n" +
							"\t\tSystem.out.println(\"Hello World\");\n" +
					"\t}\n}";
					
					
					ICompilationUnit u = pack.getCompilationUnit(name + ".java");
					if(!u.exists()){
						pack.createCompilationUnit(name + ".java", source, false, null);
					}
					
					
					
//					IWorkbenchPage page = window.getActivePage();
//					IFile file = u.get
//					IEditorDescriptor desc = PlatformUI.getWorkbench().
//					        getEditorRegistry().getDefaultEditor(file.getName());
//					try {
//						page.openEditor(new FileEditorInput(file), desc.getId());
//					} catch (PartInitException e2) {
//						// TODO Auto-generated catch block
//						e2.printStackTrace();
//					}
					
					
					
					
					
					//Вывод в консоль
					MessageConsole myConsole = new MessageConsole("Jon.com.ua", null);
			        myConsole.activate();
			        ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ myConsole });
			        ConsolePlugin.getDefault().getConsoleManager().showConsoleView(myConsole);
			        final MessageConsoleStream stream = myConsole.newMessageStream();
			        stream.setActivateOnWrite(true);
			        stream.println("Hi there!");
			        
			        try {
						stream.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        //Вывод в консоль КОНЕЦ
			        
				} catch (JavaModelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("Default");

			}
		});
		return m;
	}


	public IJavaProject newJavaProject(IProject project) {
		IJavaProject javaProject =  null;
		try {
			project.create(null);
			project.open(null);
			IProjectDescription description = project.getDescription();
			description.setNatureIds(new String[] { JavaCore.NATURE_ID });
			project.setDescription(description, null);

			javaProject = JavaCore.create(project);
			javaProject.setOutputLocation(mkdir("bin", project).getFullPath(),	null);


			IFolder src = mkdir("src", project);
			IClasspathEntry[] entriess = new IClasspathEntry[2];
			
			IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(src);
			entriess[0] = JavaCore.newSourceEntry(root.getPath());
			entriess[1] = JavaRuntime.getDefaultJREContainerEntry();
			javaProject.setRawClasspath(entriess, null);
			
		} catch (CoreException ex) {
			ex.printStackTrace();
		}
		return javaProject;
	}

	public IFolder mkdir(String name, IProject project) throws CoreException {
		IFolder folder = project.getFolder(name);
		folder.create(false, true, null);
		return folder;
	}
}