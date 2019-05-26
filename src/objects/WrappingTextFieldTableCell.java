package objects;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.DefaultStringConverter;

public class WrappingTextFieldTableCell<Object> extends TextFieldTableCell<Object, String> {

	private final Text cellText; // text that will be displayed in the cell

	public WrappingTextFieldTableCell() {
		super(new DefaultStringConverter()); // i think this converts the string in the textfield to a task object
		this.cellText = createText();
	}

	@Override
	public void cancelEdit() { // when you are done editing the textfield
		super.cancelEdit();
		setGraphic(cellText);
	}

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (!isEmpty() && !isEditing()) {
			setGraphic(cellText);
		}
	}

	private Text createText() {
		Text text = new Text(); // creating a new text object
		text.wrappingWidthProperty().bind(widthProperty()); // binding the width property of the text to the width
															// property of the tablecell
		text.textProperty().bind(itemProperty()); // whatever is entered into the cell is the value of the text
		return text;
	}
}
