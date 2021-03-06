package nl.tudelft.watchdog.intellij.ui.wizards;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A single registration step that has two options:
 * 1. The user already has a registration, for which they have to enter their ID.
 * 2. The user has to create a registration in the {@link #getRegistrationPanel()}.
 */
abstract class RegistrationStep extends WizardStep {

    private final RegistrationWizard wizard;

    private Container panel;

    private JPanel dynamicContent;
    private boolean hasValidUserId = false;

    RegistrationStep(RegistrationWizard wizard) {
        this.wizard = wizard;
    }

    @Override
    void _initWithPanel(Container panel) {
        this.panel = panel;

        panel.add(createUserRegistrationIntroduction());
        panel.add(createUserIsRegisteredQuestion());

        this.recreateDynamicContent(new JPanel());
    }

    private void recreateDynamicContent(Component component) {
        this.dynamicContent = new JPanel();
        this.dynamicContent.setLayout(new BoxLayout(this.dynamicContent, BoxLayout.Y_AXIS));
        this.dynamicContent.add(component);

        Component glue = Box.createVerticalGlue();
        glue.setPreferredSize(new Dimension(0, Short.MAX_VALUE));
        this.dynamicContent.add(glue);

        this.panel.add(this.dynamicContent);
    }

    @Override
    boolean isFinishedWithStep() {
        return hasValidUserId;
    }

    private Component createUserRegistrationIntroduction() {
        Container container = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 3));

        container.add(new JLabel(this.obtainHeaderText()));

        return container;
    }

    abstract String obtainHeaderText();

    private Component createUserIsRegisteredQuestion() {
        Container container = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 3));

        container.add(new JLabel("Do you want to create a new WatchDog " + this.getRegistrationType() + " registration?"));

        ButtonGroup buttons = new ButtonGroup();

        JRadioButton yes = new JRadioButton("Yes");
        container.add(yes);
        buttons.add(yes);
        whenSelectedCreatePanelAndUpdateUI(yes, getRegistrationPanel());

        JRadioButton no = new JRadioButton("No");
        container.add(no);
        buttons.add(no);
        whenSelectedCreatePanelAndUpdateUI(no, getIdInputPanel());

        return container;
    }

    abstract String getRegistrationType();

    abstract Function<Consumer<Boolean>, JPanel> getIdInputPanel();

    abstract Function<Consumer<Boolean>,JPanel> getRegistrationPanel();

    private void whenSelectedCreatePanelAndUpdateUI(JRadioButton button, Function<Consumer<Boolean>, JPanel> panelConstructor) {
        button.addItemListener(itemEvent -> {
            if (button.isSelected()) {
                this.panel.remove(this.dynamicContent);

                // Create the panel with the new container and attach a listener for when the
                // panel has finished obtaining all the required information
                this.recreateDynamicContent(panelConstructor.apply(hasValidUserId-> {
                    this.hasValidUserId = hasValidUserId;
                    this.dynamicContent.updateUI();
                    wizard.updateButtons();
                }));

                this.panel.revalidate();
                this.panel.repaint();
            }
        });
    }

}
