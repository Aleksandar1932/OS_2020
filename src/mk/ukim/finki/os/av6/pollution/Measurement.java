package mk.ukim.finki.os.av6.pollution;

import java.util.Date;

public class Measurement {
    String particleName;
    Integer amount;
    String timestamp;

    public Measurement(String particleName, Integer amount, String timestamp) {
        this.particleName = particleName;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("%s : %d @ %s", particleName, amount, timestamp);
    }

    public String getParticleName() {
        return particleName;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
