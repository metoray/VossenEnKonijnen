package mirlexpat.voskonijn.controller;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Scale;

import mirlexpat.voskonijn.logic.Simulator;

public class komodoDragonSettings {

	protected Shell shell;

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		shell = new Shell();
		shell.setSize(464, 434);
		shell.setText("SWT Application");
		
		Button btnCheckButton = new Button(shell, SWT.CHECK);
		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnCheckButton.setBounds(29, 148, 93, 16);
		btnCheckButton.setText("Rabbit");
		
		Button btnCheckButton_1 = new Button(shell, SWT.CHECK);
		btnCheckButton_1.setBounds(29, 170, 93, 16);
		btnCheckButton_1.setText("Fox");
		
		Button btnCheckButton_2 = new Button(shell, SWT.CHECK);
		btnCheckButton_2.setBounds(29, 192, 93, 16);
		btnCheckButton_2.setText("Komodovaraan");
		
		Button btnCheckButton_3 = new Button(shell, SWT.CHECK);
		btnCheckButton_3.setBounds(29, 214, 93, 16);
		btnCheckButton_3.setText("Hunter");
		
		Label lblWhatToHunt = new Label(shell, SWT.NONE);
		lblWhatToHunt.setBounds(29, 127, 93, 15);
		lblWhatToHunt.setText("What to hunt?");
		
		Scale scale = new Scale(shell, SWT.NONE);
		scale.setBounds(257, 53, 170, 42);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton.setBounds(10, 361, 75, 25);
		btnNewButton.setText("Accept");
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.setBounds(91, 361, 75, 25);
		btnNewButton_1.setText("Cancel");
		
		Label lblSettingsKomododragon = new Label(shell, SWT.CENTER);
		lblSettingsKomododragon.setBounds(156, 10, 136, 16);
		lblSettingsKomododragon.setText("Settings KomodoDragon");
		
		Label lblMaxAge = new Label(shell, SWT.CENTER);
		lblMaxAge.setBounds(314, 32, 55, 15);
		lblMaxAge.setText("Max Age");
		
		Label lblBreedingAge = new Label(shell, SWT.CENTER);
		lblBreedingAge.setBounds(294, 97, 95, 15);
		lblBreedingAge.setText("Breeding Age");
		
		Scale scale_1 = new Scale(shell, SWT.NONE);
		scale_1.setBounds(257, 119, 170, 42);
		
		Label lblBreedingProbability = new Label(shell, SWT.CENTER);
		lblBreedingProbability.setBounds(257, 167, 170, 15);
		lblBreedingProbability.setText("Breeding Probability");
		
		Scale scale_2 = new Scale(shell, SWT.NONE);
		scale_2.setBounds(257, 188, 170, 42);
		
		Label lblMaxLitterSize = new Label(shell, SWT.CENTER);
		lblMaxLitterSize.setBounds(294, 236, 96, 15);
		lblMaxLitterSize.setText("Max Litter Size");
		
		Scale scale_3 = new Scale(shell, SWT.NONE);
		scale_3.setBounds(257, 257, 170, 42);
		
		Label lblFoodValue = new Label(shell, SWT.CENTER);
		lblFoodValue.setBounds(314, 305, 75, 15);
		lblFoodValue.setText("Food Value");
		
		Scale scale_4 = new Scale(shell, SWT.NONE);
		scale_4.setBounds(257, 326, 170, 42);

	}
}
