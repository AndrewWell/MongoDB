import java.io.*;
import java.util.ArrayList;

public class WorkingWithFiles {
    private String fullFileName;
    private FileInputStream file;
    private ArrayList<String> arrayList;
    private int index;


    public WorkingWithFiles(String fullFileName) throws FileNotFoundException {
        this.fullFileName = fullFileName;
        file = new FileInputStream(fullFileName);
        index = 0;
        arrayList = new ArrayList<>();
    }

    private void addToList(String line) {
        if (splitLine(line)[3].length() != 4) {
            System.out.println("[Warning] Line: '" + line + "' was`t included in the " +
                    "program code because it dos`t meet the hyphenation criteria");
            return;
        }
        this.arrayList.add(index, line.replace("\'", "\""));
        index++;
    }

    public void readFile() {
        try {
            InputStreamReader fr = new InputStreamReader(file, "Windows-1251");
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            while (line != null) {
                addToList(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    private String[] splitLine(String line) {
        String[] parts = line.split(";");
        return parts;
    }
}
