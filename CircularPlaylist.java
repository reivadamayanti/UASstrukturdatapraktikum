import java.util.Random;

// package-private Song class inside the same file, no public modifier
class Song {
    String title;
    String artist;
    int duration; // in seconds
    Song next;

    public Song(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.next = null;
    }
}

public class CircularPlaylist {
    private Song currentSong;
    private int size;

    public CircularPlaylist() {
        this.currentSong = null;
        this.size = 0;
    }

    public void addSong(String title, String artist, int duration) {
        Song newSong = new Song(title, artist, duration);
        if (currentSong == null) {
            currentSong = newSong;
            newSong.next = newSong; // point to itself
        } else {
            Song temp = currentSong;
            while (temp.next != currentSong) {
                temp = temp.next;
            }
            temp.next = newSong;
            newSong.next = currentSong;
        }
        size++;
    }

    public void removeSong(String title) {
        if (currentSong == null) return;
        Song temp = currentSong;
        Song prev = null;
        do {
            if (temp.title.equals(title)) {
                if (prev != null) {
                    prev.next = temp.next;
                } else {
                    Song last = currentSong;
                    while (last.next != currentSong) {
                        last = last.next;
                    }
                    last.next = currentSong.next;
                    currentSong = currentSong.next;
                }
                size--;
                return;
            }
            prev = temp;
            temp = temp.next;
        } while (temp != currentSong);
    }

    public void playNext() {
        if (currentSong != null) {
            currentSong = currentSong.next;
        }
    }

    public void playPrevious() {
        if (currentSong != null) {
            Song temp = currentSong;
            while (temp.next != currentSong) {
                temp = temp.next;
            }
            currentSong = temp;
        }
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void shuffle() {
        if (currentSong == null) return;
        Song[] songs = new Song[size];
        Song temp = currentSong;
        for (int i = 0; i < size; i++) {
            songs[i] = temp;
            temp = temp.next;
        }
        Random rand = new Random();
        for (int i = 0; i < songs.length; i++) {
            int j = rand.nextInt(songs.length);
            Song tmp = songs[i];
            songs[i] = songs[j];
            songs[j] = tmp;
        }
        currentSong = songs[0];
        for (int i = 0; i < songs.length - 1; i++) {
            songs[i].next = songs[i + 1];
        }
        songs[songs.length - 1].next = currentSong;
    }

    public String getTotalDuration() {
        int totalDuration = 0;
        Song temp = currentSong;
        if (temp != null) {
            do {
                totalDuration += temp.duration;
                temp = temp.next;
            } while (temp != currentSong);
        }
        return String.format("%d:%02d", totalDuration / 60, totalDuration % 60);
    }

    public void displayPlaylist() {
        if (currentSong == null) return;
        Song temp = currentSong;
        System.out.println("=== Current Playlist ===");
        int index = 1;
        do {
            String prefix = (temp == currentSong) ? "-> Currently Playing:" : String.format("   %d.", index);
            System.out.printf("%s %s - %s (%d:%02d)%n", prefix, temp.title, temp.artist, temp.duration / 60, temp.duration % 60);
            temp = temp.next;
            index++;
        } while (temp != currentSong);
        System.out.printf("Total Duration: %s%n", getTotalDuration());
    }
}

