package ua.com.jon.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import ua.com.jon.Activator;

public class Test extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	
	public Test() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Description");
	}

	public void createFieldEditors() {

		addField(new DirectoryFieldEditor("DirectoryFieldEditor",
				"&Directory preference:", getFieldEditorParent()));
		addField(new BooleanFieldEditor("BooleanFieldEditor",
				"&An example of a boolean preference", getFieldEditorParent()));

		addField(new RadioGroupFieldEditor("RadioGroupFieldEditor",
				"An example of a multiple-choice preference", 1,
				new String[][] { { "&Choice 1", "choice1" },
						{ "C&hoice 2", "choice2" } }, getFieldEditorParent()));

		addField(new StringFieldEditor("StringFieldEditor",
				"A &text preference:", getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {

	}

	@Override
	protected void performApply() {

		super.performApply();
		System.out.println("Apply");
	}
}
