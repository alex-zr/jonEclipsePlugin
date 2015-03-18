package ua.com.jon.service;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
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
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.ide.IDE;



public class TaskSelectService {
	
	public void createIfNotExist(IWorkbenchWindow window,String projectName, String pkgName, String className, String code){
		
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		IJavaProject javaProject;
		
		
		if (!project.exists()) {
			javaProject = createJavaProject(project);
		} else {
			javaProject = JavaCore.create(project);
		}
		
		IFolder src = project.getFolder("src");
		
		try {
			IPackageFragment pack = javaProject.getPackageFragmentRoot(src).createPackageFragment(pkgName, false, null);

			String source = "package " + pkgName + ";\n\n\n";
			source += code;
			
			
			ICompilationUnit u = pack.getCompilationUnit(className + ".java");
			if(!u.exists()){
				pack.createCompilationUnit(className + ".java", source, false, null);
			} else {
				//Test message
//				MessageDialog.openInformation(null, "jon", "class exist");
			}
			
			//WIN!!! Открывает и выделяет задание в Package Explorer
			expandPackageExplorerFor(u, window);
			//WIN!!! Открывает окно редактора для выбранного задания
			IFile file = project.getFile(u.getPath().removeFirstSegments(1)); // .removeFirstSegments(1) костыль какой-то 
			openEditorFor(file, window);		

		} catch (JavaModelException e1) {
			e1.printStackTrace();
		}
		
	}
	
	
	public void expandPackageExplorerFor(ICompilationUnit u, IWorkbenchWindow window){
		IViewPart view;
		try {
			view = window.getActivePage().showView( "org.eclipse.jdt.ui.PackageExplorer" );
			view.getSite().getSelectionProvider().setSelection(new org.eclipse.jface.viewers.StructuredSelection(u));
		} catch (PartInitException e1) {
			e1.printStackTrace();
		}
	}
	
	public void openEditorFor(IFile file, IWorkbenchWindow window){
		try{
			   IWorkbenchPage page = window.getActivePage();
//			   HashMap map = new HashMap(); // Нахрена все это было??
//			   map.put(IMarker.LINE_NUMBER, new Integer(5));  // Нахрена все это было??
//			   map.put(IWorkbenchPage.EDITOR_ID_ATTR, "org.eclipse.ui.Editor");  // Нахрена все это было??
			   IMarker marker = file.createMarker(IMarker.TEXT);
//			   marker.setAttributes(map); // Нахрена все это было??
			   
			   //page.openEditor(marker); //2.1 API
			   IDE.openEditor(page, marker); //3.0 API
			   marker.delete();
			}catch(CoreException e){
				e.printStackTrace();
			}
	}
	
	
	@Deprecated
	public void handleSelectTask() {
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject("test");
		IJavaProject javaProject;
		if (!project.exists()) {
			javaProject = createJavaProject(project);
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
			
			
			
//			IWorkbenchPage page = window.getActivePage();
//			IFile file = u.get
//			IEditorDescriptor desc = PlatformUI.getWorkbench().
//			        getEditorRegistry().getDefaultEditor(file.getName());
//			try {
//				page.openEditor(new FileEditorInput(file), desc.getId());
//			} catch (PartInitException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
			
			
			
			
			
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

	private IJavaProject createJavaProject(IProject project) {
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
	
	private IFolder mkdir(String name, IProject project) throws CoreException {
		IFolder folder = project.getFolder(name);
		folder.create(false, true, null);
		return folder;
	}
}
