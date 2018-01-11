package sbitneva.entity;

public class Port {
    private int portId;
    private String portName;

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    @Override
    public String toString() {
        return "Port{" +
                "portId=" + portId +
                ", portName='" + portName + '\'' +
                '}';
    }
}
