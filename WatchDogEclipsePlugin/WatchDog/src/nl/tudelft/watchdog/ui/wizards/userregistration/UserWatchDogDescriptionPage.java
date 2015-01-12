package nl.tudelft.watchdog.ui.wizards.userregistration;

import nl.tudelft.watchdog.ui.util.BrowserOpenerSelection;
import nl.tudelft.watchdog.ui.util.UIUtils;
import nl.tudelft.watchdog.ui.wizards.FinishableWizardPage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

/**
 * A page that contains the WatchDog project description.
 */
public class UserWatchDogDescriptionPage extends FinishableWizardPage {

	private Link linkedText;
	private Label welcomeText;

	/** Constructor. */
	protected UserWatchDogDescriptionPage() {
		super("WatchDog Description");
	}

	@Override
	public void createControl(Composite parent) {
		setTitle("What is WatchDog? (2/3)");
		setDescription("Help Science, and win prizes along the way");

		Composite topComposite = UIUtils.createFullGridedComposite(parent, 1);
		createWatchDogDescription(topComposite);
		createLogoRow(topComposite);

		setControl(topComposite);
		setPageComplete(true);
	}

	private Composite createWatchDogDescription(Composite topContainer) {

		Composite composite = UIUtils
				.createFullGridedComposite(topContainer, 1);

		welcomeText = UIUtils.createBoldLabel("", SWT.WRAP, composite);

		linkedText = new Link(composite, SWT.WRAP);
		linkedText.setText("");
		linkedText.addSelectionListener(new BrowserOpenerSelection());

		return composite;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			welcomeText
					.setText("WatchDog is a free, non-commercial Eclipse plugin from TU Delft that assesses how developers make software.");
			welcomeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			welcomeText.getParent().layout();
			welcomeText.getParent().update();

			String welcomeText = "\nIt measures how you write code and tests, but never what you write! And when you run tests. Our promise: <a href=\"http://www.testroots.org/testroots_watchdog.html#details\">Your data</a> is strictly numerical, and we never do anything bad with it.\n\nWhat's in it for you? Super-amazing <a href=\"http://www.testroots.org/testroots_watchdog.html#prizes\">prizes,</a> a report on your personal development behaviour and a truly appreciated contribution to science! :-).\n";

			linkedText.setText(welcomeText);
			linkedText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			linkedText.getParent().layout();
			linkedText.getParent().update();
			linkedText.getParent().getParent().layout();
			linkedText.getParent().getParent().update();
		}
	}

	/** Creates a horizontal separator. */
	public void createSeparator(Composite parent) {
		Label separator = UIUtils.createLabel("", SWT.SEPARATOR
				| SWT.HORIZONTAL | SWT.FILL, parent);
		GridData layoutData = UIUtils.createFullGridUsageData();
		layoutData.horizontalSpan = 2;
		separator.setLayoutData(layoutData);
	}

	@Override
	public boolean canFinish() {
		return false;
	}

}