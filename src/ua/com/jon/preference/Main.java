package ua.com.jon.preference;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import ua.com.jon.Activator;

public class Main extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	public Main() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Project preferences");
	}

	@Override
	public void init(IWorkbench workbench) {
		Activator.getDefault().getPreferenceStore().setDefault(PreferenceConst.PROJECT, PreferenceConst.DEFAULT_PROJECT_NAME);
		
	}

	@Override
	protected void createFieldEditors() {
		StringFieldEditor projectName = new StringFieldEditor(PreferenceConst.PROJECT, "Project name", getFieldEditorParent());
		projectName.setEmptyStringAllowed(false);
		projectName.setErrorMessage(PreferenceConst.PROJECT_NAME_ERROR_MESSAGE);
		addField(projectName);
	}

}
