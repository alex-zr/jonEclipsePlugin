package ua.com.jon.service;

import java.io.IOException;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class ConsoleWriterService {
	
	public void write(String consoleName, String text) {
		
		MessageConsole myConsole = createIfNotExist(consoleName);
        //myConsole.activate(); // Не понятно нахрена оно..

        ConsolePlugin.getDefault().getConsoleManager().showConsoleView(myConsole);
        
        final MessageConsoleStream stream = myConsole.newMessageStream();
        stream.setActivateOnWrite(true);
        //Цветной вывод в консоль RGB
        stream.setColor(getColor(0, 0, 0));
        
        stream.println(text);
        
        try {
			stream.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**Создает новую консоль только в том случае, если нет открытой с таким же именем*/
	public MessageConsole createIfNotExist(String name) {
		MessageConsole console;
		IConsole [] cons = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
        for(IConsole c : cons){
        	if(name.equals(c.getName())){
        		console = (MessageConsole)c;
        		return console;
        	}
        }
        console = new MessageConsole(name, null);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ console });
		return console;
	}
	
	public Color getColor(int r,int g, int b) {
		  RGB rgb=new RGB(r,g,b);
		  return new Color(Display.getCurrent(),rgb);
	}
}
