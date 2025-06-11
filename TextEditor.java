class Action {
    String type; // "INSERT", "DELETE", "REPLACE"
    String text;
    int position;
    String previousText; // for undo
    Action next;
    Action prev;

    public Action(String type, String text, int position, String previousText) {
        this.type = type;
        this.text = text;
        this.position = position;
        this.previousText = previousText;
        this.next = null;
        this.prev = null;
    }
}

public class TextEditor {
    private StringBuilder currentText;
    private Action head;
    private Action tail;
    private Action currentAction;

    public TextEditor() {
        this.currentText = new StringBuilder();
        this.head = null;
        this.tail = null;
        this.currentAction = null;
    }

    public void insertText(String text, int position) {
        if (position < 0 || position > currentText.length()) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
        String previousText = currentText.toString();
        currentText.insert(position, text);
        Action action = new Action("INSERT", text, position, previousText);
        addAction(action);
    }

    public void deleteText(int start, int length) {
        if (start < 0 || start + length > currentText.length()) {
            throw new IndexOutOfBoundsException("Invalid range");
        }
        String previousText = currentText.toString();
        currentText.delete(start, start + length);
        Action action = new Action("DELETE", currentText.substring(start, start + length), start, previousText);
        addAction(action);
    }

    public void replaceText(int start, int length, String newText) {
        if (start < 0 || start + length > currentText.length()) {
            throw new IndexOutOfBoundsException("Invalid range");
        }
        String previousText = currentText.toString();
        currentText.replace(start, start + length, newText);
        Action action = new Action("REPLACE", newText, start, previousText);
        addAction(action);
    }

    private void addAction(Action action) {
        if (head == null) {
            head = action;
            tail = action;
        } else {
            tail.next = action;
            action.prev = tail;
            tail = action;
        }
        currentAction = action;
    }

    public void undo() {
        if (currentAction == null) return;
        if (currentAction.type.equals("INSERT")) {
            currentText.delete(currentAction.position, currentAction.position + currentAction.text.length());
        } else if (currentAction.type.equals("DELETE")) {
            currentText.insert(currentAction.position, currentAction.text);
        } else if (currentAction.type.equals("REPLACE")) {
            currentText.replace(currentAction.position, currentAction.position + currentAction.previousText.length(), currentAction.previousText);
        }
        currentAction = currentAction.prev;
    }

    public void redo() {
        if (currentAction == null || currentAction.next == null) return;
        currentAction = currentAction.next;
        if (currentAction.type.equals("INSERT")) {
            currentText.insert(currentAction.position, currentAction.text);
        } else if (currentAction.type.equals("DELETE")) {
            currentText.delete(currentAction.position, currentAction.position + currentAction.text.length());
        } else if (currentAction.type.equals("REPLACE")) {
            currentText.replace(currentAction.position, currentAction.position + currentAction.previousText.length(), currentAction.previousText);
        }
    }

    public String getCurrentText() {
        return currentText.toString();
    }

    public void getActionHistory() {
        Action temp = head;
        while (temp != null) {
            System.out.printf("Action: %s, Text: '%s', Position: %d\n", temp.type, temp.text, temp.position);
            temp = temp.next;
        }
    }
}
