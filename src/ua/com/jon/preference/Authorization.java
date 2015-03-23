package ua.com.jon.preference;


import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import ua.com.jon.Activator;

public class Authorization extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public Authorization() {
		super(GRID);
		setDescription("Athorization");
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}
	
	@Override
	public void init(IWorkbench workbench) {
		
	}

	@Override
	protected void createFieldEditors() {
		
		
		StringFieldEditor login = new StringFieldEditor("login",       "Login   ", getFieldEditorParent());
		StringFieldEditor password = new StringFieldEditor("password", "Password", getFieldEditorParent());
		
		// ВКЛ - ВЫКЛ
		BooleanFieldEditor account = new BooleanFieldEditor("account", "Use account", getFieldEditorParent());
		account.setPropertyChangeListener(new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				login.setEnabled(account.getBooleanValue(), getFieldEditorParent());
				password.setEnabled(account.getBooleanValue(), getFieldEditorParent());
			}
		});
		
		login.setEnabled(account.getBooleanValue(), getFieldEditorParent());
		password.setEnabled(account.getBooleanValue(), getFieldEditorParent());
		
		addField(login);
		addField(password);

	} 

}
