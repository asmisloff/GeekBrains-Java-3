public class Task extends Thread {

    public Task(TaskType taskType, int complexity, String description) {
        this.complexity = complexity;
        this.description = description;
        this.taskType = taskType;
    }

    @Override
    public void run() {
        try {
            switch (taskType) {
                case PRINT:
                    MFU.print(this);
                    break;
                case COPY:
                    MFU.copy(this);
                    break;
                case SCAN_TO_FILE:
                    MFU.scanToFile(this);
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getComplexity() {
        return complexity;
    }

    public String getDescription() {
        return description;
    }

    TaskType taskType;
    private int complexity;
    private String description;
}

enum TaskType {PRINT, COPY, SCAN_TO_FILE}
