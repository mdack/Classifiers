package presentation.command.commandFactory;

import presentation.command.Command;

public abstract class CommandFactory {

	private static CommandFactory instance;

	public static CommandFactory getInstance(Boolean esCommandDAO) {
		if(instance == null){
			instance = new CommandFactoryImp();
		}
		
		return instance;
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @return
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public abstract Command getCommand(int CommandName);

	}