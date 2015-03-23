package ua.com.jon.preference;


import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import ua.com.jon.Activator;

public class Authorization extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public Authorization() {
		super(GRID);
		setDescription("Authorization");
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	} 
	
	@Override
	public void init(IWorkbench workbench) {
		
	}

	@Override
	protected void createFieldEditors() {
		
		
		StringFieldEditor login = new StringFieldEditor(PreferenceConst.LOGIN, "Login", getFieldEditorParent());
		StringFieldEditor password = new StringFieldEditor(PreferenceConst.PASSWORD, "Password", getFieldEditorParent());
		
		// ВКЛ - ВЫКЛ
//		BooleanFieldEditor account = new BooleanFieldEditor(PreferenceConst.USE_ACCOUNT, "Use account", getFieldEditorParent());
//		addField(account);
//		
//		account.setPropertyChangeListener(new IPropertyChangeListener() {
//			@Override
//			public void propertyChange(PropertyChangeEvent event) {
//				System.out.println("propertyChange");
//				login.setEnabled(account.getBooleanValue(), getFieldEditorParent());
//				password.setEnabled(account.getBooleanValue(), getFieldEditorParent());
//			}
//		});
//		
//		login.setEnabled(account.getBooleanValue(), getFieldEditorParent());
//		password.setEnabled(account.getBooleanValue(), getFieldEditorParent());
		
		addField(login);
		addField(password);
		

	} 

}
