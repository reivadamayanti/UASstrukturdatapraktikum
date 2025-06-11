public class Main {
    public static void main(String[] args) {
        // Testing StudentLinkedList
        StudentLinkedList studentList = new StudentLinkedList();
        studentList.insertFirst("12345", "Andi Pratama", 3.75);
        studentList.insertLast("12346", "Sari Dewi", 3.82);
        studentList.insertLast("12347", "Budi Santoso", 3.65);
        studentList.display();

        studentList.sortByGPA();
        System.out.println("\nAfter sorting by GPA:");
        studentList.display();

        // Testing CircularPlaylist
        CircularPlaylist playlist = new CircularPlaylist();
        playlist.addSong("Bohemian Rhapsody", "Queen", 363);
        playlist.addSong("Hotel California", "Eagles", 391);
        playlist.addSong("Stairway to Heaven", "Led Zeppelin", 482);
        playlist.addSong("Imagine", "John Lennon", 183);
        playlist.displayPlaylist();

        // Testing TextEditor
        TextEditor editor = new TextEditor();
        editor.insertText("Hello World", 0);
        editor.insertText("Beautiful ", 6);
        System.out.println(editor.getCurrentText()); // "Hello Beautiful World"
        editor.undo();
        System.out.println(editor.getCurrentText()); // "Hello World"
        editor.redo();
        System.out.println(editor.getCurrentText()); // "Hello Beautiful World"
    }
}
