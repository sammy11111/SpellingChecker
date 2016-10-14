package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.Controller;
import util.Utils;

/**
 * the implementation for the UI interface
 * 
 * @author Sammy Ibrahim
 *
 */
public class UIImpl implements UI {

	private Controller controller;

	/**
	 * no args constructor
	 */
	public UIImpl() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.UI#init(java.lang.String, java.lang.String)
	 */
	@Override
	public void init(String dictionaryFileName, String textFileName) throws IOException {
		controller.loadDictionary(dictionaryFileName);
		controller.loadTextFile(textFileName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.UI#initUI()
	 */
	public void initUI() {
		JFrame frame = new JFrame("Speller Checker");

		JPanel panel0 = new JPanel(new FlowLayout());
		panel0.setPreferredSize(new Dimension(300, 50));
		JPanel panel1 = new JPanel(new FlowLayout());
		panel1.setPreferredSize(new Dimension(300, 250));
		JPanel panel2 = new JPanel(new FlowLayout());
		panel2.setPreferredSize(new Dimension(300, 150));

		JLabel label = new JLabel("No suggestions!");

		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(400, 200));
		textArea.setText(controller.getRepo().getSpellText().getContent());

		DefaultListModel<String> model = new DefaultListModel<String>();
		JList textList = new JList(model);
		textList.setPreferredSize(new Dimension(150, 100));

		JButton button = new JButton();
		button.setText("Show suggestions!");
		button.setPreferredSize(new Dimension(200, 40));

		JButton userSuggestionsButton = new JButton();
		userSuggestionsButton.setText("Add more suggestions!");
		userSuggestionsButton.setPreferredSize(new Dimension(200, 40));

		JButton saveUserSuggestionsButton = new JButton();
		saveUserSuggestionsButton.setText("Save your suggestions!");
		saveUserSuggestionsButton.setPreferredSize(new Dimension(200, 40));

		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(200, 40));

		panel1.add(textArea);
		panel1.add(button);
		panel2.add(userSuggestionsButton);

		userSuggestionsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						panel2.add(textField);
						panel2.add(saveUserSuggestionsButton);
						panel2.remove(userSuggestionsButton);
						frame.validate();
						frame.repaint();
					}
				});
			}
		});

		saveUserSuggestionsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {

						// do operations only if a word is selected
						if (textArea.getSelectedText() != null) {
							String userText = textField.getText();
							String selectedWord = textArea.getSelectedText();

							// replace text in UI
							textArea.setText(Utils.replaceWordInText(userText, selectedWord, textArea.getText()));

							// update the text Spell text content
							controller.getRepo().getSpellText().setContent(textArea.getText());

							if (controller.findSimilarWords(userText).isEmpty()) {
								List<String> suggestions = controller.suggestionsForWord(selectedWord);
								suggestions.add(userText);
								controller.proposeNewSuggestions(selectedWord, suggestions);
							}
						}
					}
				});
			}
		});

		textField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("Word is typed here.....");
			}
		});

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (textArea.getSelectedText() != null) {// do
																	// operations
																	// only if a
																	// word is
																	// selected
							if (model != null && model.getSize() > 0) {
								model.clear();
							}
							List<String> suggestionsList = controller.findSimilarWords(textArea.getSelectedText());
							if (suggestionsList != null && suggestionsList.size() > 0) {
								for (String element : suggestionsList) {
									model.addElement(element);
								}
								panel1.add(textList);
								panel1.remove(label);

							} else {
								panel1.remove(textList);
								panel1.add(label);

							}
							frame.validate();
							frame.repaint();

						}
					}
				});

			}
		});

		textList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					System.out.println(e.getLastIndex());
					System.out.println(model.getElementAt(e.getLastIndex()));
					textArea.setText(Utils.replaceWordInText(model.getElementAt(e.getLastIndex()),
							textArea.getSelectedText(), textArea.getText()));
					// update the text Spell text content
					controller.getRepo().getSpellText().setContent(textArea.getText());

				}
				System.out.println("f:" + e.getFirstIndex() + " l:" + e.getLastIndex());

			}
		});
		frame.add(panel0, BorderLayout.NORTH);
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);

		frame.setSize(800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.UI#getController()
	 */
	public Controller getController() {
		return controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.UI#setController(controller.Controller)
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

}
