public class MFU {

    static final Object printMonitor = new Object();
    static final Object scanMonitor = new Object();

    static void print(Task task) throws InterruptedException {
        synchronized (printMonitor) {
            System.out.println("Идет печать: " + task.getDescription());
            Thread.sleep(100 * task.getComplexity());
            System.out.println("Печать завершена: " + task.getDescription());
        }
    }

    static void scanToFile(Task task) throws InterruptedException {
        synchronized (scanMonitor) {
            System.out.println("Идет сканивание: " + task.getDescription());
            Thread.sleep(100 * task.getComplexity());
            System.out.println("Сканирование завершено: " + task.getDescription());
        }
    }

    static void copy(Task task) throws InterruptedException {
        scanToFile(task);
        print(task);
    }

    public static void main(String[] args) {
        new Task(TaskType.PRINT, 10, "Печать чертежей").start();
        new Task(TaskType.PRINT, 10, "Печать схемы").start();
        new Task(TaskType.COPY, 5, "Копирование паспорта").start();
        new Task(TaskType.SCAN_TO_FILE, 3, "Сканирование договора").start();
    }
}
