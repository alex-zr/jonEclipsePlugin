package ua.com.jon.preference;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import ua.com.jon.Activator;

public class Authorization extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public Authorization() {
		super(GRID);
		setDescription("Authorization");
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	public void init(IWorkbench workbench) {
		Activator
				.getDefault()
				.getPreferenceStore()
				.setDefault(PreferenceConst.LOGIN, PreferenceConst.DEFAULT_LOGIN);
		Activator
				.getDefault()
				.getPreferenceStore()
				.setDefault(PreferenceConst.PASSWORD, PreferenceConst.DEFAULT_PASSWORD);
	}

	@Override
	protected void createFieldEditors() {

		StringFieldEditor login = new StringFieldEditor(PreferenceConst.LOGIN,
				"Login", getFieldEditorParent());
		
//		 StringFieldEditor password = new StringFieldEditor(PreferenceConst.PASSWORD, "Password", getFieldEditorParent());
		
		//Password editor
		StringFieldEditor password = new StringFieldEditor(PreferenceConst.PASSWORD, "Password", getFieldEditorParent()) {

			@Override
			protected void doFillIntoGrid(Composite parent, int numColumns) {
				super.doFillIntoGrid(parent, numColumns);
//				getTextControl().setEchoChar((char)10084); 
				getTextControl().setEchoChar((char)(9785 + (int)(Math.random() * 4)));
				
			}

		};
		// ВКЛ - ВЫКЛ
		// BooleanFieldEditor account = new
		// BooleanFieldEditor(PreferenceConst.USE_ACCOUNT, "Use account",
		// getFieldEditorParent());
		// addField(account);
		//
		// account.setPropertyChangeListener(new IPropertyChangeListener() {
		// @Override
		// public void propertyChange(PropertyChangeEvent event) {
		// System.out.println("propertyChange");
		// login.setEnabled(account.getBooleanValue(), getFieldEditorParent());
		// password.setEnabled(account.getBooleanValue(),
		// getFieldEditorParent());
		// }
		// });
		//
		// login.setEnabled(account.getBooleanValue(), getFieldEditorParent());
		// password.setEnabled(account.getBooleanValue(),
		// getFieldEditorParent());
		login.setEmptyStringAllowed(false);
		login.setErrorMessage(PreferenceConst.LOGIN_ERROR_MESSAGE);
		password.setEmptyStringAllowed(false);
		password.setErrorMessage(PreferenceConst.PASSWORD_ERROR_MESSAGE);
		addField(login);
		addField(password);

	}

}
