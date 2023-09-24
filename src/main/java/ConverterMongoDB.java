import org.bson.Document;

import java.util.Arrays;
import java.util.List;


public class ConverterMongoDB {
    private int index;


    public ConverterMongoDB() {
        index = 0;
    }

    public Document getDocument(String line) {
        index++;
        return distributor(splitLine(line));
    }

    private Document distributor(String[] line) {
        short length = (short) line.length;
        switch (length) {
            case 5: {
                return new Document("_id", index)
                        .append("persAccount", line[0])
                        .append("fullName", line[1])
                        .append("address", line[2])
                        .append("aclualPeriod", line[3])
                        .append("amount", line[4]);
            }
            case 7: {
                return new Document("_id", index)
                        .append("persAccount", line[0])
                        .append("fullName", line[1])
                        .append("address", line[2])
                        .append("aclualPeriod", line[3])
                        .append("amount", line[4])
                        .append("meterData", Arrays.asList(line[5], line[6]));
            }
            default: {
                return new Document("_id", index)
                        .append("persAccount", line[0])
                        .append("fullName", line[1])
                        .append("address", line[2])
                        .append("aclualPeriod", line[3])
                        .append("amount", line[4])
                        .append("meterData", getAsList(line));
            }
        }
    }

    private String[] splitLine(String line) {
        String[] parts = line.split(";");
        return parts;
    }

    private List<String> getAsList(String[] line) {
        int length = line.length;
        String[] arrTemp = new String[length - 5];
        for (int i = 0, j = 5; i < length - 5; i++, j++) {
            arrTemp[i] = line[j];
        }
        return Arrays.asList(arrTemp);
    }
}
