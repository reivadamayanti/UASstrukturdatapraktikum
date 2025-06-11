class Student {
    String nim;
    String name;
    double gpa;
    Student next;

    public Student(String nim, String name, double gpa) {
        this.nim = nim;
        this.name = name;
        this.gpa = gpa;
        this.next = null;
    }
}

class StudentLinkedList {
    private Student head;
    private int size;

    public StudentLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void insertFirst(String nim, String name, double gpa) {
        Student newStudent = new Student(nim, name, gpa);
        newStudent.next = head;
        head = newStudent;
        size++;
    }

    public void insertLast(String nim, String name, double gpa) {
        Student newStudent = new Student(nim, name, gpa);
        if (head == null) {
            head = newStudent;
        } else {
            Student current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newStudent;
        }
        size++;
    }

    public void insertAt(int position, String nim, String name, double gpa) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
        if (position == 0) {
            insertFirst(nim, name, gpa);
        } else {
            Student newStudent = new Student(nim, name, gpa);
            Student current = head;
            for (int i = 0; i < position - 1; i++) {
                current = current.next;
            }
            newStudent.next = current.next;
            current.next = newStudent;
            size++;
        }
    }

    public void deleteByNim(String nim) {
        if (head == null) return;
        if (head.nim.equals(nim)) {
            head = head.next;
            size--;
            return;
        }
        Student current = head;
        while (current.next != null && !current.next.nim.equals(nim)) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
            size--;
        }
    }

    public Student search(String nim) {
        Student current = head;
        while (current != null) {
            if (current.nim.equals(nim)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void display() {
        Student current = head;
        System.out.println("=== Data Mahasiswa ===");
        while (current != null) {
            System.out.printf("NIM: %s, Nama: %s, IPK: %.2f\n", current.nim, current.name, current.gpa);
            current = current.next;
        }
        System.out.printf("Total mahasiswa: %d\n", size);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void sortByGPA() {
        if (head == null) return;
        boolean swapped;
        do {
            swapped = false;
            Student current = head;
            while (current.next != null) {
                if (current.gpa < current.next.gpa) {
                    double tempGPA = current.gpa;
                    String tempNIM = current.nim;
                    String tempName = current.name;

                    current.gpa = current.next.gpa;
                    current.nim = current.next.nim;
                    current.name = current.next.name;

                    current.next.gpa = tempGPA;
                    current.next.nim = tempNIM;
                    current.next.name = tempName;

                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    public void reverse() {
        Student prev = null;
        Student current = head;
        Student next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }

    public Student findHighestGPA() {
        if (head == null) return null;
        Student highest = head;
        Student current = head.next;
        while (current != null) {
            if (current.gpa > highest.gpa) {
                highest = current;
            }
            current = current.next;
        }
        return highest;
    }

    public Student[] getStudentsAboveGPA(double threshold) {
        Student current = head;
        Student[] result = new Student[size];
        int index = 0;
        while (current != null) {
            if (current.gpa > threshold) {
                result[index++] = current;
            }
            current = current.next;
        }
        return java.util.Arrays.copyOf(result, index);
    }

    public void mergeSortedList(StudentLinkedList otherList) {
        Student current1 = this.head;
        Student current2 = otherList.head;
        Student mergedHead = null;
        Student mergedTail = null;

        while (current1 != null && current2 != null) {
            Student smallerNode;
            if (current1.gpa >= current2.gpa) {
                smallerNode = current1;
                current1 = current1.next;
            } else {
                smallerNode = current2;
                current2 = current2.next;
            }
            if (mergedHead == null) {
                mergedHead = smallerNode;
                mergedTail = smallerNode;
            } else {
                mergedTail.next = smallerNode;
                mergedTail = smallerNode;
            }
        }
        if (current1 != null) {
            mergedTail.next = current1;
        } else {
            mergedTail.next = current2;
        }
        this.head = mergedHead;
    }
}
