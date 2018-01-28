package sbitneva.entity;

/**
 * Service entity.
 */
public class Service {
    private String serviceName;

    /**
     * Get additional service name.
     *
     * @return Service name
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Set additional service name.
     *
     * @param serviceName Service name
     */
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Service representation.
     *
     * @return Service description string
     */
    @Override
    public String toString() {
        return "Service{"
                + "serviceName='" + serviceName + '\''
                + '}';
    }
}
